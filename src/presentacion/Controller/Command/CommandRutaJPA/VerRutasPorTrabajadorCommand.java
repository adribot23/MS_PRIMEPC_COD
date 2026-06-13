package presentacion.Controller.Command.CommandRutaJPA;

import java.util.LinkedHashSet;
import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TRuta;
import negocio.RutaJPA.TVinculacionRutaTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerRutasPorTrabajadorCommand implements Command {

	@Override
	public Context execute(Object data) {
		Set<TVinculacionRutaTrabajador> vinculaciones = SAAbstractFactory.getInstancia()
				.generarSAVinculacionRutaTrabajador().listar_vinculaciones_por_trabajador((int) data);

		if (!vinculaciones.isEmpty()) {
			return new Context(Evento.RES_VER_RUTA_POR_TRABAJADOR_OK, vinculaciones);
		} else {
			return new Context(Evento.RES_VER_RUTA_POR_TRABAJADOR_KO, null);
		}

	}

}
