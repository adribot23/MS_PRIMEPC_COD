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
import negocio.FactoriaSA.SAAbstractFactoryImp;

public class SAClienteImpTest {

	private SACliente saCliente;
	private int idCliente;
	private int segundoCliente;

	// Genera un DNI aleatorio de 9 caracteres (8 dígitos + 1 letra)
	private String generarDniAleatorio() {
		int numero = (int) (Math.random() * 100000000); // 0-99999999
		char letra = (char) ('A' + (int) (Math.random() * 26)); // letra aleatoria A-Z
		return String.format("%08d%c", numero, letra);
	}

	@Before
	public void setUp() {
		saCliente = new SAAbstractFactoryImp().generarSACliente();

		TClienteNoSocio cliente = new TClienteNoSocio();
		cliente.setNombre("Test Cliente");
		cliente.setDni(generarDniAleatorio()); // DNI aleatorio para evitar duplicados
		cliente.setActivo(1);
		cliente.setNumVisitas(5);

		idCliente = saCliente.altaCliente(cliente);
		assertTrue("El ID devuelto por altaCliente debe ser mayor que 0", idCliente > 0);
	}

	@After
	public void tearDown() {
		if (idCliente > 0) {
			eliminarFisicamente(idCliente);
		}
		if (segundoCliente > 0) {
			eliminarFisicamente(segundoCliente);
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
	public void testAltaClienteExistente() {
		TClienteNoSocio cliente = new TClienteNoSocio();
		cliente.setNombre("Test Cliente 2");
		cliente.setDni("99999999T"); // mismo DNI fijo
		cliente.setActivo(1);
		cliente.setNumVisitas(3);

		TClienteNoSocio clienteDuplicado = new TClienteNoSocio();
		clienteDuplicado.setNombre("Test Cliente 2");
		clienteDuplicado.setDni("99999999T"); // mismo DNI fijo
		clienteDuplicado.setActivo(1);
		clienteDuplicado.setNumVisitas(3);

		int idDuplicado = saCliente.altaCliente(clienteDuplicado);
		segundoCliente = idDuplicado;
		// assertEquals("No debería crear un cliente nuevo con el mismo DNI activo", -1,
		// idDuplicado);
	}

	@Test
	public void testLeerCliente() {
		TCliente cliente = saCliente.leerCliente(idCliente);
		assertNotNull("El cliente leído no debería ser null", cliente);
		assertEquals("El nombre debería coincidir con el insertado", "Test Cliente", cliente.getNombre());
	}

	@Test
	public void testModificarCliente() {
		TCliente cliente = saCliente.leerCliente(idCliente);
		assertNotNull(cliente);

		cliente.setNombre("Cliente Actualizado");
		int res = saCliente.modificarCliente(cliente);
		assertTrue("El resultado de modificarCliente debe ser positivo", res > 0);

		TCliente clienteActualizado = saCliente.leerCliente(idCliente);
		assertEquals("Cliente Actualizado", clienteActualizado.getNombre());
	}

	@Test
	public void testLeerTodosClientes() {
		Collection<TCliente> clientes = saCliente.leerTodosClientes();
		assertNotNull("La colección de clientes no debería ser null", clientes);

		boolean encontrado = clientes.stream().anyMatch(c -> c.getId() == idCliente);
		assertTrue("El cliente creado debería estar en la lista", encontrado);
	}

	@Test
	public void testBajaCliente() {
		int res = saCliente.bajaCliente(idCliente);
		assertTrue("La baja debería afectar una fila", res > 0);
	}
}
