
package integracion.FactoriaDAO;

import integracion.Almacen.DAOAlmacen;
import integracion.Cliente.DAOCliente;
import integracion.Empleado.DAOEmpleado;
import integracion.Producto.DAOProducto;
import integracion.Proveedor.DAOProveedor;
import integracion.Proveedor.DAOProveedorProducto;
import integracion.Venta.DAOLineaVenta;
import integracion.Venta.DAOVenta;

public abstract class DAOAbstractFactory {

	private static DAOAbstractFactory instancia;

	public static synchronized DAOAbstractFactory getInstancia() {
		if (instancia == null) {
			instancia = new DAOAbstractFactoryImp();
		}
		return instancia;
	}


	public abstract DAOAlmacen generaDAOAlmacen(); 


	public abstract DAOCliente generaDAOCliente();
	public abstract DAOVenta generaDAOVenta(); 
	public abstract DAOLineaVenta generaDAOLineaVenta();
	
	public abstract DAOEmpleado generaDAOEmpleado(); 

	public abstract DAOProducto generaDAOProducto(); 

	
	public abstract DAOProveedor generaDAOProveedor(); 

	
	public abstract DAOProveedorProducto generaDAOProveedorProducto(); 
}