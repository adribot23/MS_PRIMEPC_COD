package presentacion.Controller.Command.CommandFacturaJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ListarFacturasPorRemitenteCommand implements Command{
	public Context execute(Object data) {
		Set<TFactura> facturas = SAAbstractFactory.getInstancia().generarSAFactura().listarFacturasPorRemitente((Integer) data);

		if (facturas != null && !!facturas.isEmpty()) {
			return new Context(Evento.RES_QUITAR_PAQUETE_FACTURA_OK, facturas);
		} else {
			return new Context(Evento.RES_QUITAR_PAQUETE_FACTURA_KO, null);
		}
	}
}
