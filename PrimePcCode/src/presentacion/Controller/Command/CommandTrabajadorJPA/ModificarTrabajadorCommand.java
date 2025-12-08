package presentacion.Controller.Command.CommandTrabajadorJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarTrabajadorCommand implements Command{
	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSATrabajador().modificarTrabajador((TTrabajador) data);
		if (res > 0) {
			return new Context(Evento.RES_MODIFICAR_TRABAJADOR_OK, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_TRABAJADOR_KO, res);
		}

	}
}
