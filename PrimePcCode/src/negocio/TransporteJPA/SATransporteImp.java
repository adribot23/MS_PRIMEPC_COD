package negocio.TransporteJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TrabajadorJPA.Trabajador;

public class SATransporteImp implements SATransporte {

	@Override
	public synchronized int altaTransporte(TTransporte t) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			List<Transporte> listaTransporte = em
					.createNamedQuery("negocio.TransporteJPA.Transporte.findByMatricula", Transporte.class)
					.setParameter("matricula", t.getMatricula()).getResultList();

			Transporte r = listaTransporte.isEmpty() ? null : listaTransporte.get(0);

			if (r == null) {
				Transporte n = new Transporte(t);
				em.persist(n); // commit detectará conflictos por @Version si hay
				em.getTransaction().commit();
				res = n.getId();
			} else if (r.getActivo() == 0) {
				r.setNombre(t.getNombre());
				r.setMatricula(t.getMatricula());
				r.setCapacidad(t.getCapacidad());
				r.setActivo(1);
				em.getTransaction().commit();
				res = r.getId();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public int bajaTransporte(int id) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Transporte t = em.find(Transporte.class, id); // No lock necesario, versión controla conflictos
			if (t != null && t.getActivo() == 1) {

				if (t.getTrabajadores().isEmpty()) {
					t.setActivo(0); // marca como inactivo
					em.getTransaction().commit();
					res = t.getId();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public int modificarTransporte(TTransporte t) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Transporte tExistente = em.find(Transporte.class, t.getId()); // No lock necesario al modificar con @Version
			if (tExistente != null && tExistente.getActivo() == 1) {

				List<Transporte> tMatricula = em
						.createNamedQuery("negocio.TransporteJPA.Transporte.findByMatricula", Transporte.class)
						.setParameter("matricula", t.getMatricula()).getResultList();

				if (tMatricula.isEmpty() || (tMatricula.size() == 1 && tMatricula.get(0).getId() == t.getId())) {
					tExistente.setNombre(t.getNombre());
					tExistente.setMatricula(t.getMatricula());
					tExistente.setCapacidad(t.getCapacidad());

					em.getTransaction().commit();
					res = tExistente.getId();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public TTransporte leerTransporte(int id) {
		TTransporte transporte = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Transporte transporteById = em.find(Transporte.class, id, LockModeType.OPTIMISTIC);

			if (transporteById != null) {
				transporte = transporteById.toTransfer();
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return transporte;
	}

	@Override
	public Set<TTransporte> leerTodosTransportes() {
		Set<TTransporte> listaTransportes = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();
			TypedQuery<Transporte> query = em.createNamedQuery("negocio.TransporteJPA.Transporte.findAll",
					Transporte.class);
			query.setLockMode(LockModeType.OPTIMISTIC); // Opcional, protege lectura

			List<Transporte> resultados = query.getResultList();
			if (!resultados.isEmpty()) {
				listaTransportes = new LinkedHashSet<>();
				for (Transporte t : resultados) {
					// No hace falta lock individual porque el query ya bloquea optimista
					listaTransportes.add(t.toTransfer());
				}
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return listaTransportes;
	}

	@Override
	public int vincularTransporteTrabajador(TTransporteTrabajador t) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Transporte transporte = em.find(Transporte.class, t.getId_transporte());
			Trabajador trabajador = em.find(Trabajador.class, t.getId_trabajador(), LockModeType.OPTIMISTIC);

			if (transporte != null && trabajador != null && transporte.getActivo() == 1
					&& trabajador.getActivo() == 1) {
				if (!transporte.getTrabajadores().contains(trabajador)) {
					// EclipseLink incrementa automáticamente versión del transporte en ManyToMany
					transporte.getTrabajadores().add(trabajador);
					
					em.getTransaction().commit();
					res = 1;
				} else {
					em.getTransaction().rollback();
				}

			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public int desvincularTransporteTrabajador(TTransporteTrabajador t) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Transporte transporte = em.find(Transporte.class, t.getId_transporte());
			Trabajador trabajador = em.find(Trabajador.class, t.getId_trabajador(), LockModeType.OPTIMISTIC);

			if (transporte != null && trabajador != null && transporte.getActivo() == 1
					&& trabajador.getActivo() == 1) {
				if (transporte.getTrabajadores().contains(trabajador)) {
					// EclipseLink incrementa automáticamente versión del transporte en ManyToMany
					transporte.getTrabajadores().remove(trabajador);
					em.getTransaction().commit();
					res = 1;
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public Set<TTransporte> leerTransportesPorTrabajador(int id_trabajador) {
		Set<TTransporte> transportes = new LinkedHashSet<>();
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();
			Trabajador trabajador = em.find(Trabajador.class, id_trabajador, LockModeType.OPTIMISTIC);

			if (trabajador != null && trabajador.getActivo() == 1) {
				for (Transporte transporte : trabajador.getTransportes()) {
					if (transporte.getActivo() == 1)
						transportes.add(transporte.toTransfer());
				}
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return transportes;
	}

}
