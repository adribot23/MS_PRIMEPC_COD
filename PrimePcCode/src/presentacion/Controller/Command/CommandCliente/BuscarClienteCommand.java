/**
 * 
 */
package presentacion.Controller.Command.CommandCliente;

import negocio.Cliente.TCliente;
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
public class BuscarClienteCommand implements Command {
	public Context execute(Object data) {
		int idCliente = (int) data;
		TCliente cliente =  SAAbstractFactory.getInstancia().generarSACliente().leerCliente(idCliente);

		if (cliente != null)
			return new Context(Evento.RES_BUSCAR_CLIENTE_OK, cliente);
		else
			return new Context(Evento.RES_BUSCAR_CLIENTE_KO, null);
	}
}