package presentacion.Controller.Command.CommandRutaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaRutaCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSARuta().bajaRuta((int) data);
		if (res > 0)
			return new Context(Evento.RES_BAJA_RUTA_OK, res);
		else
			return new Context(Evento.RES_BAJA_RUTA_KO, null);
	}
}
