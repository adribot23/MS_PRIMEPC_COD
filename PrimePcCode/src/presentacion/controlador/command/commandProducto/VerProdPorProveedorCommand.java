package presentacion.controlador.command.commandProducto;

import java.util.Collection;

import negocio.factoria.FactoriaNegocio;
import negocio.transfers.TProducto;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class VerProdPorProveedorCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Collection<TProducto> productos = FactoriaNegocio.obtenerInstancia().generaSAProducto()
				.leerProductosPorAlmacen((int) transfer);
		if (productos != null && !productos.isEmpty())
			return new Context(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK, productos);
		else
			return new Context(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO, null);
	}
}
