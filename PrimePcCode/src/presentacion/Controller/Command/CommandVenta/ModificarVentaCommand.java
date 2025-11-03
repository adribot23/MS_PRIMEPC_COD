package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;

import negocio.Venta.SAVenta;

import negocio.Venta.TVenta;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarVentaCommand implements Command {

	@Override
	public Context execute(Object data) {


		if (data == null) {
			return new Context(Evento.MODIFICAR_VENTA, null);
		}

		if (!(data instanceof TVenta)) {
			return new Context(Evento.RES_MODIFICAR_VENTA_KO, "Datos de venta incorrectos.");
		}

		try {
			SAVenta saVenta = SAAbstractFactory.getInstancia().generarSAVenta();
			int resultado = saVenta.modificarVenta((TVenta) data);

			if (resultado > 0) {
				return new Context(Evento.RES_MODIFICAR_VENTA_OK, data);
			} else {
				return new Context(Evento.RES_MODIFICAR_VENTA_KO, resultado);
			}
		} catch (Exception ex) {
			return new Context(Evento.RES_MODIFICAR_VENTA_KO, ex.getMessage());
		}

	}
}
