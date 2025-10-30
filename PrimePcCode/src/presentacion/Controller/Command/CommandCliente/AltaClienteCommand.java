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
public class AltaClienteCommand implements Command {
	public Context execute(Object data) {
		int res = SAAbstractFactory.getInstancia().generarSACliente().altaCliente((TCliente) data);
		if (res > 0)
			return new Context(Evento.RES_ALTA_CLIENTE_OK, res);
		else
			return new Context(Evento.RES_ALTA_CLIENTE_KO, null);
	}
}