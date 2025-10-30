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

import integracion.Cliente.DAOCliente;
import integracion.FactoriaDAO.DAOAbstractFactory;
import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;

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
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.create(cliente);
		assertTrue(id > 0);
	}

	@Test
	public void testCrearClienteSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.create(cliente);
		assertTrue(id > 0);
	}

	@Test
	public void testLeerClienteNoSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteNoSocio);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerClienteSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteSocio);
		assertEquals("María López", leido.getNombre());
	}

	@Test
	public void testActualizarClienteNoSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.create(cliente);

		TClienteNoSocio noSocio = (TClienteNoSocio) daoCliente.read(id);
		int visitasAntes = noSocio.getNumVisitas();
		noSocio.setNumVisitas(visitasAntes + 1);

		int res = daoCliente.update(noSocio);
		assertTrue(res > 0);

		TClienteNoSocio actualizado = (TClienteNoSocio) daoCliente.read(id);
		assertEquals(visitasAntes + 1, actualizado.getNumVisitas());
	}

	@Test
	public void testActualizarClienteSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.create(cliente);

		TClienteSocio socio = (TClienteSocio) daoCliente.read(id);
		int puntosAntes = socio.getPuntos();
		socio.setPuntos(puntosAntes + 50);

		int res = daoCliente.update(socio);
		assertTrue(res > 0);

		TClienteSocio actualizado = (TClienteSocio) daoCliente.read(id);
		assertEquals(puntosAntes + 50, actualizado.getPuntos());
	}

	@Test
	public void testLeerPorDNI() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read_by_DNI("12345678A");
		assertNotNull(leido);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerTodo() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		id = daoCliente.create(new TClienteNoSocio(-1, "Cliente1", "11111111A", 1, 2));
		id_extra = daoCliente.create(new TClienteSocio(-1, "Cliente2", "22222222B", 1, 0, 50));

		Collection<TCliente> clientes = daoCliente.read_all();
		assertNotNull(clientes);
		assertTrue(clientes.size() >= 2);
	}

	@Test
	public void testEliminarClienteNoSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", "12345678A", 1, 5);
		id = daoCliente.create(cliente);

		int res = daoCliente.delete(id);
		assertTrue(res > 0);

		TCliente clienteBorrado = daoCliente.read(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}

	@Test
	public void testEliminarClienteSocio() {
		daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		cliente = new TClienteSocio(-1, "María López", "87654321B", 1, 0, 100);
		id = daoCliente.create(cliente);

		int res = daoCliente.delete(id);
		assertTrue(res > 0);

		TCliente clienteBorrado = daoCliente.read(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}
}
