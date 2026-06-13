package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;
import negocio.Venta.TCarrito;

public class PasarCarritoACerrarCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		if (!(transfer instanceof TCarrito)) {
			return new Context(Evento.RES_PASAR_CARRITO_A_CERRAR_KO, "Tipo de dato incorrecto en transfer");
		}

		TCarrito carrito = (TCarrito) transfer;
		int result = SAAbstractFactory.getInstancia().generarSAVenta().pasar_carrito(carrito);

		if (result == 1)
			return new Context(Evento.RES_PASAR_CARRITO_A_CERRAR_OK, carrito);
		else
			return new Context(Evento.RES_PASAR_CARRITO_A_CERRAR_KO, carrito);
	}
}
