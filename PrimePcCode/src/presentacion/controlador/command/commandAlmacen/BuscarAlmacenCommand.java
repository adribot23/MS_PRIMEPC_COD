package presentacion.controlador.command.commandAlmacen;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TAlmacen;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarAlmacenCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int idAlmacen = (int) transfer;
		TAlmacen almacen = FactoriaNegocio.obtenerInstancia().generaSAAlmacen().leerAlmacen(idAlmacen);

		if (almacen != null)
			return new Context(Evento.RES_BUSCAR_ALMACEN_OK, almacen);
		else
			return new Context(Evento.RES_BUSCAR_ALMACEN_KO, null);
	}
}
