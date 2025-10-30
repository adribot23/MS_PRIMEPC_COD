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
public interface SAVenta {
	

	public TCarrito abrirVenta(int data);

	public int cerrarVenta(TCarrito data);
	
	public TVentaTOA leerVenta(int data);

	public int modificarVenta(TVenta data);
	
	public int devolverVenta(TLineaVenta data);

	public int insertarProductoCarrito(TCarrito data);

	public int eliminarProductoCarrito(TCarrito data);
	
	public Set<TVentaTOA> leerTodasVentas();

	public Set<TVentaTOA> leerVentasPorCliente(int data);

	public Set<TVentaTOA> leerVentasPorEmpleado(int data);

	



	
}