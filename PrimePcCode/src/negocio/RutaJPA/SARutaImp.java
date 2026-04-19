package negocio.RutaJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import negocio.PaqueteJPA.Paquete;
import integracion.EMFSingleton.EMFSingleton;

public class SARutaImp implements SARuta {

	@Override
	public synchronized int alta_ruta(TRuta truta) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Buscar por origen + destino (clave única)
			List<Ruta> listaRutas = em
					.createNamedQuery("negocio.RutaJPA.Ruta.findByOrigenDestino", Ruta.class)
					.setParameter("origen", truta.get_origen())
					.setParameter("destino", truta.get_destino())
					.getResultList();

			Ruta r = listaRutas.isEmpty() ? null : listaRutas.get(0);

			if (r == null) {
				// No existe, lo creamos
				Ruta ruta = new Ruta(truta);
				ruta.setActivo(1);
				em.persist(ruta);
				em.getTransaction().commit();
				res = ruta.getId();
			} else if (r.getActivo() == 0) {
				// Existe pero está inactivo, lo reactivamos
				r.setActivo(1);
				r.setDistancia(truta.get_distancia());
				em.getTransaction().commit();
				res = r.getId();
			} else {
				// Ya existe y está activo
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
	public int baja_ruta(int id) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Ruta ruta = em.find(Ruta.class, id);

			if (ruta != null) {
				boolean existeActivo = false;

				for (Paquete paquete : ruta.get_lista_paquetes()) {
					if (paquete.getActivo() == 1) {
						res = -2;
						existeActivo = true;
						break;
					}
				}
				
				

				if (!existeActivo && !ruta.get_vinculaciones().isEmpty()) {
					res = -3;
					existeActivo = true;
				}

				if (!existeActivo) {
					res = ruta.getId();
					ruta.setActivo(0);
					em.getTransaction().commit();
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
	public int modificar_ruta(TRuta truta) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Ruta rutaExistente = em.find(Ruta.class, truta.get_id());

			if (rutaExistente != null && rutaExistente.getActivo() == 1) {
				// Verificar que no exista otra ruta con el mismo origen+destino
				List<Ruta> rutasConMismoOrigenDestino = em
						.createNamedQuery("negocio.RutaJPA.Ruta.findByOrigenDestino", Ruta.class)
						.setParameter("origen", truta.get_origen())
						.setParameter("destino", truta.get_destino())
						.getResultList();

				if (rutasConMismoOrigenDestino.isEmpty() || 
					(rutasConMismoOrigenDestino.size() == 1 && rutasConMismoOrigenDestino.get(0).getId() == truta.get_id())) {
					rutaExistente.setOrigen(truta.get_origen());
					rutaExistente.setDestino(truta.get_destino());
					rutaExistente.setDistancia(truta.get_distancia());

					em.getTransaction().commit();
					res = rutaExistente.getId();
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
	public TRuta buscar_ruta(int id) {
		TRuta res = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Bloqueamos optimista la ruta para evitar que la modifiquen mientras lo
			// leemos
			Ruta ruta = em.find(Ruta.class, id, LockModeType.OPTIMISTIC);

			if (ruta != null) {
				res = ruta.entityToTransfer();
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			em.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public Set<TRuta> listar_rutas() {
		Set<TRuta> res = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

		
			TypedQuery<Ruta> query = em.createQuery("SELECT r FROM Ruta r", Ruta.class);
			query.setLockMode(LockModeType.OPTIMISTIC);

			List<Ruta> result = query.getResultList();

			if (result != null) {

				res = new LinkedHashSet<TRuta>();

				for (Ruta ruta : result) {
					em.lock(ruta, LockModeType.OPTIMISTIC);
					res.add(ruta.entityToTransfer());
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

		return res;
	}

}
