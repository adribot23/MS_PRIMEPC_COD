package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TCarritoFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class EliminarPaqueteCommand implements Command{
	public Context execute(Object data) {
		Integer res = SAAbstractFactory.getInstancia().generarSAFactura().eliminarPaquete((TCarritoFactura) data);

		if (res > 0) {
			return new Context(Evento.RES_QUITAR_PAQUETE_FACTURA_OK, data);
		} else {
			return new Context(Evento.RES_QUITAR_PAQUETE_FACTURA_KO, data);
		}
	}
}
