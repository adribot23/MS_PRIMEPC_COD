/**
 * 
 */
package presentacion.Controller.Command.CommandProveedor;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Proveedor.TProveedor;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class AltaProveedorCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAProveedor().altaProveedor((TProveedor) data);
		if (res > 0)
			return new Context(Evento.RES_ALTA_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_ALTA_PROVEEDOR_KO, null);
	}
}