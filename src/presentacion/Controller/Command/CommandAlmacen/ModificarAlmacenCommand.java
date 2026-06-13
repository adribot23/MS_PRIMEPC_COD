/**
 * 
 */
package presentacion.Controller.Command.CommandAlmacen;

import negocio.Almacen.TAlmacen;
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
public class ModificarAlmacenCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAAlmacen().modificarAlmacen((TAlmacen) data);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_ALMACEN_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_ALMACEN_KO, null);
	}
}