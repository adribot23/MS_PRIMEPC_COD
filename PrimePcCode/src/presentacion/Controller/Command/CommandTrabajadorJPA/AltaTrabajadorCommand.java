package presentacion.Controller.Command.CommandTrabajadorJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class AltaTrabajadorCommand implements Command{
	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATrabajador().altaTrabajador((TTrabajador) data);
		if (res > 0) {
			return new Context(Evento.RES_ALTA_TRABAJADOR_OK, res);
		} else {
			return new Context(Evento.RES_ALTA_TRABAJADOR_KO, res);
		}

	}

}
