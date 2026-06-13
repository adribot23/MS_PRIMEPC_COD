package presentacion.Controller.Command.CommandTransporteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TransporteJPA.TTransporteTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VincularTransporteTrabajadorCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATransporte()
				.vincularTransporteTrabajador((TTransporteTrabajador) data);
		if (res > 0) {
			return new Context(Evento.RES_VINCULAR_TRANSPORTE_TRABAJADOR_OK, res);
		} else {
			return new Context(Evento.RES_VINCULAR_TRANSPORTE_TRABAJADOR_KO, res);
		}

	}

}
