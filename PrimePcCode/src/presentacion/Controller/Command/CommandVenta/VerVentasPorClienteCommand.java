/**
 * 
 */
package presentacion.Controller.Command.CommandVenta;

import java.util.Collection;
import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.TVentaTOA;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VerVentasPorClienteCommand implements Command {
	public Context execute(Object data) {
		Set<TVentaTOA> ventasCliente =SAAbstractFactory.getInstancia().generarSAVenta().leerVentasPorCliente((int) data);
		if (ventasCliente != null)
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_OK, ventasCliente);
		else
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO, null);
	}
}