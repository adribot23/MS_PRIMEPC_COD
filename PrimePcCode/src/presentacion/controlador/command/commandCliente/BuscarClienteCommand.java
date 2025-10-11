package presentacion.controlador.command.commandCliente;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCliente;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarClienteCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int idCliente = (int) transfer;
		TCliente cliente = FactoriaNegocio.obtenerInstancia().generaSACliente().leerCliente(idCliente);

		if (cliente != null)
			return new Context(Evento.RES_BUSCAR_CLIENTE_OK, cliente);
		else
			return new Context(Evento.RES_BUSCAR_CLIENTE_KO, null);
	}
}