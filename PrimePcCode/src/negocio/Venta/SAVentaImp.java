/**
 * 
 */
package negocio.Venta;

import java.util.Set;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author adria
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SAVentaImp implements SAVenta {

	@Override
	public TCarrito abrirVenta(int data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cerrarVenta(TCarrito data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TVentaTOA leerVenta(int data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modificarVenta(TVenta data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int devolverVenta(TLineaVenta data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertarProductoCarrito(TCarrito data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminarProductoCarrito(TCarrito data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<TVentaTOA> leerTodasVentas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVentaTOA> leerVentasPorCliente(int data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVentaTOA> leerVentasPorEmpleado(int data) {
		// TODO Auto-generated method stub
		return null;
	}
	
}