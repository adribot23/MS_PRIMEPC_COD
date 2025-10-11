package presentacion.factoria;

import presentacion.vista.IGUI;

public abstract class FactoriaPresentacion {

	private static FactoriaPresentacion instancia;

	public static FactoriaPresentacion obtenerInstancia() {
		if (instancia == null)
			instancia = new FactoriaPresentacionImp();
		return instancia;
	}

	public abstract IGUI GeneraVista(Evento e);

}
