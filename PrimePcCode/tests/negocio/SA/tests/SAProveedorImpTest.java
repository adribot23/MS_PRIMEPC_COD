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

import negocio.FactoriaSA.SAAbstractFactoryImp;
import negocio.Proveedor.SAProveedor;
import negocio.Proveedor.TProveedor;

public class SAProveedorImpTest {

    private SAProveedor saProveedor;
    private int idProveedorCreado = -1;

    @Before
    public void setUp() {
        saProveedor = new SAAbstractFactoryImp().generarSAProveedor();
    }

    @After
    public void tearDown() {
        if (idProveedorCreado > 0) {
            eliminarFisicamente(idProveedorCreado);
            idProveedorCreado = -1;
        }
    }

    // === MÉTODOS AUXILIARES ===
    private int eliminarFisicamente(int id) {
        int filasAfectadas = 0;
        try (Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "")) {
            String sql = "DELETE FROM PROVEEDOR WHERE ID = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    // === TESTS ===

    @Test
    public void testAltaLeerModificarBajaProveedor() {
        // 1. Alta
        TProveedor proveedor = new TProveedor();
        proveedor.setNombre("ProveedorTest");
        proveedor.setActivo(1);

        idProveedorCreado = saProveedor.altaProveedor(proveedor);
        assertTrue("El proveedor debería haberse creado correctamente", idProveedorCreado > 0);

        // 2. Leer
        TProveedor proveedorLeido = saProveedor.leerProveedor(idProveedorCreado);
        assertNotNull("El proveedor leído no debe ser null", proveedorLeido);
        assertEquals("ProveedorTest", proveedorLeido.getNombre());

        // 3. Modificar
        proveedorLeido.setNombre("ProvTestModificado");
        int resMod = saProveedor.modificarProveedor(proveedorLeido);
        assertEquals("Debe devolver 1 si se modifica correctamente", 1, resMod);

        TProveedor proveedorModificado = saProveedor.leerProveedor(idProveedorCreado);
        assertEquals("ProvTestModificado", proveedorModificado.getNombre());

        // 4. Baja
        int resBaja = saProveedor.bajaProveedor(idProveedorCreado);
        assertEquals("Debe devolver 1 si se da de baja correctamente", 1, resBaja);

        TProveedor proveedorBaja = saProveedor.leerProveedor(idProveedorCreado);
        //assertNull("Proveedor dado de baja no debería poder leerse", proveedorBaja);
    }

    @Test
    public void testLeerTodosProveedores() {
        // Crear uno por si la tabla está vacía
        TProveedor proveedor = new TProveedor();
        proveedor.setNombre("ProveedorMultiple");
        proveedor.setActivo(1);

        idProveedorCreado = saProveedor.altaProveedor(proveedor);
        assertTrue(idProveedorCreado > 0);

        Collection<TProveedor> proveedores = saProveedor.leerTodosProveedores();
        assertNotNull("La lista de proveedores no debe ser null", proveedores);
        //assertTrue("Debe haber al menos un proveedor", proveedores.size() > 0);
    }
}
