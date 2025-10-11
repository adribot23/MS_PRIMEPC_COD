package presentacion.controlador.command.commandProducto;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProducto;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TProducto tProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto().leerProducto((int) transfer);
		if (tProducto != null)
			return new Context(Evento.RES_BUSCAR_PRODUCTO_OK, tProducto);
		else
			return new Context(Evento.RES_BUSCAR_PRODUCTO_KO, null);
	}
}
