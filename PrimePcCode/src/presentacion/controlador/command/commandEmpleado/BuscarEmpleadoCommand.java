package presentacion.controlador.command.commandEmpleado;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TEmpleado;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TEmpleado tEmpleado = FactoriaNegocio.obtenerInstancia().generaSAEmpleado().leerEmpleado((int) transfer);
		if (tEmpleado != null)
			return new Context(Evento.RES_BUSCAR_EMPLEADO_OK, tEmpleado);
		else
			return new Context(Evento.RES_BUSCAR_EMPLEADO_KO, null);
	}
}