package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TVenta;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class ModificarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAVenta().modificarVenta((TVenta) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_VENTA_OK, null);
		else
			return new Context(Evento.RES_MODIFICAR_VENTA_KO, null);
	}
}
