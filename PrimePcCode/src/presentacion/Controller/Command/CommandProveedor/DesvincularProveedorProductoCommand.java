/**
 * 
 */
package presentacion.Controller.Command.CommandProveedor;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class DesvincularProveedorProductoCommand implements Command {
	public Context execute(Object data) {
		int[] datos = (int[]) data;
		int idProducto = datos[0];
		int idProveedor = datos[1];

		int res =  SAAbstractFactory.getInstancia().generarSAProveedor().desvincularProductoProveedor(idProducto,idProveedor);
		if (res > 0)
			return new Context(Evento.RES_DESVINCULAR_PRODUCTO_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_DESVINCULAR_PRODUCTO_PROVEEDOR_KO, null);
	}
}