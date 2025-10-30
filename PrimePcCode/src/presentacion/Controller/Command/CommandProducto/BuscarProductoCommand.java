/**
 * 
 */
package presentacion.Controller.Command.CommandProducto;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Producto.TProducto;
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
public class BuscarProductoCommand implements Command {
	public Context execute(Object data) {
		TProducto tProducto = SAAbstractFactory.getInstancia().generarSAProducto().leerProducto((int) data);
		if (tProducto != null)
			return new Context(Evento.RES_BUSCAR_PRODUCTO_OK, tProducto);
		else
			return new Context(Evento.RES_BUSCAR_PRODUCTO_KO, null);
	}
}
