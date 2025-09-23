package integracion.factoria;


import integracion.daos.DAOAlmacen;
import integracion.daos.DAOCliente;
import integracion.daos.DAOEmpleado;
import integracion.daos.DAOProducto;
import integracion.daos.DAOProveedor;
import integracion.daos.DAOVenta;

public abstract class FactoriaIntegracion {
	private static FactoriaIntegracion instancia;
	public static FactoriaIntegracion obtenerInstancia()
	{ if (instancia== null)
	instancia = new FactoriaIntegracionImp();
	return instancia;
	}
	public abstract DAOAlmacen generaDAOAlmacen();
	public abstract DAOCliente generaDAOCliente();
	public abstract DAOEmpleado generaDAOEmpleado();
	public abstract DAOProducto generaDAOProducto();
	public abstract DAOProveedor generaDAOProveedor();
	public abstract DAOVenta generaDAOVenta();
}
