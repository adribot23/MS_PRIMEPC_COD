package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import negocio.Venta.TCarrito;

public class PasarCarritoAInsertarCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = SAAbstractFactory.getInstancia().generarSAVenta().pasar_carrito((TCarrito) transfer);
		if (result == 1)
			return new Context(Evento.RES_PASAR_CARRITO_A_INSERTAR_OK, transfer);
		else
			return new Context(Evento.RES_PASAR_CARRITO_A_INSERTAR_KO, transfer);
	}
}
