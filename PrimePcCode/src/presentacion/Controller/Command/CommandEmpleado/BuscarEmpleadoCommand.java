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
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class BuscarEmpleadoCommand implements Command {
	public Context execute(Object data) {
		TEmpleado tEmpleado = SAAbstractFactory.getInstancia().generarSAEmpleado().leerEmpleado((int) data);
		if (tEmpleado != null)
			return new Context(Evento.RES_BUSCAR_EMPLEADO_OK, tEmpleado);
		else
			return new Context(Evento.RES_BUSCAR_EMPLEADO_KO, null);
	}

}