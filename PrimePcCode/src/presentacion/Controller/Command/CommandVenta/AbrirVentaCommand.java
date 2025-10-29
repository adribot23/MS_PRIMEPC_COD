/**
 * 
 */
package presentacion.Controller.Command.CommandVenta;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Venta.TCarrito;
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
public class AbrirVentaCommand implements Command {
	public Context execute(Object data) {
		TCarrito carrito =SAAbstractFactory.getInstancia().generarSAVenta().abrirVenta((int) data);

		if (carrito != null)
			return new Context(Evento.RES_ABRIR_VENTA_OK, carrito);
		else
			return new Context(Evento.RES_ABRIR_VENTA_KO, null);
	}
}