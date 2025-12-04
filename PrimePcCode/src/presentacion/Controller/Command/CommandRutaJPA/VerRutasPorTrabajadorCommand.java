package presentacion.Controller.Command.CommandRutaJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TRuta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerRutasPorTrabajadorCommand implements Command {

	@Override
	public Context execute(Object data) {
		Set<TRuta> rutas = SAAbstractFactory.getInstancia().generarSARuta()
				.mostrarRutasPorTrabajador((int) data);
		if (rutas != null)
			return new Context(Evento.RES_VER_RUTA_POR_TRABAJADOR_OK, rutas);
		else
			return new Context(Evento.RES_VER_RUTA_POR_TRABAJADOR_KO, null);
	}
}
