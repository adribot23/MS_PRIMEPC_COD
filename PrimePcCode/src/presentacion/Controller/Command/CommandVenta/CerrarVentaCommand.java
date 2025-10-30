/**
 * 
 */
package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.TCarrito;
import negocio.Venta.TLineaVenta;
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
public class CerrarVentaCommand implements Command {
	public Context execute(Object data) {
		int id_venta =  SAAbstractFactory.getInstancia().generarSAVenta().cerrarVenta((TCarrito) data);
		if (id_venta > 0)
			return new Context(Evento.RES_CERRAR_VENTA_OK,id_venta);
		else
			return new Context(Evento.RES_CERRAR_VENTA_KO,null);
	}
}
