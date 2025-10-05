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

import integracion.daos.DAOEmpleado;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TEmpleado;
import negocio.transfers.TEmpleadoCompleto;
import negocio.transfers.TEmpleadoParcial;

public class DAOEmpleadoImpTest {

	private DAOEmpleado daoEmpleado;
	private int idGenerado = -1;

	@Before
	public void setUp() {
		daoEmpleado = FactoriaIntegracion.obtenerInstancia().generaDAOEmpleado();
	}

	@After
	public void tearDown() {
		if (idGenerado > 0) {
			eliminarFisicamente(idGenerado);
			idGenerado = -1; // Reset para el siguiente test
		}
		daoEmpleado = null;
	}

	public int eliminarFisicamente(int id) { // solo para el test
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

	@Test
	public void testCrearYLeerEmpleadoCompleto() {
		TEmpleadoCompleto empleadoCompleto = new TEmpleadoCompleto(-1, "Juan Perez", "12345678A", "600600600", 1, 10);
		idGenerado = daoEmpleado.crear(empleadoCompleto);

		assertTrue(idGenerado > 0);

		TEmpleado empleadoLeido = daoEmpleado.leer(idGenerado);

		assertNotNull(empleadoLeido);
		assertTrue(empleadoLeido instanceof TEmpleadoCompleto);
		assertEquals("Juan Perez", empleadoLeido.getNombre());
		assertEquals("12345678A", empleadoLeido.getDni());
		assertEquals("600600600", empleadoLeido.getTelefono());
	}

	@Test
	public void testCrearYLeerEmpleadoParcial() {
		TEmpleadoParcial empleadoParcial = new TEmpleadoParcial(-1, "Maria Lopez", "87654321B", "700700700", 1, 20);
		idGenerado = daoEmpleado.crear(empleadoParcial);

		assertTrue(idGenerado > 0);

		TEmpleado empleadoLeido = daoEmpleado.leer(idGenerado);

		assertNotNull(empleadoLeido);
		assertTrue(empleadoLeido instanceof TEmpleadoParcial);
		assertEquals("Maria Lopez", empleadoLeido.getNombre());
		assertEquals("87654321B", empleadoLeido.getDni());
		assertEquals("700700700", empleadoLeido.getTelefono());
	}

	@Test
	public void testActualizarEmpleadoCompleto() {
		TEmpleadoCompleto empleadoCompleto = new TEmpleadoCompleto(-1, "Carlos Diaz", "11223344C", "800800800", 1, 5);
		idGenerado = daoEmpleado.crear(empleadoCompleto);

		assertTrue(idGenerado > 0);

		empleadoCompleto.setId(idGenerado);
		empleadoCompleto.setNombre("Carlos Modificado");
		empleadoCompleto.setHorasExtra(15);

		int filasActualizadas = daoEmpleado.actualizar(empleadoCompleto);
		assertTrue(filasActualizadas > 0);

		TEmpleado empleadoLeido = daoEmpleado.leer(idGenerado);
		assertEquals("Carlos Modificado", empleadoLeido.getNombre());
		assertEquals("11223344C", empleadoLeido.getDni());
	}

	@Test
	public void testEliminarEmpleado() {
		TEmpleadoParcial empleadoParcial = new TEmpleadoParcial(-1, "Lucia Ramos", "33445566D", "900900900", 1, 25);
		idGenerado = daoEmpleado.crear(empleadoParcial);

		assertTrue(idGenerado > 0);

		int filasEliminadas = daoEmpleado.eliminar(idGenerado);
		assertTrue(filasEliminadas > 0);

		TEmpleado empleadoLeido = daoEmpleado.leer(idGenerado);
		assertNotNull(empleadoLeido);
		assertEquals(0, empleadoLeido.getActivo());
	}

	@Test
	public void testLeerTodo() {
		Collection<TEmpleado> empleados = daoEmpleado.leerTodo();

		assertNotNull(empleados);
		assertTrue(empleados.size() >= 0);
	}

	@Test
	public void testLeerPorDNI() {
		TEmpleadoCompleto empleadoCompleto = new TEmpleadoCompleto(-1, "Ana Torres", "55667788E", "911911911", 1, 8);
		idGenerado = daoEmpleado.crear(empleadoCompleto);

		TEmpleado empleadoLeido = daoEmpleado.leerPorDNI("55667788E");

		assertNotNull(empleadoLeido);
		assertEquals("Ana Torres", empleadoLeido.getNombre());
		assertEquals("55667788E", empleadoLeido.getDni());
	}
}
