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

import integracion.Cliente.DAOCliente;
import integracion.FactoriaDAO.DAOAbstractFactoryImp;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Cliente.TCliente;
import negocio.Cliente.TClienteNoSocio;
import negocio.Cliente.TClienteSocio;

public class DAOClienteImpTest {

	private DAOCliente daoCliente;
	private TCliente cliente;
	private int id;
	private int id_extra;
	private Transaction transaccion;

	// Genera un DNI aleatorio de 9 caracteres (8 dígitos + 1 letra)
	private String generarDniAleatorio() {
		int numero = (int) (Math.random() * 100000000);
		char letra = (char) ('A' + (int) (Math.random() * 26));
		return String.format("%08d%c", numero, letra);
	}

	@Before
	public void setUp() {
		TManager tManager = TManager.getInstance();
		transaccion = tManager.createTransaction();
		if (transaccion != null) {
			transaccion.start();
		}

		daoCliente = new DAOAbstractFactoryImp().generaDAOCliente();
		id = -1;
		id_extra = -1;
	}

	@After
	public void tearDown() {
		if (transaccion != null) {
			try {
				transaccion.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (id > 0) {
			eliminarFisicamente(id);
			id = -1;
		}
		if (id_extra > 0) {
			eliminarFisicamente(id_extra);
			id_extra = -1;
		}
	}

	public int eliminarFisicamente(int id) {
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "");

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
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", generarDniAleatorio(), 1, 5);
		id = daoCliente.create(cliente);
		assertTrue("El ID devuelto debería ser mayor que 0", id > 0);
	}

	@Test
	public void testCrearClienteSocio() {
		cliente = new TClienteSocio(-1, "María López", generarDniAleatorio(), 1, 0, 100);
		id = daoCliente.create(cliente);
		assertTrue("El ID devuelto debería ser mayor que 0", id > 0);
	}

	@Test
	public void testLeerClienteNoSocio() {
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", generarDniAleatorio(), 1, 5);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteNoSocio);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerClienteSocio() {
		cliente = new TClienteSocio(-1, "María López", generarDniAleatorio(), 1, 0, 100);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read(id);
		assertNotNull(leido);
		assertTrue(leido instanceof TClienteSocio);
		assertEquals("María López", leido.getNombre());
	}

	@Test
	public void testActualizarClienteNoSocio() {
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", generarDniAleatorio(), 1, 5);
		id = daoCliente.create(cliente);

		TClienteNoSocio noSocio = (TClienteNoSocio) daoCliente.read(id);
		int visitasAntes = noSocio.getNumVisitas();
		noSocio.setNumVisitas(visitasAntes + 1);

		int res = daoCliente.update(noSocio);
		assertTrue("El update debería afectar al menos una fila", res > 0);

		TClienteNoSocio actualizado = (TClienteNoSocio) daoCliente.read(id);
		assertEquals(visitasAntes + 1, actualizado.getNumVisitas());
	}

	@Test
	public void testActualizarClienteSocio() {
		cliente = new TClienteSocio(-1, "María López", generarDniAleatorio(), 1, 0, 100);
		id = daoCliente.create(cliente);

		TClienteSocio socio = (TClienteSocio) daoCliente.read(id);
		int puntosAntes = socio.getPuntos();
		socio.setPuntos(puntosAntes + 50);

		int res = daoCliente.update(socio);
		assertTrue("El update debería afectar al menos una fila", res > 0);

		TClienteSocio actualizado = (TClienteSocio) daoCliente.read(id);
		assertEquals(puntosAntes + 50, actualizado.getPuntos());
	}

	@Test
	public void testLeerPorDNI() {
		String dni = generarDniAleatorio();
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", dni, 1, 5);
		id = daoCliente.create(cliente);

		TCliente leido = daoCliente.read_by_DNI(dni);
		assertNotNull(leido);
		assertEquals("Carlos Pérez", leido.getNombre());
	}

	@Test
	public void testLeerTodo() {
		id = daoCliente.create(new TClienteNoSocio(-1, "Cliente1", generarDniAleatorio(), 1, 2));
		id_extra = daoCliente.create(new TClienteSocio(-1, "Cliente2", generarDniAleatorio(), 1, 0, 50));

		Collection<TCliente> clientes = daoCliente.read_all();
		assertNotNull(clientes);
		assertTrue("Debería haber al menos 2 clientes en la lista", clientes.size() >= 2);
	}

	@Test
	public void testEliminarClienteNoSocio() {
		cliente = new TClienteNoSocio(-1, "Carlos Pérez", generarDniAleatorio(), 1, 5);
		id = daoCliente.create(cliente);

		int res = daoCliente.delete(id);
		assertTrue("El delete debería afectar al menos una fila", res > 0);

		TCliente clienteBorrado = daoCliente.read(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}

	@Test
	public void testEliminarClienteSocio() {
		cliente = new TClienteSocio(-1, "María López", generarDniAleatorio(), 1, 0, 100);
		id = daoCliente.create(cliente);

		int res = daoCliente.delete(id);
		assertTrue("El delete debería afectar al menos una fila", res > 0);

		TCliente clienteBorrado = daoCliente.read(id);
		assertNotNull(clienteBorrado);
		assertEquals(0, clienteBorrado.getActivo());
	}
}
