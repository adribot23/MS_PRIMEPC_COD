package integracion.dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import integracion.Producto.DAOProducto;
import integracion.FactoriaDAO.DAOAbstractFactoryImp;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TProducto;

public class DAOProductoImpTest {

    private DAOProducto daoProducto;
    private int idProducto = -1;
    private int idAlmacenDummy = -1;
    private Transaction transaccion;
    private final String modeloTest = "ModeloTest";
    private final String marcaTest = "MarcaTest";

    @Before
    public void setUp() {
        // Iniciar transacción
        TManager tManager = TManager.getInstance();
        transaccion = tManager.createTransaction();
        if (transaccion != null) {
            transaccion.start();
        }

        daoProducto = new DAOAbstractFactoryImp().generaDAOProducto();

        // Crear almacén dummy para cumplir la FK
        idAlmacenDummy = crearAlmacenDummy();

        // Crear un producto de prueba inicial
        TProducto producto = new TProducto();
        producto.setActivo(1);
        producto.setPrecio(199.99);
        producto.setUnidades(10);
        producto.setMarca(marcaTest);
        producto.setModelo(modeloTest);
        producto.setIdAlmacen(idAlmacenDummy); 

        idProducto = daoProducto.create(producto);
    }

    @After
    public void tearDown() {
        if (transaccion != null) {
            try {
                transaccion.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (idProducto > 0) eliminarFisicamenteProducto(idProducto);
        if (idAlmacenDummy > 0) eliminarFisicamenteAlmacen(idAlmacenDummy);
    }

    // ===== Métodos auxiliares =====

    private int crearAlmacenDummy() {
        int id = -1;
        try (Connection c = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "")) {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO ALMACEN (NOMBRE, CAPACIDAD_MAX, OCUPACION, ACTIVO) VALUES ('AlmacenTest', 100, 0, 1)",
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

    // ===== Tests =====

    @Test
    public void testCrearProducto() {
        assertTrue("El ID del producto creado debe ser mayor que 0", idProducto > 0);
    }

    @Test
    public void testLeerProducto() {
        TProducto producto = daoProducto.read(idProducto);
        assertNotNull("El producto leído no debe ser null", producto);
        assertEquals(idProducto, producto.getId());
    }

    @Test
    public void testActualizarProducto() {
        TProducto producto = daoProducto.read(idProducto);
        assertNotNull(producto);

        producto.setPrecio(279.99);
        producto.setUnidades(25);

        int res = daoProducto.update(producto);
        assertEquals(1, res);

        TProducto actualizado = daoProducto.read(idProducto);
        assertEquals(279.99, actualizado.getPrecio(), 0.01);
        assertEquals(25, actualizado.getUnidades());
    }

    @Test
    public void testLeerTodosLosProductos() {
        Collection<TProducto> productos = daoProducto.read_all();
        assertNotNull(productos);
        assertTrue(productos.size() > 0);
    }

    @Test
    public void testEliminarProducto() {
        int res = daoProducto.delete(idProducto);
        assertEquals(1, res);

        TProducto producto = daoProducto.read(idProducto);
        assertNotNull(producto);
        assertEquals(0, producto.getActivo());
    }
}
