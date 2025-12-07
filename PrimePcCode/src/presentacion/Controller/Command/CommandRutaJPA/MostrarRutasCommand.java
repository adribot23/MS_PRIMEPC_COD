package presentacion.Controller.Command.CommandRutaJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TRuta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class MostrarRutasCommand implements Command {

	@Override
	public Context execute(Object data) {
		Set<TRuta> rutas = SAAbstractFactory.getInstancia().generarSARuta().listar_rutas();
		if (rutas != null && !rutas.isEmpty()) {
			return new Context(Evento.RES_MOSTRAR_TODAS_RUTAS_OK, rutas);
		} else {
			return new Context(Evento.RES_MOSTRAR_TODAS_RUTAS_KO, null);
		}

	}

}
