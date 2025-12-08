package presentacion.Controller.Command.CommandTrabajadorJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class MostrarTrabajadorCommand implements Command{
	@Override
	public Context execute(Object data) {
		Set<TTrabajador> trabajadores = SAAbstractFactory.getInstancia().generarSATrabajador().leerTodosTrabajadores();
		if (trabajadores != null && !trabajadores.isEmpty()) {
			return new Context(Evento.RES_MOSTRAR_TODOS_TRABAJADORES_OK, trabajadores);
		} else {
			return new Context(Evento.RES_MOSTRAR_TODOS_TRABAJADORES_KO, null);
		}

	}
}
