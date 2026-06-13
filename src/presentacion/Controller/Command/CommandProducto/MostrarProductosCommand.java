/**
 * 
 */
package presentacion.Controller.Command.CommandProducto;

import java.util.Set;

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
public class MostrarProductosCommand implements Command {
	public Context execute(Object data) {
		Set<TProducto> productos = SAAbstractFactory.getInstancia().generarSAProducto().leerTodosProductos();
		if (productos != null && !productos.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_PRODUCTOS_OK, productos);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_PRODUCTOS_KO, null);
	}

}