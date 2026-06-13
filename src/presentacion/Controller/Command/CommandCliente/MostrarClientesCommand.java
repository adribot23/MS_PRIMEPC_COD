/**
 * 
 */
package presentacion.Controller.Command.CommandCliente;

import java.util.Set;

import negocio.Cliente.TCliente;
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
public class MostrarClientesCommand implements Command {
	public Context execute(Object data) {
		Set<TCliente> clientes = SAAbstractFactory.getInstancia().generarSACliente().leerTodosClientes();

		if (clientes != null && !clientes.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_CLIENTES_OK, clientes);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_CLIENTES_KO, null);
	}
}
