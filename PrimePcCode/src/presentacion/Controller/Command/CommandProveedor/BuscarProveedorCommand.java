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
public class BuscarProveedorCommand implements Command {
	public Context execute(Object data) {
		TProveedor tProveedor = SAAbstractFactory.getInstancia().generarSAProveedor().leerProveedor((int) data);
		if (tProveedor != null)
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_OK, tProveedor);
		else
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_KO, null);
	}
	}
