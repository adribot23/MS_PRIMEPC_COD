/**
 * 
 */
package presentacion.Controller.Command.CommandProducto;

import negocio.FactoriaSA.SAAbstractFactory;
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
public class BajaProductoCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAProducto().bajaProducto((int) data);
		if (res > 0)
			return new Context(Evento.RES_BAJA_PRODUCTO_OK, res);
		else
			return new Context(Evento.RES_BAJA_PRODUCTO_KO, null);
	}
}
