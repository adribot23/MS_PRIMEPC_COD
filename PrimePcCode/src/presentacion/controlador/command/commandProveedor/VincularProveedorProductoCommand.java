package presentacion.controlador.command.commandProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.Context;
import presentacion.controlador.command.Command;
import presentacion.factoria.Evento;

public class VincularProveedorProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int[] datos = (int[]) transfer;
		int idProducto = datos[0];
		int idProveedor = datos[1];

		int res = FactoriaNegocio.obtenerInstancia().generaSAProveedor().vincularProductoProveedor(idProducto,
				idProveedor);
		if (res > 0)
			return new Context(Evento.RES_VINCULAR_PRODUCTO_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_VINCULAR_PRODUCTO_PROVEEDOR_KO, null);
	}
}
