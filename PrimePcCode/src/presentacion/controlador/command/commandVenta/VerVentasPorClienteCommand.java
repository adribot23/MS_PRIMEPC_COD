package presentacion.controlador.command.commandVenta;

import java.util.Collection;
import java.util.Set;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TVentaCompletaTOA;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class VerVentasPorClienteCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TVentaCompletaTOA> ventasCliente = FactoriaNegocio.obtenerInstancia().generaSAVenta()
				.leerVentasPorCliente((int) transfer);
		if (ventasCliente != null)
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_OK, ventasCliente);
		else
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO, ventasCliente);
	}
}
