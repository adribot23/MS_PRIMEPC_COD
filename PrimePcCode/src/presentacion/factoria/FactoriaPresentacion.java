package presentacion.factoria;

import presentacion.vista.Evento;
import presentacion.vista.IGUI;

public abstract class FactoriaPresentacion {

	private static FactoriaPresentacion instancia;

	public static FactoriaPresentacion obtenerInstancia(Evento e) {
		if (instancia == null)
			instancia = new FactoriaPresentacionImp(e);
		return instancia;
	}

	public abstract IGUI GeneraVista();

}
