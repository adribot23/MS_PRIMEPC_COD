
package presentacion.controlador.command.commandProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class BuscarProveedorCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TProveedor tProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor().leerProveedor((int) transfer);
		if (tProveedor != null)
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_OK, tProveedor);
		else
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_KO, null);
	}
}