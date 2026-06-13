package presentacion.Controller.Command.CommandRutaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TRuta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BuscarRutaCommand implements Command {

	@Override
	public Context execute(Object data) {
		TRuta ruta = SAAbstractFactory.getInstancia().generarSARuta().buscar_ruta((int) data);
		if (ruta != null) {
			return new Context(Evento.RES_BUSCAR_RUTA_OK, ruta);
		} else {
			return new Context(Evento.RES_BUSCAR_RUTA_KO, null);
		}
	}

}
