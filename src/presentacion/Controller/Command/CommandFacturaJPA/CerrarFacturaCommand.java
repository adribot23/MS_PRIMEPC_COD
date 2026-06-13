package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TCarritoFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CerrarFacturaCommand implements Command{
	public Context execute(Object transfer) {
		int res = SAAbstractFactory.getInstancia().generarSAFactura()
				.cerrarFactura((TCarritoFactura) transfer);
		if (res > 0)
			return new Context(Evento.RES_CERRAR_FACTURA_OK, transfer);
		else
			return new Context(Evento.RES_CERRAR_FACTURA_KO, transfer);

	}
}
