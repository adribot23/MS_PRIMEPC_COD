
package Integracion.FactoriaDAO;

import Integracion.Almacen.DAOAlmacen;
import Integracion.Almacen.DAOAlmacenImp;
import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.DAOClienteImp;
import Integracion.Empleado.DAOEmpleado;
import Integracion.Empleado.DAOEmpleadoImp;
import Integracion.Producto.DAOProducto;
import Integracion.Producto.DAOProductoImp;
import Integracion.Proveedor.DAOProveedor;
import Integracion.Proveedor.DaoProveedorProducto;
import Integracion.Proveedor.DaoProveedorProductoImp;
import Integracion.Venta.DAOLineaVenta;
import Integracion.Venta.DAOLineaVentaImp;
import Integracion.Venta.DAOVenta;
import Integracion.Venta.DAOVentaImp;

public class DAOAbstractFactoryImp extends DAOAbstractFactory {

	@Override
	public DAOAlmacen generaDAOAlmacen() {
		return new DAOAlmacenImp();
	}

	@Override
	public DAOCliente generaDAOCliente() {
		return new DAOClienteImp();
	}

	@Override
	public DAOVenta generaDAOVenta() {
		return new DAOVentaImp();
	}

	@Override
	public DAOLineaVenta generaDAOLineaVenta() {
		return new DAOLineaVentaImp();
	}

	@Override
	public DAOEmpleado generaDAOEmpleado() {
		return new DAOEmpleadoImp();
	}

	@Override
	public DAOProducto generaDAOProducto() {
		return new DAOProductoImp();
	}

	@Override
	public DAOProveedor generaDAOProveedor() {
		return new DAOProveedorImp();
	}

	@Override
	public DaoProveedorProducto generaDAOProveedorProducto() {
		return new DaoProveedorProductoImp();
	}
}