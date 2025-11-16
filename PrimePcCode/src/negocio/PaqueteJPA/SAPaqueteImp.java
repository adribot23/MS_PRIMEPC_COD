package negocio.PaqueteJPA;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import integracion.EMFSingleton.EMFSingleton;

public class SAPaqueteImp implements SAPaquete{

	@Override
	public int altaPaquete(TPaquete paquete) {
		int id = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		EntityTransaction tr = em.getTransaction();
		
		return 0;
	}

	@Override
	public int bajaPaquete(int id_paquete) {
		return 0;
	}

	@Override
	public int modificarPaquete(TPaquete paquete) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public TPaquete buscarPaquete(int id_paquete) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TPaquete> mostrarPaquetes() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
