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

import integracion.Proveedor.DAOProveedor;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import integracion.FactoriaDAO.DAOAbstractFactoryImp;
import negocio.Proveedor.TProveedor;

public class DAOProveedorImpTest {

    private DAOProveedor daoProveedor;
    private int idProveedor = -1;
    private final String nombreTest = "ProveedorTest";

    private Transaction transaccion;

    @Before
    public void setUp() {
    	TManager tManager = TManager.getInstance();
        transaccion = tManager.createTransaction();
        if (transaccion != null) {
            transaccion.start();
            System.out.println("[DEBUG] Transacción iniciada correctamente.");
        }
        daoProveedor = new DAOAbstractFactoryImp().generaDAOProveedor();

        // Crear proveedor de prueba
        TProveedor proveedor = new TProveedor();
        proveedor.setActivo(1);
        proveedor.setNombre(nombreTest);

        idProveedor = daoProveedor.create(proveedor);
        System.out.println("[DEBUG] Proveedor de prueba creado con ID = " + idProveedor);
    }

    @After
    public void tearDown() {
        if (idProveedor > 0) {
            eliminarFisicamente(idProveedor);
            System.out.println("[DEBUG] Proveedor eliminado físicamente: ID = " + idProveedor);
        }
    }

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

    // ==== TESTS ====

    @Test
    public void testCrearProveedor() {
        assertTrue("El proveedor debería haberse creado correctamente", idProveedor > 0);
    }

    @Test
    public void testLeerProveedor() {
        TProveedor proveedor = daoProveedor.read(idProveedor);
        assertNotNull("El proveedor leído no debería ser null", proveedor);
        assertEquals(nombreTest, proveedor.getNombre());
    }

    @Test
    public void testActualizarProveedor() {
        TProveedor proveedor = daoProveedor.read(idProveedor);
        assertNotNull(proveedor);

        proveedor.setNombre("ProvTestActualizado");
        int res = daoProveedor.update(proveedor);
        assertEquals(1, res);

        TProveedor actualizado = daoProveedor.read(idProveedor);
        assertEquals("ProvTestActualizado", actualizado.getNombre());
    }

    @Test
    public void testLeerProveedorPorNombre() {
        TProveedor proveedor = daoProveedor.read_by_name(nombreTest);
        assertNotNull("Debe existir un proveedor con ese nombre", proveedor);
        assertEquals(nombreTest, proveedor.getNombre());
    }

    @Test
    public void testLeerTodosProveedores() {
        Collection<TProveedor> proveedores = daoProveedor.read_all();
        assertNotNull(proveedores);
        assertTrue("Debe haber al menos un proveedor", proveedores.size() > 0);
    }

    @Test
    public void testEliminarProveedor() {
        int res = daoProveedor.delete(idProveedor);
        assertEquals("Debe devolver 1 si se eliminó correctamente", 1, res);

        TProveedor proveedor = daoProveedor.read(idProveedor);
        assertNotNull(proveedor);
        assertEquals("Proveedor debería estar inactivo tras eliminarlo", 0, proveedor.getActivo());
    }
}
