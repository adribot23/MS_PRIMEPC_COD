package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = SAAbstractFactory.getInstancia().generarSAVenta().eliminar_venta((Integer) transfer);
		if (result == 1)
			return new Context(Evento.RES_BAJA_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_BAJA_VENTA_KO, transfer);
	}
}
