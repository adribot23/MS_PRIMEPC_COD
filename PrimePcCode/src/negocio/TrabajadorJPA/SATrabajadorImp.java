package negocio.TrabajadorJPA;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import integracion.EMFSingleton.EMFSingleton;

public class SATrabajadorImp implements SATrabajador{

	@Override
	public int altaTrabajador(TTrabajador trabajador) {
		int id = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		EntityTransaction tr = em.getTransaction();
		
		return 0;
	}

	@Override
	public int bajaTrabajador(int id_trabajador) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public int modificarTrabajador(TTrabajador trabajador) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public TTrabajador buscarTrabajador(int id_trabajador) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TTrabajador> mostrarTrabajador() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
