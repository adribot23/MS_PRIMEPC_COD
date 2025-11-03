package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;

import negocio.Venta.SAVenta;

import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;


public class BuscarVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.BUSCAR_VENTA, null);
		}

		if (!(data instanceof Integer)) {
			return new Context(Evento.RES_BUSCAR_VENTA_KO, "Identificador de venta no válido.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			Object venta = saVenta.leerVenta((Integer) data);

			if (venta != null) {
				return new Context(Evento.RES_BUSCAR_VENTA_OK, venta);
			} else {
				return new Context(Evento.RES_BUSCAR_VENTA_KO, data);
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_BUSCAR_VENTA_KO, ex.getMessage());
		}
	}
}
