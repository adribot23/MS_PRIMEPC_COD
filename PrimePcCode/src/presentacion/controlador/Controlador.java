package presentacion.controlador;

import presentacion.factoria.Evento;

public abstract class Controlador {

	private static Controlador ctrl;

	public static synchronized Controlador obtenerInstancia() {
		if (ctrl == null)
			ctrl = new ControladorImp();
		return ctrl;
	}

	public abstract void accion(Context context);
}
