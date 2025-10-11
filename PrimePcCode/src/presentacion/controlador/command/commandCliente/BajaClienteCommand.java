package presentacion.controlador.command.commandCliente;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BajaClienteCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int idCliente = (int) transfer;
		int res = FactoriaNegocio.obtenerInstancia().generaSACliente().bajaCliente(idCliente);

		if (res > 0)
			return new Context(Evento.RES_BAJA_CLIENTE_OK, res);
		else
			return new Context(Evento.RES_BAJA_CLIENTE_KO, null);
	}
}
