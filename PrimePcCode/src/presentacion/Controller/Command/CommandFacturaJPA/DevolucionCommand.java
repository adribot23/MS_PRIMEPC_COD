package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TLineaFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class DevolucionCommand implements Command{
	public Context execute(Object data) {
		Integer devolucion = SAAbstractFactory.getInstancia().generarSAFactura().devolucion((TLineaFactura) data);

		if (devolucion > 0) {
			return new Context(Evento.RES_DEVOLUCION_OK, null);
		} else {
			return new Context(Evento.RES_DEVOLUCION_KO, null);
		}
	}
}
