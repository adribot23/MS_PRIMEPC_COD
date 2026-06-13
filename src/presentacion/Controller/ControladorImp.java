
package presentacion.Controller;

import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.CommandFactory;
import presentacion.Controller.Command.Context;
import presentacion.GUI.GUIAbstractFactory;

public class ControladorImp extends Controlador {

	@Override
	public void accion(Context context) {

		Command command = CommandFactory.getInstance().getCommand(context.getEvento());

		if (command != null) {
			context = command.execute(context.getDatos());
		}

		GUIAbstractFactory.getInstancia().generarVistas(context.getEvento()).actualizar(context);

	}
}