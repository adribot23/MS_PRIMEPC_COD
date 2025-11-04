/**
 * 
 */
package presentacion.Controller.Command.CommandProveedor;

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
public class BajaProveedorCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAProveedor().bajaProveedor((int) data);
		if (res > 0)
			return new Context(Evento.RES_BAJA_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_BAJA_PROVEEDOR_KO, null);
	}
}