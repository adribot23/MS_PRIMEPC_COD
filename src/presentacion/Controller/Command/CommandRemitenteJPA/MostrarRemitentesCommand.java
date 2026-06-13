package presentacion.Controller.Command.CommandRemitenteJPA;

import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RemitenteJPA.TRemitente;

public class MostrarRemitentesCommand implements Command {

	public Context execute(Object data) {

		Set<TRemitente> remitentes = SAAbstractFactory.getInstancia().generarSARemitente().listarTodosRemitentes();

		if (remitentes != null && !remitentes.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_REMITENTES_OK, remitentes);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_REMITENTES_KO, remitentes);
	}
}
