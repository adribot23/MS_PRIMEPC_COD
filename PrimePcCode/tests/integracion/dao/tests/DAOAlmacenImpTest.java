package integracion.dao.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.daos.DAOAlmacen;
import negocio.transfers.TAlmacen;
import integracion.factoria.FactoriaIntegracion;

public class DAOAlmacenImpTest {

	private DAOAlmacen daoAlmacen;
	private List<Integer> almacenesCreados;

	@Before
	public void setUp() {
		daoAlmacen = FactoriaIntegracion.obtenerInstancia().generaDAOAlmacen();
		almacenesCreados = new ArrayList<>();
	}

	@After
	public void tearDown() {
		for (Integer id : almacenesCreados) {
			eliminarFisicamente(id);
		}
	}

	public int eliminarFisicamente(int id) { // solo para el test
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
			String sql = "DELETE FROM ALMACEN WHERE ID = ?";
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
	public void testCrearLeerActualizarEliminar() {
		TAlmacen almacen = new TAlmacen();
		almacen.setNombre("AlmacenTest");
		almacen.setCapacidadMaxima(100);
		almacen.setOcupacion(0);

		int id = daoAlmacen.crear(almacen);
		almacenesCreados.add(id);
		assertTrue(id > 0);

		TAlmacen almacenLeido = daoAlmacen.leer(id);
		assertNotNull(almacenLeido);
		assertEquals("AlmacenTest", almacenLeido.getNombre());

		almacenLeido.setCapacidadMaxima(200);
		almacenLeido.setNombre("AlmacenTestActualizado");
		int filasActualizadas = daoAlmacen.actualizar(almacenLeido);
		assertEquals(1, filasActualizadas);

		TAlmacen almacenActualizado = daoAlmacen.leer(id);
		assertEquals("AlmacenTestActualizado", almacenActualizado.getNombre());
		assertEquals(200, almacenActualizado.getCapacidadMaxima());

		int filasEliminadas = daoAlmacen.eliminar(id);
		assertEquals(1, filasEliminadas);

		TAlmacen almacenEliminado = daoAlmacen.leer(id);
		assertNotNull(almacenEliminado);
		assertEquals(0, almacenEliminado.getActivo());
	}

	@Test
	public void testLeerPorNombre() {
		TAlmacen almacen = new TAlmacen();
		almacen.setNombre("AlmacenBuscar");
		almacen.setCapacidadMaxima(50);
		almacen.setOcupacion(10);

		int id = daoAlmacen.crear(almacen);
		almacenesCreados.add(id);
		assertTrue(id > 0);

		TAlmacen almacenLeido = daoAlmacen.leerPorNombre("AlmacenBuscar");
		assertNotNull(almacenLeido);
		assertEquals(id, almacenLeido.getId());
	}

	@Test
	public void testLeerTodo() {
		Collection<TAlmacen> almacenes = daoAlmacen.leerTodo();
		assertNotNull(almacenes);
	}

	@Test
	public void testActualizarOcupacion() {
		TAlmacen almacen = new TAlmacen();
		almacen.setNombre("AlmacenOcupacion");
		almacen.setCapacidadMaxima(300);
		almacen.setOcupacion(0);

		int id = daoAlmacen.crear(almacen);
		almacenesCreados.add(id);
		assertTrue(id > 0);

		TAlmacen almacenLeido = daoAlmacen.leer(id);
		almacenLeido.setOcupacion(150);
		daoAlmacen.actualizar(almacenLeido);

		TAlmacen almacenActualizado = daoAlmacen.leer(id);
		assertEquals(150, almacenActualizado.getOcupacion());
	}
}
