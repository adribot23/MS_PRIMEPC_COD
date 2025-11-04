package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CerrarVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.CERRAR_VENTA, null);
		}

		if (!(data instanceof Integer)) {
			return new Context(Evento.RES_CERRAR_VENTA_KO, "Identificador de venta no válido.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			Integer resultado = saVenta.bajaVenta((Integer) data);

			if (resultado != null && resultado > 0) {
				return new Context(Evento.RES_CERRAR_VENTA_OK, data);
			} else {
				return new Context(Evento.RES_CERRAR_VENTA_KO,
						"No se pudo cerrar la venta " + String.valueOf(data) + ".");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_CERRAR_VENTA_KO, ex.getMessage());
		}
	}
}
