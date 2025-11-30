package presentacion.Controller.Command.CommandTransporteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarTransporteCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATransporte().modificarTransporte((TTransporte) data);
		if (res > 0) {
			return new Context(Evento.RES_MODIFICAR_TRANSPORTE_OK, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_TRANSPORTE_KO, res);
		}

	}

}
