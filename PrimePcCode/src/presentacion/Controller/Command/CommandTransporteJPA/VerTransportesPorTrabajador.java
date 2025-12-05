package presentacion.Controller.Command.CommandTransporteJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.TransporteJPA.TTransporte;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerTransportesPorTrabajador implements Command {

	@Override
	public Context execute(Object data) {
		Set<TTransporte> transportes = SAAbstractFactory.getInstancia().generarSATransporte()
				.leerTransportesPorTrabajador((int) data);

		if (transportes != null && !transportes.isEmpty()) {
			return new Context(Evento.RES_VER_TRANSPORTE_POR_TRABAJADOR_OK, transportes);
		} else {
			return new Context(Evento.RES_VER_TRANSPORTE_POR_TRABAJADOR_KO, null);
		}

	}

}
