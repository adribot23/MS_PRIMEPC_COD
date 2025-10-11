package presentacion.controlador.command.commandVenta;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TVentaCompletaTOA;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TVentaCompletaTOA ventaCompletaBus = FactoriaNegocio.obtenerInstancia().generaSAVenta()
				.leerVenta((int) transfer);
		if (ventaCompletaBus != null)
			return new Context(Evento.RES_BUSCAR_VENTA_OK, ventaCompletaBus);
		else
			return new Context(Evento.RES_BUSCAR_VENTA_KO, ventaCompletaBus);
	}

}
