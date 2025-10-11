package presentacion.controlador.command.commandProducto;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BajaProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAProducto().bajaProducto((int) transfer);
		if (res > 0)
			return new Context(Evento.RES_BAJA_PRODUCTO_OK, res);
		else
			return new Context(Evento.RES_BAJA_PRODUCTO_KO, null);
	}
}