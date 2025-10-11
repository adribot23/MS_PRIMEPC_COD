package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCarrito;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class CerrarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int id_venta = FactoriaNegocio.obtenerInstancia().generaSAVenta().cerrarVenta((TCarrito) transfer);
		if (id_venta > 0)
			return new Context(Evento.RES_CERRAR_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_CERRAR_VENTA_KO, transfer);
	}

}