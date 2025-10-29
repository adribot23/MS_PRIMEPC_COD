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
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class InsertarProductoVentaCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAVenta().insertarProductoCarrito((TCarrito)data);
		if (res > 0)
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_OK, data);
		else
			return new Context(Evento.RES_INSERTAR_PRODUCTO_VENTA_KO, null);
	}
}