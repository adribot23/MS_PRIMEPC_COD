package presentacion.controlador.command.commandCliente;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCliente;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class ModificarClienteCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSACliente().modificarCliente((TCliente) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_CLIENTE_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_CLIENTE_KO, null);
	}
}
