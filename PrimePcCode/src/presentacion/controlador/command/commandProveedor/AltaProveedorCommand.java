package presentacion.controlador.command.commandProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class AltaProveedorCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAProveedor().altaProveedor((TProveedor) transfer);
		if (res > 0)
			return new Context(Evento.RES_ALTA_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_ALTA_PROVEEDOR_KO, null);
	}
}
