package integracion.dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.Producto.DAOProducto;
import integracion.factoria.FactoriaIntegracion;
import negocio.Producto.TProducto;

public class DAOProductoImpTest {

	private DAOProducto daoProducto;
	private int idProducto;
	private final String modeloTest = "ModeloTest";
	private final String marcaTest = "MarcaTest";

	@Before
	public void setUp() {
		daoProducto = FactoriaIntegracion.obtenerInstancia().generaDAOProducto();

		// Creamos un producto de prueba
		TProducto producto = new TProducto();
		producto.setActivo(1);
		producto.setPrecio(199.99);
		producto.setUnidades(10);
		producto.setMarca(marcaTest);
		producto.setModelo(modeloTest);

		idProducto = daoProducto.crear(producto);
	}

	@After
	public void tearDown() {
		if (idProducto > 0) {
			eliminarFisicamente(idProducto); // funcion solo usada en el test
		}
	}

	public int eliminarFisicamente(int id) { // solo para el test
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

	@Test
	public void testCrearProducto() {
		assertTrue(idProducto > 0);
	}

	@Test
	public void testLeerProducto() {
		TProducto producto = daoProducto.leer(idProducto);
		assertNotNull(producto);
		assertEquals(idProducto, producto.getId());
	}

	@Test
	public void testActualizarProducto() {
		TProducto producto = daoProducto.leer(idProducto);
		assertNotNull(producto);

		producto.setPrecio(279.99);
		producto.setUnidades(25);

		int res = daoProducto.actualizar(producto);
		assertEquals(1, res);

		TProducto actualizado = daoProducto.leer(idProducto);
		assertEquals(279.99, actualizado.getPrecio(), 0.01);
		assertEquals(25, actualizado.getUnidades());
	}

	@Test
	public void testLeerProductoPorModelo() {
		TProducto producto = daoProducto.leerPorModelo(modeloTest);
		assertNotNull(producto);
		assertEquals(modeloTest, producto.getModelo());
	}

	@Test
	public void testLeerProductoPorMarcaYModelo() {
		TProducto producto = daoProducto.leerPorMarcaYModelo(modeloTest, marcaTest);
		assertNotNull(producto);
		assertEquals(modeloTest, producto.getModelo());
		assertEquals(marcaTest, producto.getMarca());
	}

	@Test
	public void testLeerTodosLosProductos() {
		Collection<TProducto> productos = daoProducto.leerTodo();
		assertNotNull(productos);
		assertTrue(productos.size() > 0);
	}

	@Test
	public void testEliminarProducto() {
		int res = daoProducto.eliminar(idProducto);
		assertEquals(1, res);

		TProducto producto = daoProducto.leer(idProducto);
		assertNotNull(producto);
		assertEquals(0, producto.getActivo());
	}
}
