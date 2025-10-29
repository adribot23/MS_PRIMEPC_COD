/**
 * 
 */
package integracion.Venta;

import java.util.Set;

import negocio.Venta.TVenta;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class DAOVentaImp implements DAOVenta {

	@Override
	public int create(TVenta venta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<TVenta> read_by_cliente(Integer idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVenta> read_by_empleado(Integer idEmpleado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TVenta read(Integer id_venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TVenta venta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer id_venta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<TVenta> read_all() {
		// TODO Auto-generated method stub
		return null;
	}
	
}