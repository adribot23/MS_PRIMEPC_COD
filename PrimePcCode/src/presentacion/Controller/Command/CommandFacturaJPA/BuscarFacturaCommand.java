package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TFacturaTOA;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BuscarFacturaCommand implements Command {
	public Context execute(Object data) {

		TFacturaTOA tFacturaTOA = SAAbstractFactory.getInstancia().generarSAFactura()
				.buscarFactura((Integer) data);

		if (tFacturaTOA != null)
			return new Context(Evento.RES_BUSCAR_FACTURA_OK, tFacturaTOA);
		else
			return new Context(Evento.RES_BUSCAR_FACTURA_KO, null);

	}
}
