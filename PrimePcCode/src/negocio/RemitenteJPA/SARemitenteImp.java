package negocio.RemitenteJPA;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;

public class SARemitenteImp implements SARemitente {

	@Override
	public synchronized int altaRemitente(TRemitente tRemitente) {
		int id = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();

			TypedQuery<Remitente> query = em.createNamedQuery("Negocio.RemitenteJPA.Remitente.findByNombre",
					Remitente.class);

			query.setParameter("nombre", tRemitente.getNombre());

			Remitente existente = null;

			try {
				existente = query.getSingleResult();
			} catch (Exception e) {
			}

			Remitente nuevo = null;

			if (existente == null) {

				if (tRemitente instanceof TEmpresa) {
					TEmpresa te = (TEmpresa) tRemitente;
					Empresa empresa = new Empresa(te);
					nuevo = empresa;
					em.persist(nuevo);
					tr.commit();
					id = nuevo.getId();

				} else if (tRemitente instanceof TParticular) {
					TParticular tp = (TParticular) tRemitente;
					Particular particular = new Particular(tp);
					nuevo = particular;
					em.persist(nuevo);
					tr.commit();
					id = nuevo.getId();

				} else {
					tr.rollback();
				}
			}

			else if (existente != null && existente.getActivo() == 0) {

				if (existente instanceof Empresa && tRemitente instanceof TEmpresa) {

					TEmpresa te = (TEmpresa) tRemitente;
					Empresa emp = (Empresa) existente;

					emp.setActivo(1);
					emp.setDireccion(te.getDireccion());
					emp.setTelefono(te.getTelefono());
					emp.setNombre(te.getNombre());
					emp.setNumRegistroFiscal(te.getNumRegistroFiscal());

					tr.commit();
					id = emp.getId();
				} else if (existente instanceof Particular && tRemitente instanceof TParticular) {

					TParticular tp = (TParticular) tRemitente;
					Particular par = (Particular) existente;

					par.setActivo(1);
					par.setDireccion(tp.getDireccion());
					par.setTelefono(tp.getTelefono());
					par.setNombre(tp.getNombre());
					par.setFechaNacimiento(tp.getFechaNacimiento());

					tr.commit();
					id = par.getId();
				} else {
					tr.rollback();
				}

			} else {
				tr.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		} finally {
			em.close();
		}

		return id;
	}

	@Override
	public int bajaRemitente(int id_remitente) {
		int exito = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();

			Remitente remitente = em.find(Remitente.class, id_remitente);

			if (remitente != null && remitente.getActivo() == 1) {

				remitente.setActivo(0);

				tr.commit();
				exito = remitente.getId();

			} else {
				tr.rollback();
			}
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return exito;
	}

	@Override
	public int modificarRemitente(TRemitente tRem) {
		int res = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Remitente remitenteByID = em.find(Remitente.class, tRem.getId());

			if (remitenteByID == null || remitenteByID.getActivo() == 0) {
				em.getTransaction().rollback();
				em.close();
				return res;
			}

			TypedQuery<Remitente> query = em.createNamedQuery("Negocio.RemitenteJPA.Remitente.findByNombre",
					Remitente.class);
			query.setParameter("nombre", tRem.getNombre());

			List<Remitente> lista = query.getResultList();

			boolean nombreDisponible = lista.isEmpty() || (lista.size() == 1 && lista.get(0).getId() == tRem.getId());

			if (!nombreDisponible) {
				em.getTransaction().rollback();
				em.close();
				return res;
			}

			if (tRem instanceof TEmpresa && remitenteByID instanceof Empresa) {

				TEmpresa te = (TEmpresa) tRem;
				Empresa emp = (Empresa) remitenteByID;

				emp.setNombre(te.getNombre());
				emp.setDireccion(te.getDireccion());
				emp.setTelefono(te.getTelefono());
				emp.setNumRegistroFiscal(te.getNumRegistroFiscal());

				em.getTransaction().commit();
				res = emp.getId();
			}

			else if (tRem instanceof TParticular && remitenteByID instanceof Particular) {

				TParticular tp = (TParticular) tRem;
				Particular par = (Particular) remitenteByID;

				par.setNombre(tp.getNombre());
				par.setDireccion(tp.getDireccion());
				par.setTelefono(tp.getTelefono());
				par.setFechaNacimiento(tp.getFechaNacimiento());

				em.getTransaction().commit();
				res = par.getId();
			}

			else {
				em.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
		}

		em.close();
		return res;
	}

	@Override
	public TRemitente buscarRemitente(int id_remitente) {
		TRemitente tremitente = null;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Remitente remitente = em.find(Remitente.class, id_remitente, LockModeType.OPTIMISTIC);

			if (remitente != null) {
				tremitente = remitente.entityToTransfer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();

		return tremitente;
	}

	@Override
	public Set<TRemitente> listarTodosRemitentes() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();
		Set<TRemitente> remitentes = new LinkedHashSet<>();

		try {
			tr.begin();
			TypedQuery<Remitente> query = em.createQuery("SELECT r FROM Remitente r", Remitente.class);
			Set<Remitente> listaRemitentes = new LinkedHashSet<>(query.getResultList());

			for (Remitente r : listaRemitentes) {
				em.lock(r, LockModeType.OPTIMISTIC);

				TRemitente t = r.entityToTransfer();
				remitentes.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();

		return remitentes;
	}

	@Override
	public double calcularPrecioPaquetesRemitente(int id_remitente) {
		return 0;
	}
}
