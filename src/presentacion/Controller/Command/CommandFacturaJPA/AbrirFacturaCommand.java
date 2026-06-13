package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TCarritoFactura;
import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class AbrirFacturaCommand implements Command {

	@Override
	public Context execute(Object data) {
		TCarritoFactura carrito = SAAbstractFactory.getInstancia().generarSAFactura().abrirFactura((TFactura) data);
		if (carrito != null) {
			return new Context(Evento.RES_ABRIR_FACTURA_OK, carrito);
		} else {
			return new Context(Evento.RES_ABRIR_FACTURA_KO, null);
		}

	}

}
