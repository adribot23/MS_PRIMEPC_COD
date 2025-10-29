
/**
 * 
 */
package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.TVentaTOA;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class BuscarVentaCommand implements Command {
	public Context execute(Object data) {
		TVentaTOA ventaCompleta = SAAbstractFactory.getInstancia().generarSAVenta().leerVenta((int) data);
		if (ventaCompleta != null)
			return new Context(Evento.RES_BUSCAR_VENTA_OK, ventaCompleta);
		else
			return new Context(Evento.RES_BUSCAR_VENTA_KO, null);
	}
}
