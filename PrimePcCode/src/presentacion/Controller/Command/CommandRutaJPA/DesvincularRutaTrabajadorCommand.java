package presentacion.Controller.Command.CommandRutaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TVinculacionRutaTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class DesvincularRutaTrabajadorCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSARuta()
				.desvincularRutaTrabajador((TVinculacionRutaTrabajador) data);
		if (res > 0)
			return new Context(Evento.RES_DESVINCULAR_RUTA_TRABAJADOR_OK, res);
		else
			return new Context(Evento.RES_DESVINCULAR_RUTA_TRABAJADOR_KO, null);
	}
}
