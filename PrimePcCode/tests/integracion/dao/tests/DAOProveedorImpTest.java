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

import integracion.Proveedor.DAOProveedor;

import negocio.Proveedor.TProveedor;

public class DAOProveedorImpTest {
	/*
	 * private DAOProveedor daoProveedor; private int idProveedor; private final
	 * String nombreTest = "ProveedorTest";
	 * 
	 * @Before public void setUp() { daoProveedor =
	 * FactoriaIntegracion.obtenerInstancia().generaDAOProveedor();
	 * 
	 * // Creamos un proveedor de prueba TProveedor proveedor = new TProveedor();
	 * proveedor.setActivo(1); proveedor.setNombre(nombreTest);
	 * 
	 * idProveedor = daoProveedor.crear(proveedor); }
	 * 
	 * @After public void tearDown() { if (idProveedor > 0) {
	 * eliminarFisicamente(idProveedor); } }
	 * 
	 * public int eliminarFisicamente(int id) { // solo para el test int
	 * filasAfectadas = 0; try { Connection conexion =
	 * DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	 * String sql = "DELETE FROM PROVEEDOR WHERE ID = ?"; PreparedStatement ps =
	 * conexion.prepareStatement(sql); ps.setInt(1, id); filasAfectadas =
	 * ps.executeUpdate(); ps.close(); conexion.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } return filasAfectadas; }
	 * 
	 * @Test public void testCrearProveedor() { assertTrue(idProveedor > 0); }
	 * 
	 * @Test public void testLeerProveedor() { TProveedor proveedor =
	 * daoProveedor.leer(idProveedor); assertNotNull(proveedor);
	 * assertEquals(nombreTest, proveedor.getNombre()); }
	 * 
	 * @Test public void testActualizarProveedor() { TProveedor proveedor =
	 * daoProveedor.leer(idProveedor); assertNotNull(proveedor);
	 * 
	 * proveedor.setNombre("ProveedorTestActualizado"); int res =
	 * daoProveedor.actualizar(proveedor); assertEquals(1, res);
	 * 
	 * TProveedor actualizado = daoProveedor.leer(idProveedor);
	 * assertEquals("ProveedorTestActualizado", actualizado.getNombre()); }
	 * 
	 * @Test public void testLeerProveedorPorNombre() { TProveedor proveedor =
	 * daoProveedor.leerPorNombre(nombreTest); assertNotNull(proveedor);
	 * assertEquals(nombreTest, proveedor.getNombre()); }
	 * 
	 * @Test public void testLeerTodosProveedores() { Collection<TProveedor>
	 * proveedores = daoProveedor.leerTodo(); assertNotNull(proveedores);
	 * assertTrue(proveedores.size() > 0); }
	 * 
	 * @Test public void testEliminarProveedor() { int res =
	 * daoProveedor.eliminar(idProveedor); assertEquals(1, res);
	 * 
	 * TProveedor proveedor = daoProveedor.leer(idProveedor);
	 * assertNotNull(proveedor); assertEquals(0, proveedor.getActivo()); }
	 */
}
