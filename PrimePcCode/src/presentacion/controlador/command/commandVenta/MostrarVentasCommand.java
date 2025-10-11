package presentacion.controlador.command.commandVenta;

import java.util.Collection;
import java.util.Set;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TVentaCompletaTOA;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class MostrarVentasCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TVentaCompletaTOA> ventas = FactoriaNegocio.obtenerInstancia().generaSAVenta().leerTodasVentas();
		if (ventas != null)
			return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_OK, ventas);
		else
			return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_KO, ventas);
	}
}
