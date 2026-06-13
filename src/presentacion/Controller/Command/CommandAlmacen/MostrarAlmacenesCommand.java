/**
 * 
 */
package presentacion.Controller.Command.CommandAlmacen;

import java.util.Set;

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
public class MostrarAlmacenesCommand implements Command {
	public Context execute(Object data) {
		Set<TAlmacen> almacenes = SAAbstractFactory.getInstancia().generarSAAlmacen().leerTodosAlmacenes();

		if (almacenes != null && !almacenes.isEmpty())
			return new Context(Evento.RES_MOSTRAR_TODOS_ALMACENES_OK, almacenes);
		else
			return new Context(Evento.RES_MOSTRAR_TODOS_ALMACENES_KO, null);
	}
}