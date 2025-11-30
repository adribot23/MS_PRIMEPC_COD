package presentacion.Controller.Command.CommandTransporteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaTransporteCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATransporte().bajaTransporte((int) data);
		if (res > 0) {
			return new Context(Evento.RES_BAJA_TRANSPORTE_OK, res);
		} else {
			return new Context(Evento.RES_BAJA_TRANSPORTE_KO, res);
		}
	}

}
