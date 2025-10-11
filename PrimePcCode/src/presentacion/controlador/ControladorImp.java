package presentacion.controlador;


import presentacion.controlador.command.Command;
import presentacion.controlador.command.CommandFactory;
import presentacion.factoria.FactoriaPresentacion;



public class ControladorImp extends Controlador {

	public void accion(Context context) {

		Command command = CommandFactory.getInstance().getCommand(context.getEvento());

		if (command != null) {
			context = command.execute(context.getDatos());
		}

		FactoriaPresentacion.obtenerInstancia().GeneraVista(context.getEvento()).actualizar(context);

	}
}
