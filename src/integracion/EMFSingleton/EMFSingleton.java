package integracion.EMFSingleton;

import jakarta.persistence.EntityManagerFactory;

public abstract class EMFSingleton {
	private static EMFSingleton instancia;

	public static synchronized EMFSingleton getInstancia() {

		if (instancia == null)
			instancia = new EMFSingletonImp();

		return instancia;
	}

	public abstract EntityManagerFactory getEntityManagerFactory();
}
