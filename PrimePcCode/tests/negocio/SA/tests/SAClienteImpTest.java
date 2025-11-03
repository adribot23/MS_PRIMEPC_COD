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

import negocio.Cliente.SACliente;
import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;

public class SAClienteImpTest {
	/*
	 * private SACliente saCliente; private int idCliente;
	 * 
	 * @Before public void setUp() { saCliente =
	 * FactoriaNegocio.obtenerInstancia().generaSACliente();
	 * 
	 * TClienteNoSocio cliente = new TClienteNoSocio();
	 * cliente.setNombre("Test Cliente"); cliente.setDni("99999999T");
	 * cliente.setActivo(1); cliente.setNumVisitas(5);
	 * 
	 * idCliente = saCliente.altaCliente(cliente); assertTrue(idCliente > 0); }
	 * 
	 * @After public void tearDown() { if (idCliente > 0) {
	 * eliminarFisicamente(idCliente); // Borrado lógico } }
	 * 
	 * public int eliminarFisicamente(int id) { // Solo para el test int
	 * filasAfectadas = 0; try { Connection conexion =
	 * DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	 * 
	 * String sqlNoSocio = "DELETE FROM NOSOCIO WHERE ID = ?"; PreparedStatement
	 * psNoSocio = conexion.prepareStatement(sqlNoSocio); psNoSocio.setInt(1, id);
	 * filasAfectadas += psNoSocio.executeUpdate(); psNoSocio.close();
	 * 
	 * String sqlSocio = "DELETE FROM SOCIO WHERE ID = ?"; PreparedStatement psSocio
	 * = conexion.prepareStatement(sqlSocio); psSocio.setInt(1, id); filasAfectadas
	 * += psSocio.executeUpdate(); psSocio.close();
	 * 
	 * String sqlCliente = "DELETE FROM CLIENTE WHERE ID = ?"; PreparedStatement
	 * psCliente = conexion.prepareStatement(sqlCliente); psCliente.setInt(1, id);
	 * filasAfectadas += psCliente.executeUpdate(); psCliente.close();
	 * 
	 * conexion.close(); } catch (SQLException e) { e.printStackTrace(); } return
	 * filasAfectadas; }
	 * 
	 * @Test public void testAltaClienteExistente() { TClienteNoSocio
	 * clienteDuplicado = new TClienteNoSocio();
	 * clienteDuplicado.setNombre("Test Cliente 2");
	 * clienteDuplicado.setDni("99999999T"); // Mismo DNI
	 * clienteDuplicado.setActivo(1); clienteDuplicado.setNumVisitas(3);
	 * 
	 * int idDuplicado = saCliente.altaCliente(clienteDuplicado);
	 * assertEquals("No debería crear un cliente nuevo con el mismo DNI activo", -1,
	 * idDuplicado); }
	 * 
	 * @Test public void testLeerCliente() { TCliente cliente =
	 * saCliente.leerCliente(idCliente); assertNotNull(cliente);
	 * assertEquals("Test Cliente", cliente.getNombre()); assertEquals("99999999T",
	 * cliente.getDni()); }
	 * 
	 * @Test public void testModificarCliente() { TCliente cliente =
	 * saCliente.leerCliente(idCliente); assertNotNull(cliente);
	 * 
	 * cliente.setNombre("Cliente Actualizado"); int res =
	 * saCliente.modificarCliente(cliente); assertTrue(res > 0);
	 * 
	 * TCliente clienteActualizado = saCliente.leerCliente(idCliente);
	 * assertEquals("Cliente Actualizado", clienteActualizado.getNombre()); }
	 * 
	 * @Test public void testLeerTodosClientes() { Collection<TCliente> clientes =
	 * saCliente.leerTodosClientes(); assertNotNull(clientes);
	 * 
	 * boolean encontrado = clientes.stream().anyMatch(c -> c.getId() == idCliente);
	 * assertTrue(encontrado); }
	 * 
	 * @Test public void testBajaCliente() { int res =
	 * saCliente.bajaCliente(idCliente); assertEquals(1, res);
	 * 
	 * TCliente clienteEliminado = saCliente.leerCliente(idCliente);
	 * assertNull("Cliente eliminado debería ser null al intentar leerlo",
	 * clienteEliminado); }
	 */
}
