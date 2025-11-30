package negocio.factoria.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import negocio.Almacen.SAAlmacen;
import negocio.Almacen.SAAlmacenImp;
import negocio.Cliente.SACliente;
import negocio.Cliente.SAClienteImp;
import negocio.Empleado.SAEmpleado;
import negocio.Empleado.SAEmpleadoImp;
import negocio.Producto.SAProducto;
import negocio.Producto.SAProductoImp;
import negocio.Proveedor.SAProveedor;
import negocio.Proveedor.SAProveedorImp;
import negocio.Venta.SAVenta;
import negocio.Venta.SAVentaImp;
import negocio.FactoriaSA.SAAbstractFactoryImp;

public class FactoriaNegocioImpTest {

	private SAAbstractFactoryImp factoria;

	@Before
	public void setUp() {
		factoria = new SAAbstractFactoryImp();
	}

	@Test
	public void testGeneraSAAlmacen() {
		SAAlmacen sa = factoria.generarSAAlmacen();
		assertNotNull("SAAlmacen no debería ser null", sa);
		assertTrue("Debería ser instancia de SAAlmacenImp", sa instanceof SAAlmacenImp);
	}

	@Test
	public void testGeneraSACliente() {
		SACliente sa = factoria.generarSACliente();
		assertNotNull("SACliente no debería ser null", sa);
		assertTrue("Debería ser instancia de SAClienteImp", sa instanceof SAClienteImp);
	}

	@Test
	public void testGeneraSAEmpleado() {
		SAEmpleado sa = factoria.generarSAEmpleado();
		assertNotNull("SAEmpleado no debería ser null", sa);
		assertTrue("Debería ser instancia de SAEmpleadoImp", sa instanceof SAEmpleadoImp);
	}

	@Test
	public void testGeneraSAProducto() {
		SAProducto sa = factoria.generarSAProducto();
		assertNotNull("SAProducto no debería ser null", sa);
		assertTrue("Debería ser instancia de SAProductoImp", sa instanceof SAProductoImp);
	}

	@Test
	public void testGeneraSAProveedor() {
		SAProveedor sa = factoria.generarSAProveedor();
		assertNotNull("SAProveedor no debería ser null", sa);
		assertTrue("Debería ser instancia de SAProveedorImp", sa instanceof SAProveedorImp);
	}

	@Test
	public void testGeneraSAVenta() {
		SAVenta sa = factoria.generarSAVenta();
		assertNotNull("SAVenta no debería ser null", sa);
		assertTrue("Debería ser instancia de SAVentaImp", sa instanceof SAVentaImp);
	}
}
