package presentacion.controlador.command.commandProveedor;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProveedor;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class VerProvPorProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TProveedor> proveedores = (Collection<TProveedor>) FactoriaNegocio.obtenerInstancia()
				.generaSAProveedor().leerProveedorPorProducto((int) transfer);
		if (proveedores != null && !proveedores.isEmpty())
			return new Context(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK, proveedores);
		else
			return new Context(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO, null);
	}
}
