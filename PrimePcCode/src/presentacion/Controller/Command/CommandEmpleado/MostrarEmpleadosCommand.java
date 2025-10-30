/**
 * 
 */
package presentacion.Controller.Command.CommandEmpleado;

import java.util.Set;

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
public class MostrarEmpleadosCommand implements Command{
	public Context execute(Object data) {
		Set<TEmpleado> empleados = SAAbstractFactory.getInstancia().generarSAEmpleado().leerTodosEmpleados();
		if (empleados != null && !empleados.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_OK, empleados);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_KO, null);
	}


}