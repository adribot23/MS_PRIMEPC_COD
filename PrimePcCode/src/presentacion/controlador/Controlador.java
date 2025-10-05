package presentacion.controlador;

import presentacion.vista.Evento;

public abstract class Controlador {

	private static Controlador ctrl;

	public static Controlador obtenerInstancia() {
		if (ctrl == null)
			ctrl = new ControladorImp();
		return ctrl;
	}

	public abstract void accion(Evento evento, Object datos);
}
