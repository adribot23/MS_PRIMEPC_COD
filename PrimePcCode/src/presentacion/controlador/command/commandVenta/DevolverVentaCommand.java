package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TLineaVenta;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class DevolverVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaNegocio.obtenerInstancia().generaSAVenta().devolverVenta((TLineaVenta) transfer);

		if (result == -1)
			return new Context(Evento.RES_DEVOLVER_VENTA_KO, transfer);
		else
			return new Context(Evento.RES_DEVOLVER_VENTA_OK, transfer);
	}
}
