package presentacion.controlador.command.commandProveedor;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class MostrarProveedoresCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TProveedor> proveedores = FactoriaNegocio.obtenerInstancia().generaSAProveedor()
				.leerTodosProveedores();
		if (proveedores != null && !proveedores.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_PROVEEDORES_OK, proveedores);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_PROVEEDORES_KO, null);
	}
}