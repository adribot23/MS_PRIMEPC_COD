package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TCarrito;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class AbrirVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.ABRIR_VENTA, null);
		}

		if (!(data instanceof Integer)) {
			return new Context(Evento.RES_ABRIR_VENTA_KO, "El ID del empleado no es válido.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int idEmpleado = (Integer) data;
			TCarrito carrito = saVenta.abrirVenta(idEmpleado);

			if (carrito != null && carrito.getId() > 0) {
				return new Context(Evento.RES_ABRIR_VENTA_OK, carrito);
			} else {
				return new Context(Evento.RES_ABRIR_VENTA_KO, carrito);
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_ABRIR_VENTA_KO, ex.getMessage());
		}
	}
}
