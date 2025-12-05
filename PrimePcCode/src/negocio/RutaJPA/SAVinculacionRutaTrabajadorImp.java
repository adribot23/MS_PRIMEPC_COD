package negocio.RutaJPA;

import java.util.Set;

import integracion.EMFSingleton.EMFSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import negocio.TrabajadorJPA.Trabajador;

public class SAVinculacionRutaTrabajadorImp implements SAVinculacionRutaTrabajador {

	@Override
	public int vincular_ruta_trabajador(TVinculacionRutaTrabajador tvinculacion) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Trabajador trabajador = em.find(Trabajador.class, tvinculacion.get_id_trabajador(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Ruta ruta = em.find(Ruta.class, tvinculacion.get_id_ruta(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			VinculacionRutaTrabajadorID id = new VinculacionRutaTrabajadorID(tvinculacion.get_id_ruta(),
					tvinculacion.get_id_trabajador());
			VinculacionRutaTrabajador vinculacion = em.find(VinculacionRutaTrabajador.class, id);

			if (trabajador != null && ruta != null && trabajador.getActivo() == 1 && ruta.getActivo() == 1
					&& vinculacion == null) {
				vinculacion = new VinculacionRutaTrabajador(ruta, trabajador, tvinculacion.get_estado(), tvinculacion.get_fecha_asignacion(), tvinculacion.get_hora_salida());
				em.persist(vinculacion);
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
	public int desvincular_ruta_trabajador(TVinculacionRutaTrabajador tvinculacion) {
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			
			Trabajador trabajador = em.find(Trabajador.class, tvinculacion.get_id_trabajador(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Ruta ruta = em.find(Ruta.class, tvinculacion.get_id_ruta(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (trabajador != null && ruta != null && trabajador.getActivo() == 1 && ruta.getActivo() == 1) {

				VinculacionRutaTrabajadorID id = new VinculacionRutaTrabajadorID(ruta.getId(),
						trabajador.getId());
				VinculacionRutaTrabajador vinculacion = em.find(VinculacionRutaTrabajador.class, id);

				if (vinculacion != null) {
					em.remove(vinculacion);
					res = 1;
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
	public Set<TVinculacionRutaTrabajador> listar_vinculaciones_por_trabajador(int id_trabajador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVinculacionRutaTrabajador> listar_vinculaciones_por_ruta(int id_ruta) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
