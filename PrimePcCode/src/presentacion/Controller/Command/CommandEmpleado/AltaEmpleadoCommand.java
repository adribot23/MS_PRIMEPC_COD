/**
 * 
 */
package presentacion.Controller.Command.CommandEmpleado;

import negocio.Empleado.TEmpleado;
import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class AltaEmpleadoCommand implements Command {
	public Context execute(Object data) {
		int res =SAAbstractFactory.getInstancia().generarSAEmpleado().altaEmpleado((TEmpleado) data);
		if (res > 0)
			return new Context(Evento.RES_ALTA_EMPLEADO_OK, res);
		else
			return new Context(Evento.RES_ALTA_EMPLEADO_KO, null);
		// end-user-code
	}


}