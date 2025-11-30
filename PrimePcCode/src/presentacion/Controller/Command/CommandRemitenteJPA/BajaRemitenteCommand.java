package presentacion.Controller.Command.CommandRemitenteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RemitenteJPA.TRemitente;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaRemitenteCommand implements Command {

	@Override
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSARemitente().bajaRemitente((int) data);
		
		if(res > 0) {
			return new Context(Evento.RES_BAJA_REMITENTE_OK, res);
		} else {
			return new Context(Evento.RES_BAJA_REMITENTE_KO, res);
		}
		
	}

}
