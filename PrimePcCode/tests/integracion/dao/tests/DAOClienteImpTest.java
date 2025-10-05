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
import org.junit.Test;

import integracion.daos.DAOCliente;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TCliente;
import negocio.transfers.TClienteNoSocio;
import negocio.transfers.TClienteSocio;

public class DAOClienteImpTest {

	private DAOCliente daoCliente;
	private TCliente cliente;
	private int id;
	private int id_extra;

	@After
	public void tearDown() {
		if (id > 0) {
			eliminarFisicamente(id);
			id = -1;
		}
		if (id_extra > 0) {
			eliminarFisicamente(id_extra);
			id_extra = -1;
		}
	}

	public int eliminarFisicamente(int id) { // solo para el test
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

	@Test
	public void testCrearClienteNoSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.crear(cliente);
		assertTrue(id > 0);
	}

	@Test
	public void testCrearClienteSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.crear(cliente);
		assertTrue(id > 0);
	}

	@Test
	public void testLeerClienteNoSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.crear(cliente);

		TCliente leido = daoCliente.leer(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteNoSocio);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerClienteSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.crear(cliente);

		TCliente leido = daoCliente.leer(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteSocio);
		assertEquals("María López", leido.getNombre());
	}

	@Test
	public void testActualizarClienteNoSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.crear(cliente);

		TClienteNoSocio noSocio = (TClienteNoSocio) daoCliente.leer(id);
		int visitasAntes = noSocio.getNumVisitas();
		noSocio.setNumVisitas(visitasAntes + 1);

		int res = daoCliente.actualizar(noSocio);
		assertTrue(res > 0);

		TClienteNoSocio actualizado = (TClienteNoSocio) daoCliente.leer(id);
		assertEquals(visitasAntes + 1, actualizado.getNumVisitas());
	}

	@Test
	public void testActualizarClienteSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.crear(cliente);

		TClienteSocio socio = (TClienteSocio) daoCliente.leer(id);
		int puntosAntes = socio.getPuntos();
		socio.setPuntos(puntosAntes + 50);

		int res = daoCliente.actualizar(socio);
		assertTrue(res > 0);

		TClienteSocio actualizado = (TClienteSocio) daoCliente.leer(id);
		assertEquals(puntosAntes + 50, actualizado.getPuntos());
	}

	@Test
	public void testLeerPorDNI() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.crear(cliente);

		TCliente leido = daoCliente.leerPorDNI("12345678A");
		assertNotNull(leido);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerTodo() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		id = daoCliente.crear(new TClienteNoSocio(-1, "Cliente1", "11111111A", 1, 2));
		id_extra = daoCliente.crear(new TClienteSocio(-1, "Cliente2", "22222222B", 1, 0, 50));

		Collection<TCliente> clientes = daoCliente.leerTodo();
		assertNotNull(clientes);
		assertTrue(clientes.size() >= 2);
	}

	@Test
	public void testEliminarClienteNoSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.crear(cliente);

		int res = daoCliente.eliminar(id);
		assertTrue(res > 0);

		TCliente clienteBorrado = daoCliente.leer(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}

	@Test
	public void testEliminarClienteSocio() {
		daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.crear(cliente);

		int res = daoCliente.eliminar(id);
		assertTrue(res > 0);

		TCliente clienteBorrado = daoCliente.leer(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}
}
