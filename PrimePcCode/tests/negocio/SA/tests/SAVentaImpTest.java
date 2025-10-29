package negocio.SA.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import negocio.Almacen.SAAlmacen;
import negocio.Almacen.TAlmacen;
import negocio.Cliente.SACliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;
import negocio.Empleado.SAEmpleado;
import negocio.Empleado.TEmpleadoParcial;
import negocio.Producto.SAProducto;
import negocio.Producto.TProducto;
import negocio.Proveedor.SAProveedor;
import negocio.Proveedor.TProveedor;
import negocio.Venta.SAVenta;
import negocio.Venta.TVenta;


public class SAVentaImpTest {
/*
	private SACliente saCliente;
	private SAEmpleado saEmpleado;
	private SAProveedor saProveedor;
	private SAAlmacen saAlmacen;
	private SAProducto saProducto;
	private SAVenta saVenta;

	private int idCliente = 0;
	private int idEmpleado = 0;
	private int idProveedor = 0;
	private int idAlmacen = 0;
	private int idProducto = 0;
	private int idVenta = 0;

	private int idClienteSocio = 0;
	private int idProductoDescuento = 0;
	private int idVentaConDescuento = 0;

	@Before
	public void setUp() {
		saCliente = FactoriaNegocio.obtenerInstancia().generaSACliente();
		saEmpleado = FactoriaNegocio.obtenerInstancia().generaSAEmpleado();
		saProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		saAlmacen = FactoriaNegocio.obtenerInstancia().generaSAAlmacen();
		saProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		saVenta = FactoriaNegocio.obtenerInstancia().generaSAVenta();

		// Crear un cliente no socio
		TClienteNoSocio cliente = new TClienteNoSocio("Cliente Test", "12469678A", 0);
		idCliente = saCliente.altaCliente(cliente);
		assertTrue(idCliente > 0);

		// Crear un empleado parcial
		TEmpleadoParcial empleado = new TEmpleadoParcial(0, "Empleado Test", "87654321B", "600600600", 20);
		idEmpleado = saEmpleado.altaEmpleado(empleado);
		assertTrue(idEmpleado > 0);

		// Crear un proveedor
		TProveedor proveedor = new TProveedor();
		proveedor.setNombre("Proveedor Test");
		proveedor.setActivo(1);
		idProveedor = saProveedor.altaProveedor(proveedor);
		assertTrue(idProveedor > 0);

		// Crear un almacén
		TAlmacen almacen = new TAlmacen();
		almacen.setNombre("Almacen Test");
		almacen.setCapacidadMaxima(1000);
		almacen.setOcupacion(0);
		almacen.setActivo(1);
		idAlmacen = saAlmacen.altaAlmacen(almacen);
		assertTrue(idAlmacen > 0);
	}

	@After
	public void tearDown() {
		if (idCliente > 0)
			eliminarFisicamenteCliente(idCliente);

		if (idProducto > 0) {
			saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
			saProveedor.desvincularProductoProveedor(idProducto, idProveedor);
			eliminarFisicamenteProducto(idProducto);

		}

		if (idProductoDescuento > 0) {
			saAlmacen.desvincularProductoAlmacen(idProductoDescuento, idAlmacen);
			saProveedor.desvincularProductoProveedor(idProductoDescuento, idProveedor);
			eliminarFisicamenteProducto(idProductoDescuento);
		}

		if (idEmpleado > 0)
			eliminarFisicamenteEmpleado(idEmpleado);

		if (idProveedor > 0)
			eliminarFisicamenteProveedor(idProveedor);

		if (idAlmacen > 0)
			eliminarFisicamenteAlmacen(idAlmacen);

		if (idClienteSocio > 0) {
			eliminarFisicamenteCliente(idClienteSocio);
		}

		if (idVenta > 0) {
			eliminarFisicamenteVenta(idVenta);
			eliminarFisicamenteProductoVenta(idVenta);
		}

		if (idVentaConDescuento > 0)
			eliminarFisicamenteVenta(idVentaConDescuento);
		eliminarFisicamenteProductoVenta(idVentaConDescuento);

	}

	public int eliminarFisicamenteCliente(int id) { // Solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");

			String sqlNoSocio = "DELETE FROM NOSOCIO WHERE ID = ?";
			PreparedStatement psNoSocio = conexion.prepareStatement(sqlNoSocio);
			psNoSocio.setInt(1, id);
			filasAfectadas += psNoSocio.executeUpdate();
			psNoSocio.close();

			String sqlSocio = "DELETE FROM SOCIO WHERE ID = ?";
			PreparedStatement psSocio = conexion.prepareStatement(sqlSocio);
			psSocio.setInt(1, id);
			filasAfectadas += psSocio.executeUpdate();
			psSocio.close();

			String sqlCliente = "DELETE FROM CLIENTE WHERE ID = ?";
			PreparedStatement psCliente = conexion.prepareStatement(sqlCliente);
			psCliente.setInt(1, id);
			filasAfectadas += psCliente.executeUpdate();
			psCliente.close();

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteProducto(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM PRODUCTO WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteEmpleado(int id) { // Solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");

			String sqlParcial = "DELETE FROM PARCIAL WHERE ID = ?";
			PreparedStatement psParcial = conexion.prepareStatement(sqlParcial);
			psParcial.setInt(1, id);
			filasAfectadas += psParcial.executeUpdate();
			psParcial.close();

			String sqlCompleto = "DELETE FROM COMPLETO WHERE ID = ?";
			PreparedStatement psCompleto = conexion.prepareStatement(sqlCompleto);
			psCompleto.setInt(1, id);
			filasAfectadas += psCompleto.executeUpdate();
			psCompleto.close();

			String sqlEmpleado = "DELETE FROM EMPLEADO WHERE ID = ?";
			PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado);
			psEmpleado.setInt(1, id);
			filasAfectadas += psEmpleado.executeUpdate();
			psEmpleado.close();

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteProveedor(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM PROVEEDOR WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteAlmacen(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM ALMACEN WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteVenta(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM VENTA WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	public int eliminarFisicamenteProductoVenta(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM PRODUCTO_VENTA WHERE VENTA_ID = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			filasAfectadas = ps.executeUpdate();
			ps.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filasAfectadas;
	}

	@Test
	public void testVentaConProductoVinculado() {
		// Crear producto SIN proveedor ni almacén
		TProducto producto = new TProducto();
		producto.setMarca("Marca Test");
		producto.setModelo("Modelo Test");
		producto.setPrecio(15.5);
		producto.setUnidades(20);
		producto.setActivo(1);
		producto.setIdProveedor(-1);
		producto.setIdAlmacen(-1);

		idProducto = saProducto.altaProducto(producto);
		assertTrue(idProducto > 0);

		// VINCULAR producto a proveedor
		int resVinculoProveedor = saProveedor.vincularProductoProveedor(idProducto, idProveedor);
		assertEquals(1, resVinculoProveedor);

		// VINCULAR producto a almacen
		int resVinculoAlmacen = saAlmacen.vincularProductoAlmacen(idProducto, idAlmacen);
		assertEquals(1, resVinculoAlmacen);

		// CREAR la venta
		TVenta venta = new TVenta();
		venta.setIdCliente(idCliente);
		venta.setIdEmpleado(idEmpleado);

		Map<Integer, Integer> productos = new HashMap<>();
		productos.put(idProducto, 5); // Compramos 5 unidades del producto
		venta.setProductos(productos);

		idVenta = saVenta.altaVenta(venta);

		assertTrue(idVenta > 0); // La venta se debe realizar correctamente
	}

	@Test
	public void testVentaProductoNoVinculadoFalla() {
		// Crear producto SIN proveedor ni almacén
		TProducto producto = new TProducto();
		producto.setMarca("Marca Test No Vinculado");
		producto.setModelo("Modelo No Vinculado");
		producto.setPrecio(10.0);
		producto.setUnidades(10);
		producto.setActivo(1);
		producto.setIdProveedor(-1);
		producto.setIdAlmacen(-1);

		idProducto = saProducto.altaProducto(producto);
		assertTrue(idProducto > 0);

		// Intentar crear venta con producto no vinculado
		TVenta venta = new TVenta();
		venta.setIdCliente(idCliente);
		venta.setIdEmpleado(idEmpleado);

		Map<Integer, Integer> productos = new HashMap<>();
		productos.put(idProducto, 2);
		venta.setProductos(productos);

		idVenta = saVenta.altaVenta(venta);

		assertEquals(-1, idVenta); // Se espera -1 o fallo porque el
									// producto no está vinculado
	}

	@Test
	public void testVentaMasUnidadesQueStockFalla() {
		// Crear producto
		TProducto producto = new TProducto();
		producto.setMarca("Marca Test Stock");
		producto.setModelo("Modelo Stock");
		producto.setPrecio(20.0);
		producto.setUnidades(5); // Solo 5 unidades
		producto.setActivo(1);
		producto.setIdProveedor(-1);
		producto.setIdAlmacen(-1);

		idProducto = saProducto.altaProducto(producto);
		assertTrue(idProducto > 0);

		// Vincular a proveedor y almacen
		assertEquals(1, saProveedor.vincularProductoProveedor(idProducto, idProveedor));
		assertEquals(1, saAlmacen.vincularProductoAlmacen(idProducto, idAlmacen));

		// Intentar comprar 10 unidades (más de las que hay)
		TVenta venta = new TVenta();
		venta.setIdCliente(idCliente);
		venta.setIdEmpleado(idEmpleado);

		Map<Integer, Integer> productos = new HashMap<>();
		productos.put(idProducto, 10);
		venta.setProductos(productos);

		idVenta = saVenta.altaVenta(venta);

		assertEquals(-1, idVenta); // Se espera -1 o fallo
	}

	@Test
	public void testVentaClienteSocioConDescuento() {
		// Crear cliente socio
		TClienteSocio clienteSocio = new TClienteSocio("Cliente Socio Test", "11223344C", 100);
		idClienteSocio = saCliente.altaCliente(clienteSocio);
		assertTrue(idClienteSocio > 0);

		// Crear producto
		TProducto producto = new TProducto();
		producto.setMarca("Marca Descuento");
		producto.setModelo("Modelo Descuento");
		producto.setPrecio(50.0); // Precio normal
		producto.setUnidades(10);
		producto.setActivo(1);
		producto.setIdProveedor(-1);
		producto.setIdAlmacen(-1);

		idProductoDescuento = saProducto.altaProducto(producto);
		assertTrue(idProductoDescuento > 0);

		// Vincular producto
		assertEquals(1, saProveedor.vincularProductoProveedor(idProductoDescuento, idProveedor));
		assertEquals(1, saAlmacen.vincularProductoAlmacen(idProductoDescuento, idAlmacen));

		// Hacer venta
		TVenta venta = new TVenta();
		venta.setIdCliente(idClienteSocio);
		venta.setIdEmpleado(idEmpleado);

		Map<Integer, Integer> productos = new HashMap<>();
		productos.put(idProductoDescuento, 2); // Comprar 2 unidades
		venta.setProductos(productos);

		idVentaConDescuento = saVenta.altaVenta(venta);
		assertTrue(idVentaConDescuento > 0);

		// Opcional: Leer venta y comprobar importe (dependiendo si aplicas el
		// descuento automáticamente)
	}
*/
}