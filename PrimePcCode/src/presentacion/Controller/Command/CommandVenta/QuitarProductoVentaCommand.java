package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TCarrito;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class QuitarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (data == null) {
			return new Context(Evento.QUITAR_PRODUCTO_VENTA, null);
		}

		if (!(data instanceof TCarrito)) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO,
					"Los datos del carrito no son validos.");
		}

		TCarrito carrito = (TCarrito) data;

		if (carrito.getId() <= 0 || carrito.getidProducto() <= 0 || carrito.getcantidadProducto() <= 0) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO,
					"Debe indicar identificadores y unidades validas.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.eliminarProductoCarrito(carrito);

			if (resultado > 0) {
				return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_OK, carrito);
			} else {
				return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO,
						"No se pudo eliminar el producto de la venta.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, ex.getMessage());
		}
	}
}
