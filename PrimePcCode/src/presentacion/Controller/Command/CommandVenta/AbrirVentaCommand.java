package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.SAVenta;
import negocio.Venta.TVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class AbrirVentaCommand implements Command {

	@Override
	public Context execute(Object data) {

		if (data == null) {
			return new Context(Evento.ABRIR_VENTA, null);
		}

		if (!(data instanceof TVenta)) {
			return new Context(Evento.RES_ABRIR_VENTA_KO, "Los datos de la venta no son válidos.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			Integer idGenerado = saVenta.altaVenta((TVenta) data);

			if (idGenerado != null && idGenerado > 0) {
				return new Context(Evento.RES_ABRIR_VENTA_OK, idGenerado);
			} else {
				return new Context(Evento.RES_ABRIR_VENTA_KO, idGenerado);
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_ABRIR_VENTA_KO, ex.getMessage());
		}
	}
}
