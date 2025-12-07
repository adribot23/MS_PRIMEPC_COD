package presentacion.Controller.Command.CommandFacturaJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarFacturaCommand implements Command{
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAFactura().modificarFactura((TFactura) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_FACTURA_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_FACTURA_KO, res);

	}
}
