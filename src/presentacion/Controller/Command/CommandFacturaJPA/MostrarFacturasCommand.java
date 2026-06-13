package presentacion.Controller.Command.CommandFacturaJPA;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.FacturaJPA.TFactura;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class MostrarFacturasCommand implements Command {
	public Context execute(Object data) {
		Set<TFactura> facturas = SAAbstractFactory.getInstancia().generarSAFactura().mostrarFacturas();

		if (facturas != null && !facturas.isEmpty()) {
			return new Context(Evento.RES_MOSTRAR_TODAS_FACTURAS_OK, facturas);
		} else {
			return new Context(Evento.RES_MOSTRAR_TODAS_FACTURAS_KO, null);
		}
	}
}
