package presentacion.controlador.command.commandProducto;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProducto;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class ModificarProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAProducto().modificarProducto((TProducto) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_PRODUCTO_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_PRODUCTO_KO, null);
	}
}
