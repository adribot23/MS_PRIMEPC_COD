package presentacion.Controller.Command.CommandVenta;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerVentasPorEmpleadoCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (data == null) {
			return new Context(Evento.MOSTRAR_VENTAS_POR_EMPLEADO, null);
		}

		if (!(data instanceof Integer)) {
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO, "Identificador de empleado no válido.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			Set<TVenta> ventas = saVenta.leerVentasPorEmpleado((Integer) data);

			if (ventas != null && !ventas.isEmpty()) {
				return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK, ventas);
			} else {
				return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO,
						"No se encontraron ventas para el empleado indicado.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO, ex.getMessage());
		}
	}
}
