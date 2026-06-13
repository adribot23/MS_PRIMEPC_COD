package presentacion.Controller.Command.CommandTransporteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BuscarTransporteCommand implements Command {

	@Override
	public Context execute(Object data) {
		TTransporte transporte = SAAbstractFactory.getInstancia().generarSATransporte().leerTransporte((int) data);
		if (transporte != null) {
			return new Context(Evento.RES_BUSCAR_TRANSPORTE_OK, transporte);
		} else {
			return new Context(Evento.RES_BUSCAR_TRANSPORTE_KO, null);
		}
	}

}
