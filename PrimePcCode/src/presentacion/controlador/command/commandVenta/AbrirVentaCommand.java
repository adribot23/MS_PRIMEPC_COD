package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCarrito;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class AbrirVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TCarrito carrito = FactoriaNegocio.obtenerInstancia().generaSAVenta().abrirVenta((int) transfer);

		if (carrito != null)
			return new Context(Evento.RES_ABRIR_VENTA_OK, carrito);
		else
			return new Context(Evento.RES_ABRIR_VENTA_KO, null);
	}
}
