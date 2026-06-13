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
public class BuscarAlmacenCommand implements Command {
	public Context execute(Object data) {
		int idAlmacen = (int) data;
		TAlmacen almacen = SAAbstractFactory.getInstancia().generarSAAlmacen().leerAlmacen(idAlmacen);

		if (almacen != null)
			return new Context(Evento.RES_BUSCAR_ALMACEN_OK, almacen);
		else
			return new Context(Evento.RES_BUSCAR_ALMACEN_KO, null);
	}
}