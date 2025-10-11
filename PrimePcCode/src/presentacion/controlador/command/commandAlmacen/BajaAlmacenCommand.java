package presentacion.controlador.command.commandAlmacen;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BajaAlmacenCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int idAlmacen = (int) transfer;
		int res = FactoriaNegocio.obtenerInstancia().generaSAAlmacen().bajaAlmacen(idAlmacen);

		if (res > 0)
			return new Context(Evento.RES_BAJA_ALMACEN_OK, res);
		else
			return new Context(Evento.RES_BAJA_ALMACEN_KO, null);
	}
}