
package Integracion.FactoriaDAO;

import Integracion.Almacen.DAOAlmacen;
import Integracion.Cliente.DAOCliente;
import Integracion.Venta.DAOLineaVenta;
import Integracion.Venta.DAOVenta;
import Integracion.Empleado.DAOEmpleado;
import Integracion.Proveedor.DAOProveedor;
import Integracion.Proveedor.DaoProveedorProducto;
import Integracion.Producto.DAOProducto;

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

	
	public abstract DaoProveedorProducto generaDAOProveedorProducto(); 
}