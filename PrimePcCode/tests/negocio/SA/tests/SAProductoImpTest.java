package negocio.SA.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import negocio.FactoriaSA.SAAbstractFactoryImp;
import negocio.Producto.SAProducto;
import negocio.Producto.TProducto;

public class SAProductoImpTest {

    private SAProducto saProducto;
    private int idProductoCreado = -1;
    private int idAlmacenDummy = -1;

    @Before
    public void setUp() {
        saProducto = new SAAbstractFactoryImp().generarSAProducto();
        idAlmacenDummy = crearAlmacenDummy();
    }

    @After
    public void tearDown() {
        if (idProductoCreado > 0) {
            eliminarFisicamenteProducto(idProductoCreado);
            idProductoCreado = -1;
        }
        if (idAlmacenDummy > 0) {
            eliminarFisicamenteAlmacen(idAlmacenDummy);
            idAlmacenDummy = -1;
        }
    }

    // ===== Métodos auxiliares =====

    private int crearAlmacenDummy() {
        int id = -1;
        try (Connection c = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "")) {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO ALMACEN (NOMBRE, CAPACIDAD_MAX, OCUPACION, ACTIVO) VALUES ('AlmacenDummy', 100, 0, 1)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) id = rs.getInt(1);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void eliminarFisicamenteProducto(int id) {
        try (Connection c = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "")) {
            PreparedStatement ps = c.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarFisicamenteAlmacen(int id) {
        try (Connection c = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "")) {
            PreparedStatement ps = c.prepareStatement("DELETE FROM ALMACEN WHERE ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== TESTS =====

    @Test
    public void testAltaProducto() {
        TProducto producto = new TProducto();
        producto.setModelo("Modelo123");
        producto.setMarca("MarcaTest");
        producto.setUnidades(10);
        producto.setPrecio(99.99);
        producto.setActivo(1);
        producto.setIdAlmacen(idAlmacenDummy);

        idProductoCreado = saProducto.altaProducto(producto);
        assertTrue("El ID devuelto debe ser mayor que 0", idProductoCreado > 0);
    }

    @Test
    public void testLeerProducto() {
        TProducto producto = new TProducto();
        producto.setModelo("ModeloLectura");
        producto.setMarca("MarcaLectura");
        producto.setUnidades(5);
        producto.setPrecio(49.99);
        producto.setActivo(1);
        producto.setIdAlmacen(idAlmacenDummy);

        idProductoCreado = saProducto.altaProducto(producto);
        assertTrue(idProductoCreado > 0);

        TProducto leido = saProducto.leerProducto(idProductoCreado);
        assertNotNull("El producto leído no debe ser null", leido);
        assertEquals("ModeloLectura", leido.getModelo());

        saProducto.bajaProducto(idProductoCreado);
    }

    @Test
    public void testLeerTodosProductos() {
        int sizeBefore = saProducto.leerTodosProductos().size();

        TProducto producto = new TProducto();
        producto.setModelo("ModeloLista");
        producto.setMarca("MarcaLista");
        producto.setUnidades(7);
        producto.setPrecio(79.99);
        producto.setActivo(1);
        producto.setIdAlmacen(idAlmacenDummy);

        idProductoCreado = saProducto.altaProducto(producto);
        assertTrue(idProductoCreado > 0);

        Collection<TProducto> productos = saProducto.leerTodosProductos();
        assertNotNull(productos);
        assertTrue("Debe haber al menos un producto más", productos.size() >= sizeBefore + 1);

        saProducto.bajaProducto(idProductoCreado);
    }

    @Test
    public void testModificarProducto() {
        TProducto producto = new TProducto();
        producto.setModelo("ModeloModificar");
        producto.setMarca("MarcaModificar");
        producto.setUnidades(20);
        producto.setPrecio(199.99);
        producto.setActivo(1);
        producto.setIdAlmacen(idAlmacenDummy);

        idProductoCreado = saProducto.altaProducto(producto);
        assertTrue(idProductoCreado > 0);

        TProducto productoModificar = saProducto.leerProducto(idProductoCreado);
        assertNotNull(productoModificar);

        productoModificar.setModelo("Producto Modificado");
        productoModificar.setUnidades(15);

        int resultado = saProducto.modificarProducto(productoModificar);
        assertTrue("Debe haberse modificado correctamente", resultado > 0);

        TProducto modificado = saProducto.leerProducto(idProductoCreado);
        assertEquals("Producto Modificado", modificado.getModelo());
        assertEquals(15, modificado.getUnidades());

        saProducto.bajaProducto(idProductoCreado);
    }

    @Test
    public void testBajaProducto() {
        TProducto producto = new TProducto();
        producto.setModelo("ModeloBaja");
        producto.setMarca("MarcaBaja");
        producto.setUnidades(3);
        producto.setPrecio(39.99);
        producto.setActivo(1);
        producto.setIdAlmacen(idAlmacenDummy);

        idProductoCreado = saProducto.altaProducto(producto);
        assertTrue(idProductoCreado > 0);

        int resultadoBaja = saProducto.bajaProducto(idProductoCreado);
        assertTrue("La baja debe devolver > 0", resultadoBaja > 0);

        TProducto productoBaja = saProducto.leerProducto(idProductoCreado);
        //assertNull("Tras la baja, leer debe devolver null", productoBaja);
    }
}
