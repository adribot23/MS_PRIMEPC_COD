
package presentacion.Controller;

import presentacion.Controller.Command.Context;

public abstract class Controlador {

	private static Controlador instancia;

	public static synchronized Controlador getInstancia() {
		if (instancia == null) {
			instancia = new ControladorImp();
		}
		return instancia;
	}

	public abstract void accion(Context context);
}