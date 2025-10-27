
package integracion.FactoriaDAO;

import integracion.Almacen.DAOAlmacen;
import integracion.Almacen.DAOAlmacenImp;
import integracion.Cliente.DAOCliente;
import integracion.Cliente.DAOClienteImp;
import integracion.Empleado.DAOEmpleado;
import integracion.Empleado.DAOEmpleadoImp;
import integracion.Producto.DAOProducto;
import integracion.Producto.DAOProductoImp;
import integracion.Proveedor.DAOProveedor;
import integracion.Proveedor.DaoProveedorProducto;
import integracion.Proveedor.DaoProveedorProductoImp;
import integracion.Venta.DAOLineaVenta;
import integracion.Venta.DAOLineaVentaImp;
import integracion.Venta.DAOVenta;
import integracion.Venta.DAOVentaImp;

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