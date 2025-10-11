package presentacion.controlador.command.commandEmpleado;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TEmpleado;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class AltaEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAEmpleado().altaEmpleado((TEmpleado) transfer);
		if (res > 0)
			return new Context(Evento.RES_ALTA_EMPLEADO_OK, res);
		else
			return new Context(Evento.RES_ALTA_EMPLEADO_KO, null);
	}
}
