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
public class VerVentasPorEmpleadoCommand implements Command {
	public Context execute(Object data) {
		Set<TVentaTOA> ventasEmpleado = SAAbstractFactory.getInstancia().generarSAVenta().leerVentasPorEmpleado((int)data);
		if (ventasEmpleado != null)
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK, ventasEmpleado);
		else
			return new Context(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO, null);
	}
}