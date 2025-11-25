package negocio.TrabajadorJPA;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import integracion.EMFSingleton.EMFSingleton;

public class SATrabajadorImp implements SATrabajador{

	@Override
	public int AltaTrabajador(TTrabajador trabajador) {
	//	int id = -1;
	//	EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
	//	EntityTransaction tr = em.getTransaction();
		
		return 0;
	}

	@Override
	public int BajaTrabajador(int id_trabajador) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public int ModificarTrabajador(TTrabajador trabajador) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public TTrabajador leerTrabajador(int id_trabajador) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TTrabajador> leerTodosTrabajadores() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
