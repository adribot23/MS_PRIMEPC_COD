package presentacion.controlador.command.commandAlmacen;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TAlmacen;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class MostrarAlmacenesCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TAlmacen> almacenes = FactoriaNegocio.obtenerInstancia().generaSAAlmacen().leerTodosAlmacenes();

		if (almacenes != null && !almacenes.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_ALMACENES_OK, almacenes);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_ALMACENES_KO, null);
	}
}