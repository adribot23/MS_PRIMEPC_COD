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

import negocio.Producto.SAProducto;
import negocio.Producto.TProducto;

public class SAProductoImpTest {
	/*
	 * private SAProducto saProducto; private int idProductoCreado;
	 * 
	 * @Before public void setUp() { saProducto =
	 * FactoriaNegocio.obtenerInstancia().generaSAProducto(); }
	 * 
	 * @After public void tearDown() { if (idProductoCreado > 0) {
	 * eliminarFisicamente(idProductoCreado); } }
	 * 
	 * public int eliminarFisicamente(int id) { // solo para el test int
	 * filasAfectadas = 0; try { Connection conexion =
	 * DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	 * String sql = "DELETE FROM PRODUCTO WHERE ID = ?"; PreparedStatement ps =
	 * conexion.prepareStatement(sql); ps.setInt(1, id); filasAfectadas =
	 * ps.executeUpdate(); ps.close(); conexion.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } return filasAfectadas; }
	 * 
	 * @Test public void testAltaProducto() { // 1.Alta de Producto TProducto
	 * producto = new TProducto(); producto.setModelo("Modelo123");
	 * producto.setMarca("MarcaTest"); producto.setUnidades(10);
	 * producto.setPrecio(99.99); producto.setActivo(1);
	 * 
	 * idProductoCreado = saProducto.altaProducto(producto);
	 * assertTrue(idProductoCreado > 0);
	 * 
	 * }
	 * 
	 * @Test public void testLeerProducto() { // 2.Leer Producto TProducto producto
	 * = new TProducto(); producto.setModelo("ModeloLectura");
	 * producto.setMarca("MarcaLectura"); producto.setUnidades(5);
	 * producto.setPrecio(49.99); producto.setActivo(1);
	 * 
	 * idProductoCreado = saProducto.altaProducto(producto);
	 * 
	 * TProducto leido = saProducto.leerProducto(idProductoCreado);
	 * 
	 * assertNotNull(leido); assertEquals("ModeloLectura", leido.getModelo());
	 * 
	 * saProducto.bajaProducto(idProductoCreado); }
	 * 
	 * @Test public void testLeerTodosProductos() { // 3. Leer todos los productos
	 * int sizeBefore = saProducto.leerTodosProductos().size();
	 * 
	 * TProducto producto = new TProducto(); producto.setModelo("ModeloLista");
	 * producto.setMarca("MarcaLista"); producto.setUnidades(7);
	 * producto.setPrecio(79.99); producto.setActivo(1);
	 * 
	 * idProductoCreado = saProducto.altaProducto(producto);
	 * 
	 * Collection<TProducto> productos = saProducto.leerTodosProductos();
	 * assertTrue(productos.size() >= sizeBefore + 1);
	 * 
	 * // Limpieza saProducto.bajaProducto(idProductoCreado); }
	 * 
	 * @Test public void testModificarProducto() { // 4. Modificar producto
	 * TProducto producto = new TProducto(); producto.setModelo("ModeloModificar");
	 * producto.setMarca("MarcaModificar"); producto.setUnidades(20);
	 * producto.setPrecio(199.99); producto.setActivo(1);
	 * 
	 * idProductoCreado = saProducto.altaProducto(producto); TProducto
	 * productoModificar = saProducto.leerProducto(idProductoCreado);
	 * 
	 * productoModificar.setModelo("Producto Modificado");
	 * productoModificar.setUnidades(15); // cambia unidades
	 * 
	 * int resultado = saProducto.modificarProducto(productoModificar);
	 * 
	 * assertTrue(resultado > 0);
	 * 
	 * TProducto modificado = saProducto.leerProducto(idProductoCreado);
	 * assertEquals("Producto Modificado", modificado.getModelo()); assertEquals(15,
	 * modificado.getUnidades());
	 * 
	 * // Limpieza saProducto.bajaProducto(idProductoCreado); }
	 * 
	 * @Test public void testBajaProducto() { // 5. Baja de producto TProducto
	 * producto = new TProducto(); producto.setModelo("ModeloBaja");
	 * producto.setMarca("MarcaBaja"); producto.setUnidades(3);
	 * producto.setPrecio(39.99); producto.setActivo(1);
	 * 
	 * idProductoCreado = saProducto.altaProducto(producto);
	 * 
	 * int resultadoBaja = saProducto.bajaProducto(idProductoCreado);
	 * 
	 * assertTrue(resultadoBaja > 0);
	 * 
	 * TProducto productoBaja = saProducto.leerProducto(idProductoCreado);
	 * assertNull(productoBaja); }
	 */
}
