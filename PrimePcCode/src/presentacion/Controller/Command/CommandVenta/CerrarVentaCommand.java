package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TCarrito;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CerrarVentaCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (data == null) {
			return new Context(Evento.CERRAR_VENTA, null);
		}

		if (!(data instanceof TCarrito)) {
			return new Context(Evento.RES_CERRAR_VENTA_KO, "Los datos del carrito no son válidos.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.cerrarVenta((TCarrito) data);

			if (resultado > 0) {
				return new Context(Evento.RES_CERRAR_VENTA_OK, resultado);
			} else {
				return new Context(Evento.RES_CERRAR_VENTA_KO,
						"No se pudo cerrar la venta.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_CERRAR_VENTA_KO, ex.getMessage());
		}
	}
}
