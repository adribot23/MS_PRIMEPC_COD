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

import negocio.factoria.FactoriaNegocio;
import negocio.serviciosAplicacion.SAProveedor;
import negocio.transfers.TProveedor;

public class SAProveedorImpTest {

    private SAProveedor saProveedor;
    private int idProveedorCreado;

    @Before
    public void setUp() {
        saProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
    }

    @After
    public void tearDown() {
        if (idProveedorCreado > 0) {
            eliminarFisicamente(idProveedorCreado);
        }
    }
    
    public int eliminarFisicamente(int id) {  //solo para el test
	    int filasAfectadas = 0;
	    try {
	    	Connection conexion = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
	        String sql = "DELETE FROM PROVEEDOR WHERE ID = ?";
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
    public void testAltaLeerModificarBajaProveedor() {
        // 1. Alta de proveedor
        TProveedor proveedor = new TProveedor();
        proveedor.setNombre("ProveedorTest");
        proveedor.setActivo(1);

        idProveedorCreado = saProveedor.altaProveedor(proveedor);
        assertTrue(idProveedorCreado > 0);

        // 2. Leer proveedor
        TProveedor proveedorLeido = saProveedor.leerProveedor(idProveedorCreado);
        assertNotNull(proveedorLeido);
        assertEquals("ProveedorTest", proveedorLeido.getNombre());

        // 3. Modificar proveedor
        proveedorLeido.setNombre("ProveedorTestModificado");
        int resMod = saProveedor.modificarProveedor(proveedorLeido);
        assertEquals(1, resMod);

        TProveedor proveedorModificado = saProveedor.leerProveedor(idProveedorCreado);
        assertEquals("ProveedorTestModificado", proveedorModificado.getNombre());

        // 4. Baja proveedor (debería funcionar porque no tiene productos vinculados)
        int resBaja = saProveedor.bajaProveedor(idProveedorCreado);
        assertEquals(1, resBaja);

        TProveedor proveedorBaja = saProveedor.leerProveedor(idProveedorCreado);
        assertNull(proveedorBaja); // No debería poder leerse si está inactivo
    }

    @Test
    public void testLeerTodosProveedores() {
        // Alta de un proveedor para asegurarnos que hay al menos uno
        TProveedor proveedor = new TProveedor();
        proveedor.setNombre("ProveedorMultiple");
        proveedor.setActivo(1);

        idProveedorCreado = saProveedor.altaProveedor(proveedor);

        Collection<TProveedor> proveedores = saProveedor.leerTodosProveedores();
        assertNotNull(proveedores);
        assertTrue(proveedores.size() > 0);
    }

    @Test
    public void testLeerProveedorPorProductoNoExiste() {
        TProveedor proveedor = saProveedor.leerProveedorPorProducto(-1); // Producto inválido
        assertNull(proveedor);
    }

}
