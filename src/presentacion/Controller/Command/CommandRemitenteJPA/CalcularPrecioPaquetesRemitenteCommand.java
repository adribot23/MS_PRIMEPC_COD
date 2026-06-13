package presentacion.Controller.Command.CommandRemitenteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CalcularPrecioPaquetesRemitenteCommand implements Command {

	@Override
	public Context execute(Object data) {
		double res = SAAbstractFactory.getInstancia().generarSARemitente().calcularPrecioPaquetesRemitente((int) data);

		if (res != 0) {
			return new Context(Evento.RES_CALCULAR_PRECIO_PAQUETES_OK, res);
		} else {
			return new Context(Evento.RES_CALCULAR_PRECIO_PAQUETES_KO, res);
		}
	}

}
