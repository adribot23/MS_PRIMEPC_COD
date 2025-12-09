package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FacturaJPA.TCarritoFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class PasarCarritoFacturaACerrarCommand implements Command {
	@Override
	public Context execute(Object transfer) {
		if (!(transfer instanceof TCarritoFactura)) {
			return new Context(Evento.RES_PASAR_CARRITOFACTURA_A_CERRAR_KO, "Tipo de dato incorrecto en transfer");
		}
		TCarritoFactura carrito = (TCarritoFactura) transfer;

		return new Context(Evento.RES_PASAR_CARRITOFACTURA_A_CERRAR_OK, carrito);

	}
}
