/**
 * 
 */
package presentacion.Controller.Command.CommandVenta;

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
public class MostrarVentasCommand implements Command {
	public Context execute(Object data) {
		Set<TVentaTOA> ventas =  SAAbstractFactory.getInstancia().generarSAVenta().leerTodasVentas();
		if (ventas != null)
			return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_OK, ventas);
		else
			return new Context(Evento.RES_MOSTRAR_TODAS_VENTAS_KO, ventas);
	}
}