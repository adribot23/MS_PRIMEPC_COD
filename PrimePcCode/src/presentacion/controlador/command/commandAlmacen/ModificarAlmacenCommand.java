package presentacion.controlador.command.commandAlmacen;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TAlmacen;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class ModificarAlmacenCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAAlmacen().modificarAlmacen((TAlmacen) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_ALMACEN_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_ALMACEN_KO, null);
	}
}