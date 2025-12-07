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

		Set<TTrabajador> trabajadores = new LinkedHashSet<>();
		if (vinculaciones != null) {
			for (TVinculacionRutaTrabajador vinc : vinculaciones) {
				TTrabajador t = SAAbstractFactory.getInstancia().generarSATrabajador()
						.leerTrabajador(vinc.get_id_trabajador());
				if (t != null) {
					trabajadores.add(t);
				}
			}
		}

		if (!trabajadores.isEmpty()) {
			return new Context(Evento.RES_VER_TRABAJADOR_POR_RUTA_OK, trabajadores);
		} else {
			return new Context(Evento.RES_VER_TRABAJADOR_POR_RUTA_KO, null);
		}

	}

}
