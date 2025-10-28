
package Presentacion.Controller;

import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.CommandFactory;
import Presentacion.Controller.Command.Context;
import Presentacion.GUI.GUIAbstractFactory;


public class ControladorImp extends Controlador {
	
	@Override
	public void accion(Context context) {

		Command command = CommandFactory.getInstance().getCommand(context.getEvento());

		if (command != null) {
			context = command.execute(context.getDatos());
		}

		GUIAbstractFactory.obtenerInstancia().generarVistas(context.getEvento()).actualizar(context);

	}
}