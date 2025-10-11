package presentacion.controlador.command.commandCliente;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCliente;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class AltaClienteCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSACliente().altaCliente((TCliente) transfer);
		if (res > 0)
			return new Context(Evento.RES_ALTA_CLIENTE_OK, res);
		else
			return new Context(Evento.RES_ALTA_CLIENTE_KO, null);
	}
}