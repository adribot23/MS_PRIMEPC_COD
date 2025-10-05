package integracion.factoria;

import integracion.daos.DAOAlmacen;
import integracion.daos.DAOAlmacenImp;
import integracion.daos.DAOCliente;
import integracion.daos.DAOClienteImp;
import integracion.daos.DAOEmpleado;
import integracion.daos.DAOEmpleadoImp;
import integracion.daos.DAOProducto;
import integracion.daos.DAOProductoImp;
import integracion.daos.DAOProveedor;
import integracion.daos.DAOProveedorImp;
import integracion.daos.DAOVenta;
import integracion.daos.DAOVentaImp;

public class FactoriaIntegracionImp extends FactoriaIntegracion {

	@Override
	public DAOAlmacen generaDAOAlmacen() {
		return new DAOAlmacenImp();
	}

	@Override
	public DAOCliente generaDAOCliente() {
		return new DAOClienteImp();
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
	public DAOVenta generaDAOVenta() {
		return new DAOVentaImp();
	}

}
