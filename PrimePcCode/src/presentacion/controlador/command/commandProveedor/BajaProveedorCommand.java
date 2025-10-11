package presentacion.controlador.command.commandProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BajaProveedorCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		int res = FactoriaNegocio.obtenerInstancia().generaSAProveedor().bajaProveedor((int) transfer);
		if (res > 0)
			return new Context(Evento.RES_BAJA_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_BAJA_PROVEEDOR_KO, null);
	}
}