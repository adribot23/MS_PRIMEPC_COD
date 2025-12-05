package negocio.RutaJPA;

import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;

public class SARutaImp implements SARuta {

	@Override
	public synchronized int alta_ruta(TRuta truta) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			TypedQuery<Ruta> query = em.createNamedQuery("negocio.RutaJPA.Ruta.findById", Ruta.class);
			query.setParameter("id", truta.get_id());


			List<Ruta> rutas = query.getResultList();

			// Si no existe, lo creamos
			if (rutas.isEmpty()) {

				Ruta ruta = new Ruta(truta);
				em.persist(ruta);
				em.getTransaction().commit();
				res = ruta.getId();

				// Si existe, lo activamos
			} else if (query.getSingleResult().getActivo() == 0) {

				Ruta ruta = query.getSingleResult();
				ruta.setActivo(1);
				res = ruta.getId();
				em.getTransaction().commit();

				// Si existe y está activo, no hacemos nada
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}


	@Override
	public int baja_ruta(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modificar_ruta(TRuta ruta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TRuta buscar_ruta(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TRuta> listar_rutas() {
		// TODO Auto-generated method stub
		return null;
	}

}
