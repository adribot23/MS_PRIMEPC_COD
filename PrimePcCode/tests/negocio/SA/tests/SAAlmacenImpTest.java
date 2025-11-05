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

import negocio.Almacen.SAAlmacen;
import negocio.Almacen.TAlmacen;
import negocio.Producto.SAProducto;
import negocio.Producto.TProducto;
import negocio.FactoriaSA.SAAbstractFactoryImp;

public class SAAlmacenImpTest {

    private SAAlmacen saAlmacen;
    private SAProducto saProducto;

    private int idAlmacenCreado = -1;
    private int idAlmacen = -1;
    private int idProducto = -1;

    @Before
    public void setUp() {
        SAAbstractFactoryImp factoria = new SAAbstractFactoryImp();
        saAlmacen = factoria.generarSAAlmacen();
        saProducto = factoria.generarSAProducto();
    }

    @After
    public void tearDown() {
        if (idAlmacenCreado > 0) {
            eliminarFisicamenteAlmacen(idAlmacenCreado);
        }
        if (idAlmacen > 0) {
            if (idProducto > 0) {
                //saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
            }
            eliminarFisicamenteAlmacen(idAlmacen);
        }
        if (idProducto > 0) {
            eliminarFisicamenteProducto(idProducto);
        }
    }

    public int eliminarFisicamenteAlmacen(int id) {
        int filasAfectadas = 0;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "");
            String sql = "DELETE FROM ALMACEN WHERE ID = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            filasAfectadas = ps.executeUpdate();
            ps.close();
            conexion.close();
            System.out.println("[DEBUG] Almacén eliminado físicamente: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    public int eliminarFisicamenteProducto(int id) {
        int filasAfectadas = 0;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "");
            String sql = "DELETE FROM PRODUCTO WHERE ID = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            filasAfectadas = ps.executeUpdate();
            ps.close();
            conexion.close();
            System.out.println("[DEBUG] Producto eliminado físicamente: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    @Test
    public void testAltaLeerModificarBajaAlmacen() {
        TAlmacen almacen = new TAlmacen();
        almacen.setCapacidadMaxima(100);
        almacen.setOcupacion(0);
        almacen.setNombre("Test Almacen");

        idAlmacenCreado = saAlmacen.altaAlmacen(almacen);
        assertTrue("El ID devuelto debe ser mayor que 0", idAlmacenCreado > 0);

        TAlmacen almacenLeido = saAlmacen.leerAlmacen(idAlmacenCreado);
        assertNotNull("El almacén leído no debe ser null", almacenLeido);
        assertEquals("Test Almacen", almacenLeido.getNombre());
        assertEquals(100, almacenLeido.getCapacidadMaxima());
        assertEquals(0, almacenLeido.getOcupacion());
        assertEquals(1, almacenLeido.getActivo());

        almacenLeido.setCapacidadMaxima(200);
        int resMod = saAlmacen.modificarAlmacen(almacenLeido);
        assertEquals(1, resMod);

        TAlmacen almacenModificado = saAlmacen.leerAlmacen(idAlmacenCreado);
        assertEquals(200, almacenModificado.getCapacidadMaxima());

        Collection<TAlmacen> todos = saAlmacen.leerTodosAlmacenes();
        assertNotNull("La colección de almacenes no debe ser null", todos);
        assertTrue("Debe contener al menos un elemento", todos.size() > 0);

        int resBaja = saAlmacen.bajaAlmacen(idAlmacenCreado);
        assertEquals(1, resBaja);

        TAlmacen almacenBaja = saAlmacen.leerAlmacen(idAlmacenCreado);
        //assertNull("El almacén dado de baja debe ser null", almacenBaja);
    }
}
