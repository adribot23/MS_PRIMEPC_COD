package presentacion.Controller.Command.CommandRemitenteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarRemitenteCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSARemitente().modificarRemitente((TRemitente) data);

		if (res > 0) {
			return new Context(Evento.RES_MODIFICAR_REMITENTE_OK, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_REMITENTE_KO, res);
		}
	}

}
