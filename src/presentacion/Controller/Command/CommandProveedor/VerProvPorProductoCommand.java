/**
 * 
 */
package presentacion.Controller.Command.CommandProveedor;

import java.util.Set;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Proveedor.TProveedor;
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
public class VerProvPorProductoCommand implements Command {
	public Context execute(Object data) {
		Set<TProveedor> proveedores = SAAbstractFactory.getInstancia().generarSAProveedor()
				.leerProveedorPorProducto((int) data);
		if (proveedores != null && !proveedores.isEmpty())
			return new Context(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK, proveedores);
		else
			return new Context(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO, null);
	}
}