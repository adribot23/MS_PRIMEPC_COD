package presentacion.Controller.Command.CommandRutaJPA;

import java.util.LinkedHashSet;
import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.RutaJPA.TVinculacionRutaTrabajador;
import negocio.TrabajadorJPA.TTrabajador;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerTrabajadoresPorRutaCommand implements Command {

	@Override
	public Context execute(Object data) {
		Set<TVinculacionRutaTrabajador> vinculaciones = SAAbstractFactory.getInstancia()
				.generarSAVinculacionRutaTrabajador().listar_vinculaciones_por_ruta((int) data);


		if (!vinculaciones.isEmpty()) {
			return new Context(Evento.RES_VER_TRABAJADOR_POR_RUTA_OK, vinculaciones);
		} else {
			return new Context(Evento.RES_VER_TRABAJADOR_POR_RUTA_KO, null);
		}

	}

}
