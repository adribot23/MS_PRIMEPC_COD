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

import negocio.Empleado.SAEmpleado;
import negocio.Empleado.TEmpleado;
import negocio.Empleado.TEmpleadoCompleto;
import negocio.Empleado.TEmpleadoParcial;
import negocio.FactoriaSA.SAAbstractFactoryImp;

public class SAEmpleadoImpTest {

	private SAEmpleado saEmpleado;
	private int idEmpleadoCompleto;
	private int idEmpleadoParcial;

	@Before
	public void setUp() {
		SAAbstractFactoryImp factoria = new SAAbstractFactoryImp();
		saEmpleado = factoria.generarSAEmpleado();
		idEmpleadoCompleto = -1;
		idEmpleadoParcial = -1;
	}

	@After
	public void tearDown() {
		if (idEmpleadoCompleto > 0) {
			eliminarFisicamente(idEmpleadoCompleto);
			idEmpleadoCompleto = -1;
		}
		if (idEmpleadoParcial > 0) {
			eliminarFisicamente(idEmpleadoParcial);
			idEmpleadoParcial = -1;
		}
	}

	public int eliminarFisicamente(int id) {
		int filasAfectadas = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "");

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
	public void testAltaLeerModificarBajaEmpleadoCompleto() {
		// Alta
		TEmpleadoCompleto empleado = new TEmpleadoCompleto();
		empleado.setDni("12345678A");
		empleado.setNombre("Juan Perez");
		empleado.setTelefono("600600600");
		empleado.setActivo(1);
		empleado.setHorasExtra(5);

		idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado);
		assertTrue("El ID devuelto debe ser mayor que 0", idEmpleadoCompleto > 0);

		// Leer
		TEmpleado empleadoLeido = saEmpleado.leerEmpleado(idEmpleadoCompleto);
		assertNotNull("El empleado leído no debe ser null", empleadoLeido);
		assertEquals("Juan Perez", empleadoLeido.getNombre());
		assertEquals("12345678A", empleadoLeido.getDni());

		// Modificar
		empleadoLeido.setNombre("Juan P. Mod");
		int resMod = saEmpleado.modificarEmpleado(empleadoLeido);
		assertTrue("Debe haberse modificado el empleado", resMod > 0);

		TEmpleado empleadoModificado = saEmpleado.leerEmpleado(idEmpleadoCompleto);
		assertEquals("Juan P. Mod", empleadoModificado.getNombre());

		// Baja
		int resBaja = saEmpleado.bajaEmpleado(idEmpleadoCompleto);
		assertTrue("La baja debe realizarse correctamente", resBaja > 0);

		TEmpleado empleadoBaja = saEmpleado.leerEmpleado(idEmpleadoCompleto);
		assertNull("El empleado dado de baja no debe poder leerse", empleadoBaja);
	}

	@Test
	public void testAltaLeerModificarBajaEmpleadoParcial() {
		// Alta
		TEmpleadoParcial empleado = new TEmpleadoParcial();
		empleado.setDni("87654321B");
		empleado.setNombre("Maria Lopez");
		empleado.setTelefono("700700700");
		empleado.setActivo(1);
		empleado.setHorasSemanales(20);

		idEmpleadoParcial = saEmpleado.altaEmpleado(empleado);
		assertTrue("El ID devuelto debe ser mayor que 0", idEmpleadoParcial > 0);

		// Leer
		TEmpleado empleadoLeido = saEmpleado.leerEmpleado(idEmpleadoParcial);
		assertNotNull("El empleado leído no debe ser null", empleadoLeido);
		assertEquals("Maria Lopez", empleadoLeido.getNombre());
		assertEquals("87654321B", empleadoLeido.getDni());

		// Baja
		int resBaja = saEmpleado.bajaEmpleado(idEmpleadoParcial);
		assertTrue("La baja debe realizarse correctamente", resBaja > 0);

		TEmpleado empleadoBaja = saEmpleado.leerEmpleado(idEmpleadoParcial);
		assertNull("El empleado dado de baja no debe poder leerse", empleadoBaja);
	}

	@Test
	public void testLeerTodosEmpleados() {
		// Crear dos empleados
		TEmpleadoCompleto empleado1 = new TEmpleadoCompleto();
		empleado1.setDni("22334455A");
		empleado1.setNombre("Juan Perez");
		empleado1.setTelefono("611111111");
		empleado1.setActivo(1);
		empleado1.setHorasExtra(3);
		idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado1);

		TEmpleadoParcial empleado2 = new TEmpleadoParcial();
		empleado2.setDni("66778899B");
		empleado2.setNombre("Maria Lopez");
		empleado2.setTelefono("722222222");
		empleado2.setActivo(1);
		empleado2.setHorasSemanales(15);
		idEmpleadoParcial = saEmpleado.altaEmpleado(empleado2);

		Collection<TEmpleado> empleados = saEmpleado.leerTodosEmpleados();
		assertNotNull("La colección de empleados no debe ser null", empleados);
		assertTrue("Debe haber al menos dos empleados", empleados.size() > 0);

		// Limpieza
		saEmpleado.bajaEmpleado(idEmpleadoCompleto);
		saEmpleado.bajaEmpleado(idEmpleadoParcial);
	}

	@Test
	public void testModificarEmpleadoInactivo() {
		// Crear empleado
		TEmpleadoCompleto empleado = new TEmpleadoCompleto();
		empleado.setDni("11122333C");
		empleado.setNombre("Carlos Mart");
		empleado.setTelefono("800800800");
		empleado.setActivo(1);
		empleado.setHorasExtra(7);

		idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado);
		assertTrue("El ID devuelto debe ser mayor que 0", idEmpleadoCompleto > 0);

		// Dar de baja
		int resBaja = saEmpleado.bajaEmpleado(idEmpleadoCompleto);
		assertTrue("La baja debe realizarse correctamente", resBaja > 0);

		// Intentar modificar
		empleado.setNombre("Carlos Martinez Modificado");
		int resMod = saEmpleado.modificarEmpleado(empleado);
		assertEquals("No se debe poder modificar un empleado inactivo", -1, resMod);
	}
}
