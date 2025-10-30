package integracion.dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import integracion.Venta.DAOVenta;

import negocio.Venta.TVenta;

public class DAOVentaImpTest {
/*
	private DAOVenta daoVenta;
	private int idVenta;
	private final int idEmpleado = 1;
	private final int idCliente = 1;

	@Before
	public void setUp() {
		daoVenta = FactoriaIntegracion.obtenerInstancia().generaDAOVenta();

		TVenta venta = new TVenta();
		venta.setMetodoPago("Tarjeta");
		venta.setPrecio(500.0);
		venta.setDescuento(50.0);
		venta.setIdEmpleado(idEmpleado);
		venta.setIdCliente(idCliente);
		venta.setActivo(1);

		idVenta = daoVenta.crear(venta);
		assertTrue(idVenta > 0);
	}

	@After
	public void tearDown() {
		if (idVenta > 0) {
			eliminarFisicamenteEmpleado(idEmpleado);
			eliminarFisicamenteCliente(idCliente);
			eliminarFisicamenteVenta(idVenta);
		}
	}

	public int eliminarFisicamenteVenta(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM VENTA WHERE ID = ?";
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

	public int eliminarFisicamenteCliente(int id) { // solo para el test
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

	public int eliminarFisicamenteEmpleado(int id) { // solo para el test
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
	public void testCrearVenta() {
		TVenta venta = daoVenta.leer(idVenta);
		assertNotNull(venta);
		assertEquals("Tarjeta", venta.getMetodoPago());
		assertEquals(500.0, venta.getPrecio(), 0.001);
	}

	@Test
	public void testActualizarVenta() {
		TVenta venta = daoVenta.leer(idVenta);
		assertNotNull(venta);

		venta.setMetodoPago("Efectivo");
		venta.setPrecio(450.0);
		int res = daoVenta.actualizar(venta);
		assertEquals(1, res);

		TVenta actualizada = daoVenta.leer(idVenta);
		assertEquals("Efectivo", actualizada.getMetodoPago());
		assertEquals(450.0, actualizada.getPrecio(), 0.001);
	}

	@Test
	public void testLeerTodoVentas() {
		Collection<TVenta> ventas = daoVenta.leerTodo();
		assertNotNull(ventas);

		boolean encontrada = ventas.stream().anyMatch(v -> v.getId() == idVenta);
		assertTrue(encontrada);
	}

	@Test
	public void testEliminarVenta() {
		int res = daoVenta.eliminar(idVenta);
		assertEquals(1, res);

		TVenta venta = daoVenta.leer(idVenta);
		assertNotNull(venta);
		assertEquals(0, venta.getActivo());

		// Verificar que la venta eliminada no esté en el listado de ventas activas
		Collection<TVenta> ventas = daoVenta.leerTodo();
		boolean sigueActiva = false;
		for (TVenta v : ventas) {
			if (v.getId() == idVenta && v.getActivo() != 0) {
				sigueActiva = true;
				break;
			}
		}
		assertFalse(sigueActiva);
	}
	*/
}
