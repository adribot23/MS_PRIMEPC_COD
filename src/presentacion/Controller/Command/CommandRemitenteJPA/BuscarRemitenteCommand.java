package presentacion.Controller.Command.CommandRemitenteJPA;

import presentacion.GUI.Evento;
import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;

public class BuscarRemitenteCommand implements Command {

	@Override
	public Context execute(Object data) {
		TRemitente remitente = SAAbstractFactory.getInstancia().generarSARemitente().buscarRemitente((int) data);

		if (remitente != null)
			return new Context(Evento.RES_BUSCAR_REMITENTE_OK, remitente);
		else
			return new Context(Evento.RES_BUSCAR_REMITENTE_KO, remitente);
	}

}
