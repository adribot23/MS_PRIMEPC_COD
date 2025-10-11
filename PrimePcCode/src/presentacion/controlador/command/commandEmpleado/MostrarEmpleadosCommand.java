package presentacion.controlador.command.commandEmpleado;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TEmpleado;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class MostrarEmpleadosCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TEmpleado> empleados = FactoriaNegocio.obtenerInstancia().generaSAEmpleado().leerTodosEmpleados();
		if (empleados != null && !empleados.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_OK, empleados);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_KO, null);
	}
}