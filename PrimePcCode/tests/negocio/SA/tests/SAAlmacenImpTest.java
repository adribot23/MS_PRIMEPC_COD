package negocio.SA.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import negocio.factoria.FactoriaNegocio;
import negocio.serviciosAplicacion.SAAlmacen;
import negocio.serviciosAplicacion.SAProducto;
import negocio.transfers.TAlmacen;
import negocio.transfers.TProducto;

public class SAAlmacenImpTest {

	private SAAlmacen saAlmacen;
	private SAProducto saProducto;
	private int idAlmacenCreado = -1;
	private int idAlmacen = -1;
	private int idProducto = -1;

	@Before
	public void setUp() {
		saAlmacen = FactoriaNegocio.obtenerInstancia().generaSAAlmacen();
		saProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
	}

	@After
	public void tearDown() {
		if (idAlmacenCreado > 0) {
			eliminarFisicamenteAlmacen(idAlmacenCreado);
		}
		if (idAlmacen > 0) {
			if (idProducto > 0) {
				saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
			}
			eliminarFisicamenteAlmacen(idAlmacen);

		}
		if (idProducto > 0) {
			eliminarFisicamenteProducto(idProducto);
		}
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

	public int eliminarFisicamente(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM EMPLEADO WHERE ID = ?";
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
	public void testAltaLeerModificarBajaAlmacen() {
		// 1. Alta de almacén
		TAlmacen almacen = new TAlmacen();
		almacen.setCapacidadMaxima(100);
		almacen.setOcupacion(0);
		almacen.setNombre("Test Almacen");

		idAlmacenCreado = saAlmacen.altaAlmacen(almacen);
		assertTrue(idAlmacenCreado > 0);

		// 2. Leer almacén
		TAlmacen almacenLeido = saAlmacen.leerAlmacen(idAlmacenCreado);
		assertNotNull(almacenLeido);
		assertEquals("Test Almacen", almacenLeido.getNombre());
		assertEquals(100, almacenLeido.getCapacidadMaxima());
		assertEquals(0, almacenLeido.getOcupacion());
		assertEquals(1, almacenLeido.getActivo());

		// 3. Modificar almacén
		almacenLeido.setCapacidadMaxima(200);
		int resMod = saAlmacen.modificarAlmacen(almacenLeido);
		assertEquals(1, resMod);

		TAlmacen almacenModificado = saAlmacen.leerAlmacen(idAlmacenCreado);
		assertEquals(200, almacenModificado.getCapacidadMaxima());

		// 4. Leer todos
		Collection<TAlmacen> todos = saAlmacen.leerTodosAlmacenes();
		assertNotNull(todos);
		assertTrue(todos.size() > 0);

		// 5. Baja de almacén
		int resBaja = saAlmacen.bajaAlmacen(idAlmacenCreado);
		assertEquals(1, resBaja);

		TAlmacen almacenBaja = saAlmacen.leerAlmacen(idAlmacenCreado);
		assertNull(almacenBaja);
	}

	@Test
	public void testVincularYDesvincularProducto() {
		// Crear almacen para pruebas
		TAlmacen almacen = new TAlmacen();
		almacen.setCapacidadMaxima(50);
		almacen.setOcupacion(0);
		almacen.setNombre("Almacen Vinculacion");

		idAlmacen = saAlmacen.altaAlmacen(almacen);
		assertTrue(idAlmacen > 0);

		idProducto = -34; // establecemos un id que no existe

		// Vincular producto inexistente
		int resVincular = saAlmacen.vincularProductoAlmacen(idProducto, idAlmacen);
		assertTrue(resVincular == -1);

		TProducto productoPrueba = new TProducto();
		productoPrueba.setMarca("Logitech");
		productoPrueba.setModelo("MX Master 3");
		productoPrueba.setPrecio(89.99);
		productoPrueba.setUnidades(50);
		productoPrueba.setIdAlmacen(-1);
		productoPrueba.setIdProveedor(-1);
		productoPrueba.setActivo(1);
		// Vincular producto existente
		idProducto = saProducto.altaProducto(productoPrueba);
		int resVincularOtraVez = saAlmacen.vincularProductoAlmacen(idProducto, idAlmacen);
		assertTrue(resVincularOtraVez > 0);

		// Desvincular producto
		int resDesvincular = saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
		assertTrue(resDesvincular > 0);

		// Desvincular producto inexistente
		int resDesvincularOtraVez = saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
		assertTrue(resDesvincularOtraVez == -1);
	}

}