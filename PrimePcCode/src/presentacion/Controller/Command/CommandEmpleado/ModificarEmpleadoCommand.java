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
public class ModificarEmpleadoCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSAEmpleado().modificarEmpleado((TEmpleado) data);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_EMPLEADO_OK, res);
		else
			return new Context(Evento.RES_MODIFICAR_EMPLEADO_KO, null);
	}

	
}