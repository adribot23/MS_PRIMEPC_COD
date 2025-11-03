package presentacion.Controller.Command.CommandVenta;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class MostrarVentasCommand implements Command {

	@Override
	public Context execute(Object data) {

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			Set<TVenta> listado = saVenta.leerTodasVentas();

			if (listado != null) {
				return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_OK, listado);
			} else {
				return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_KO, "No hay ventas registradas.");
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_KO, ex.getMessage());
		}
	}
}
