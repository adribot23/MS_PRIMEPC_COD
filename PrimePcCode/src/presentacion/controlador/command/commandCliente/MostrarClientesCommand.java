package presentacion.controlador.command.commandCliente;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TCliente;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class MostrarClientesCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TCliente> clientes = FactoriaNegocio.obtenerInstancia().generaSACliente().leerTodosClientes();

		if (clientes != null && !clientes.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_CLIENTES_OK, clientes);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_CLIENTES_KO, null);
	}
}
