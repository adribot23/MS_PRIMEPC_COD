package presentacion.Controller.Command.CommandRutaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TRuta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarRutaCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSARuta().modificar_ruta((TRuta) data);
		if (res > 0) {
			return new Context(Evento.RES_MODIFICAR_RUTA_OK, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_RUTA_KO, res);
		}

	}

}
