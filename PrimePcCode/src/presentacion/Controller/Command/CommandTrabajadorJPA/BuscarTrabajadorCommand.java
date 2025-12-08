package presentacion.Controller.Command.CommandTrabajadorJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BuscarTrabajadorCommand implements Command{
	@Override
	public Context execute(Object data) {
		TTrabajador transporte = SAAbstractFactory.getInstancia().generarSATrabajador().leerTrabajador((int) data);
		if (transporte != null) {
			return new Context(Evento.RES_BUSCAR_TRABAJADOR_OK, transporte);
		} else {
			return new Context(Evento.RES_BUSCAR_TRABAJADOR_KO, null);
		}
	}
}
