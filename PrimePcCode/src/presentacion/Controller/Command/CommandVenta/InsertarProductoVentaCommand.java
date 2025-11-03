package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TLineaVenta;

import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class InsertarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.INSERTAR_PRODUCTO_VENTA, null);
		}

		if (!(data instanceof TLineaVenta)) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, "Los datos de la linea de venta no son validos.");
		}

		TLineaVenta linea = (TLineaVenta) data;

		if (linea.get_venta() <= 0 || linea.get_producto() <= 0 || linea.get_num_unidades() <= 0) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO,
					"Debe indicar identificadores y unidades validas.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.insertarProductoVenta(linea);

			if (resultado > 0) {
				return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_OK, linea);
			} else {
				return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, "No se pudo añadir el producto a la venta.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, ex.getMessage());
		}

	}
}
