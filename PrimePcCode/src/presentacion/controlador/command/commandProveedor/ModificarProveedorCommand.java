package presentacion.controlador.command.commandProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class ModificarProveedorCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaNegocio.obtenerInstancia().generaSAProveedor().modificarProveedor((TProveedor) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_PROVEEDOR_KO, null);
	}
}