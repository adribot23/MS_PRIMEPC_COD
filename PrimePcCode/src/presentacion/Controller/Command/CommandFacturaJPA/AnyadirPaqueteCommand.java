package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TCarritoFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class AnyadirPaqueteCommand implements Command{
	public Context execute(Object data) {
		Integer res = SAAbstractFactory.getInstancia().generarSAFactura().anyadirPaquete((TCarritoFactura) data);

		if (res > 0) {
			return new Context(Evento.RES_INSERTAR_PAQUETE_FACTURA_OK, null);
		} else {
			return new Context(Evento.RES_INSERTAR_PAQUETE_FACTURA_KO, null);
		}
	}
}
