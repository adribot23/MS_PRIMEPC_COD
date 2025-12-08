package negocio.TrabajadorJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;
import negocio.RemitenteJPA.Remitente;
import negocio.RemitenteJPA.TRemitente;
import negocio.TransporteJPA.TTransporte;
import negocio.TransporteJPA.Transporte;

public class SATrabajadorImp implements SATrabajador {

	@Override
	public synchronized int altaTrabajador(TTrabajador trabajador) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();
			

			List<Trabajador> listaTrabajador = em
					.createNamedQuery("Negocio.TrabajadorJPA.Trabajador.findByDNI", Trabajador.class)
					.setParameter("DNI", trabajador.getDNI()).getResultList();

			Trabajador r = listaTrabajador.isEmpty() ? null : listaTrabajador.get(0);

			if (r == null) {
				// Lo persisto como nuevo
				Trabajador n = new Trabajador(trabajador);
				em.persist(n);
				tr.commit();
				res = n.getId();
			} else if (r.getActivo() == 0) {
				// Lo pongo activo
				r.setActivo(1);
				tr.commit();
				res = r.getId();
			} else {
				tr.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public int bajaTrabajador(int id_trabajador) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();
			
			Trabajador t = em.find(Trabajador.class, id_trabajador);
			
			
			if (t == null && t.getActivo() == 1) {
				t.setActivo(0);
				tr.commit();
				res = t.getId();

			} else {
				em.getTransaction().rollback();
			}
			tr.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public int modificarTrabajador(TTrabajador trabajador) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			em.getTransaction().begin();
			Trabajador tExistente = em.find(Trabajador.class, trabajador.getId());
			
			if (tExistente == null || tExistente.getActivo() == 0) {
				em.getTransaction().rollback();
				em.close();
				return res;
			}

				TypedQuery<Trabajador> query = em
						.createNamedQuery("Negocio.TrabajadorJPA.Trabajador.findByDNI", Trabajador.class);
				query.setParameter("DNI", tExistente.getDNI());
				List<Trabajador> lista = query.getResultList();
				
				boolean nombreDisponible = lista.isEmpty() || (lista.size() == 1 && lista.get(0).getId() == tExistente.getId());
				
				if (!nombreDisponible) {
					em.getTransaction().rollback();
					em.close();
					return res;
				}

				if (lista.isEmpty()
						|| (lista.size() == 1 && lista.get(0).getId() == trabajador.getId())) {

					tExistente.setNombre(trabajador.getNombre());
					tExistente.setDNI(trabajador.getDNI());
					tExistente.setActivo(trabajador.isActivo());

					em.getTransaction().commit();
					res = tExistente.getId();

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
	public TTrabajador leerTrabajador(int id_trabajador) {

		TTrabajador trabajador = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			em.getTransaction().begin();
			Trabajador trabajadorByID = em.find(Trabajador.class, LockModeType.OPTIMISTIC);

			if (trabajadorByID != null) {
				trabajador = trabajadorByID.entityToTransfer();
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

		return trabajador;
	}

	@Override
	public Set<TTrabajador> leerTodosTrabajadores() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		EntityTransaction tr = em.getTransaction();
		Set<TTrabajador> trabajadores = new LinkedHashSet<>();

		try {

			tr.begin();

			TypedQuery<Trabajador> query = em.createNamedQuery("negocio.TrabajadorJPA.Trabajador.findAll",
					Trabajador.class);
			Set<Trabajador> listaTrabajadores = new LinkedHashSet<>(query.getResultList());

			for (Trabajador t: listaTrabajadores) {
				em.lock(t, LockModeType.OPTIMISTIC);

				TTrabajador tt = t.entityToTransfer();
				trabajadores.add(tt);
			}

		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		} finally {
			em.close();
		}

		return trabajadores;
	}

	@Override
	public Set<TTrabajador> leerTrabajadorPorTransporte(int id_transporte) {

		Set<TTrabajador> trabajadores = new LinkedHashSet<>();
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			em.getTransaction().begin();

			Transporte transporte = em.find(Transporte.class, id_transporte, LockModeType.OPTIMISTIC);

			if (transporte != null && transporte.getActivo() == 1) {

				for (Trabajador trabajador : transporte.getTrabajadores()) {
					em.lock(trabajador, LockModeType.OPTIMISTIC);

					if (trabajador.getActivo() == 1)
						trabajadores.add(trabajador.entityToTransfer());
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

		return trabajadores;
	}


}
