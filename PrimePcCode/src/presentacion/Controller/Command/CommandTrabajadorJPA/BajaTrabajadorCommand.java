package presentacion.Controller.Command.CommandTrabajadorJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaTrabajadorCommand implements Command{
	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATrabajador().bajaTrabajador((int) data);
		if (res > 0) {
			return new Context(Evento.RES_BAJA_TRABAJADOR_OK, res);
		} else {
			return new Context(Evento.RES_BAJA_TRABAJADOR_KO, res);
		}

	}

}
