package presentacion.controlador.command.commandVenta;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TVentaCompletaTOA;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class VerVentasPorEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TVentaCompletaTOA> ventasEmpleado = FactoriaNegocio.obtenerInstancia().generaSAVenta()
				.leerVentasPorEmpleado((int) transfer);
		if (ventasEmpleado != null)
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK, ventasEmpleado);
		else
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO, ventasEmpleado);
	}
}
