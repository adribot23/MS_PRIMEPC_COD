package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCarrito;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class QuitarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAVenta().eliminarProductoCarrito((TCarrito) transfer);

		if (res > 0)
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_QUITAR_PRODUCTO_VENTA_KO, transfer);
	}

}
