package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TCarrito;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class InsertarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object data) {
		if (data == null) {
			return new Context(Evento.INSERTAR_PRODUCTO_VENTA, null);
		}

		if (!(data instanceof TCarrito)) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO,
					"Los datos del carrito no son validos.");
		}

		TCarrito carrito = (TCarrito) data;

		if (carrito.getId() <= 0 || carrito.getidProducto() <= 0 || carrito.getcantidadProducto() <= 0) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO,
					"Debe indicar identificadores y unidades validas.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.insertarProductoCarrito(carrito);

			if (resultado > 0) {
				return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_OK, carrito);
			} else {
				return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, "No se pudo añadir el producto a la venta.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, ex.getMessage());
		}
	}
}
