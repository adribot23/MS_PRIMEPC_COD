package negocio.RutaJPA;

import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
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

			Ruta ruta = em.find(Ruta.class, truta.get_id());

			if (ruta != null && ruta.getActivo() == 1) {
				ruta.setOrigen(truta.get_origen());
				ruta.setDestino(truta.get_destino());
				ruta.setDistancia(truta.get_distancia());

				res = 1;
				em.getTransaction().commit();
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
