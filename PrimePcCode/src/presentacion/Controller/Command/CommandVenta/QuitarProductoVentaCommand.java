package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;

import negocio.Venta.SAVenta;
import negocio.Venta.TLineaVenta;

import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class QuitarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.QUITAR_PRODUCTO_VENTA, null);
		}

		if (!(data instanceof TLineaVenta)) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, "Los datos de la linea de venta no son validos.");
		}

		TLineaVenta linea = (TLineaVenta) data;

		if (linea.get_venta() <= 0 || linea.get_producto() <= 0 || linea.get_num_unidades() <= 0) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, "Debe indicar identificadores y unidades validas.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.quitarProductoVenta(linea);

			if (resultado > 0) {
				return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_OK, linea);
			} else {
				return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, "No se pudo eliminar el producto de la venta.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, ex.getMessage());
		}

	}
}
