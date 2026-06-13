package integracion.factoria.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import integracion.Almacen.DAOAlmacen;
import integracion.Almacen.DAOAlmacenImp;
import integracion.Cliente.DAOCliente;
import integracion.Cliente.DAOClienteImp;
import integracion.Empleado.DAOEmpleado;
import integracion.Empleado.DAOEmpleadoImp;
import integracion.Producto.DAOProducto;
import integracion.Producto.DAOProductoImp;
import integracion.Proveedor.DAOProveedor;
import integracion.Proveedor.DAOProveedorImp;
import integracion.Venta.DAOVenta;
import integracion.Venta.DAOVentaImp;
import integracion.FactoriaDAO.*;

public class FactoriaDAOImpTest {

	private DAOAbstractFactory factoria;

	@Before
	public void setUp() {
		factoria = new DAOAbstractFactoryImp();
	}

	@Test
	public void testGeneraDAOAlmacen() {
		DAOAlmacen dao = factoria.generaDAOAlmacen();
		assertNotNull("DAOAlmacen no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOAlmacenImp", dao instanceof DAOAlmacenImp);
	}

	@Test
	public void testGeneraDAOCliente() {
		DAOCliente dao = factoria.generaDAOCliente();
		assertNotNull("DAOCliente no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOClienteImp", dao instanceof DAOClienteImp);
	}

	@Test
	public void testGeneraDAOEmpleado() {
		DAOEmpleado dao = factoria.generaDAOEmpleado();
		assertNotNull("DAOEmpleado no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOEmpleadoImp", dao instanceof DAOEmpleadoImp);
	}

	@Test
	public void testGeneraDAOProducto() {
		DAOProducto dao = factoria.generaDAOProducto();
		assertNotNull("DAOProducto no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOProductoImp", dao instanceof DAOProductoImp);
	}

	@Test
	public void testGeneraDAOProveedor() {
		DAOProveedor dao = factoria.generaDAOProveedor();
		assertNotNull("DAOProveedor no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOProveedorImp", dao instanceof DAOProveedorImp);
	}

	@Test
	public void testGeneraDAOVenta() {
		DAOVenta dao = factoria.generaDAOVenta();
		assertNotNull("DAOVenta no debería ser null", dao);
		assertTrue("Debería ser instancia de DAOVentaImp", dao instanceof DAOVentaImp);
	}
}
