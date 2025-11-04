package presentacion.Controller.Command.CommandVenta;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerVentasPorClienteCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.MOSTRAR_VENTAS_POR_CLIENTE, null);
		}

		if (!(data instanceof Integer)) {
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO, "Identificador de cliente no válido.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int idCliente = (Integer) data;
			Set<TVenta> ventas = saVenta.leerVentasPorCliente(idCliente);

			if (ventas != null && !ventas.isEmpty()) {
				return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_OK, ventas);
			} else {
				return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO,
						"No se encontraron ventas para el cliente indicado.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO, ex.getMessage());
		}

	}
}
