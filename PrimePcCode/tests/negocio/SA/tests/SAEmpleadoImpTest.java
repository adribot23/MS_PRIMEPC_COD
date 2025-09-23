package negocio.SA.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import negocio.transfers.TEmpleado;
import negocio.transfers.TEmpleadoCompleto;
import negocio.transfers.TEmpleadoParcial;
import negocio.factoria.FactoriaNegocio;
import negocio.serviciosAplicacion.SAEmpleado;


public class SAEmpleadoImpTest {

    private SAEmpleado saEmpleado;
    private int idEmpleadoCompleto;
    private int idEmpleadoParcial;

    @Before
    public void setUp() {
        saEmpleado = FactoriaNegocio.obtenerInstancia().generaSAEmpleado();
    }

    @After
    public void tearDown() {
        if (idEmpleadoCompleto > 0) {
        	eliminarFisicamente(idEmpleadoCompleto);
        }
        if (idEmpleadoParcial > 0) {
        	eliminarFisicamente(idEmpleadoParcial);
        }
    }
    
    public int eliminarFisicamente(int id) {  // Solo para el test
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
    public void testAltaLeerModificarBajaEmpleadoCompleto() {
        // 1. Alta de empleado completo
        TEmpleadoCompleto empleado = new TEmpleadoCompleto();
        empleado.setDni("12345678A");
        empleado.setNombre("Juan Perez");
        empleado.setActivo(1);
        empleado.setHorasExtra(5); // campo específico de TEmpleadoCompleto

        idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado);
        assertTrue(idEmpleadoCompleto > 0);

        // 2. Leer empleado
        TEmpleado empleadoLeido = saEmpleado.leerEmpleado(idEmpleadoCompleto);
        assertNotNull(empleadoLeido);
        assertEquals("Juan Perez", empleadoLeido.getNombre());
        assertEquals("12345678A", empleadoLeido.getDni());

        // 3. Modificar empleado
        empleadoLeido.setNombre("Juan Perez Modificado");
        int resMod = saEmpleado.modificarEmpleado(empleadoLeido);
        assertTrue(resMod>0);

        TEmpleado empleadoModificado = saEmpleado.leerEmpleado(idEmpleadoCompleto);
        assertEquals("Juan Perez Modificado", empleadoModificado.getNombre());

        // 4. Baja de empleado
        int resBaja = saEmpleado.bajaEmpleado(idEmpleadoCompleto);
        assertTrue(resBaja>0);

        TEmpleado empleadoBaja = saEmpleado.leerEmpleado(idEmpleadoCompleto);
        assertNull(empleadoBaja); // Ya no debe leerse porque está inactivo
    }

    @Test
    public void testAltaLeerModificarBajaEmpleadoParcial() {
        // 1. Alta de empleado parcial
        TEmpleadoParcial empleado = new TEmpleadoParcial();
        empleado.setDni("87654321B");
        empleado.setNombre("Maria Lopez");
        empleado.setActivo(1);
        empleado.setHorasSemanales(20); // campo específico de TEmpleadoParcial

        idEmpleadoParcial = saEmpleado.altaEmpleado(empleado);
        assertTrue(idEmpleadoParcial > 0);

        // 2. Leer empleado
        TEmpleado empleadoLeido = saEmpleado.leerEmpleado(idEmpleadoParcial);
        assertNotNull(empleadoLeido);
        assertEquals("Maria Lopez", empleadoLeido.getNombre());
        assertEquals("87654321B", empleadoLeido.getDni());

        // Limpieza manual de este empleado
        int resBaja = saEmpleado.bajaEmpleado(idEmpleadoParcial);
        assertTrue(resBaja>0);

        TEmpleado empleadoBaja = saEmpleado.leerEmpleado(idEmpleadoParcial);
        assertNull(empleadoBaja);
    }

    @Test
    public void testLeerTodosEmpleados() {
        // Crear dos empleados
        TEmpleadoCompleto empleado1 = new TEmpleadoCompleto();
        empleado1.setDni("12345678C");
        empleado1.setNombre("Juan Perez");
        empleado1.setActivo(1);
        empleado1.setHorasExtra(3);
        idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado1);

        TEmpleadoParcial empleado2 = new TEmpleadoParcial();
        empleado2.setDni("87654321D");
        empleado2.setNombre("Maria Lopez");
        empleado2.setActivo(1);
        empleado2.setHorasSemanales(15);
        idEmpleadoParcial = saEmpleado.altaEmpleado(empleado2);

        // Leer todos
        Collection<TEmpleado> empleados = saEmpleado.leerTodosEmpleados();
        assertNotNull(empleados);
        assertTrue(empleados.size() > 0);

        // Limpieza
        saEmpleado.bajaEmpleado(idEmpleadoCompleto);
        saEmpleado.bajaEmpleado(idEmpleadoParcial);
    }

    @Test
    public void testModificarEmpleadoInactivo() {
        // Crear un empleado completo
        TEmpleadoCompleto empleado = new TEmpleadoCompleto();
        empleado.setDni("11122333C");
        empleado.setNombre("Carlos Martinez");
        empleado.setActivo(1);
        empleado.setHorasExtra(7);

        idEmpleadoCompleto = saEmpleado.altaEmpleado(empleado);
        assertTrue(idEmpleadoCompleto > 0);

        // Dar de baja
        int resBaja = saEmpleado.bajaEmpleado(idEmpleadoCompleto);
        assertTrue(resBaja>0);

        // Intentar modificarlo
        empleado.setNombre("Carlos Martinez Modificado");
        int resMod = saEmpleado.modificarEmpleado(empleado);
        assertEquals(-1, resMod); // No se puede modificar un empleado inactivo
    }
}