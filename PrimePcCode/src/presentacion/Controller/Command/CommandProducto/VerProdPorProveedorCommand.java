/**
 * 
 */
package presentacion.Controller.Command.CommandProducto;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Producto.SAProducto;
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
public class VerProdPorProveedorCommand implements Command {

	public Context execute(Object data) {
		Set<TProducto> productos = SAAbstractFactory.getInstancia().generarSAProducto()
				.leerProductosPorAlmacen((int) data);
		if (productos != null && !productos.isEmpty())
			return new Context(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK, productos);
		else
			return new Context(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO, null);
	}
}