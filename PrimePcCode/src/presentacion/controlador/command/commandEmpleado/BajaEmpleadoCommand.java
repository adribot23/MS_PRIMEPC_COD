package presentacion.controlador.command.commandEmpleado;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TEmpleado;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BajaEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAEmpleado().bajaEmpleado((int) transfer);
		if (res > 0)
			return new Context(Evento.RES_BAJA_EMPLEADO_OK, res);
		else
			return new Context(Evento.RES_BAJA_EMPLEADO_KO, null);
	}
}