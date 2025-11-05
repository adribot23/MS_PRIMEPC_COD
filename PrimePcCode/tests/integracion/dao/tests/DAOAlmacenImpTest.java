package integracion.dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.Almacen.DAOAlmacen;
import integracion.FactoriaDAO.DAOAbstractFactoryImp;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Almacen.TAlmacen;

public class DAOAlmacenImpTest {

    private DAOAlmacen daoAlmacen;
    private List<Integer> almacenesCreados;
    private Transaction transaccion;

    @Before
    public void setUp() {
        // Crear e iniciar transacción
        TManager tManager = TManager.getInstance();
        transaccion = tManager.createTransaction();
        if (transaccion != null) {
            transaccion.start();
        }

        daoAlmacen = new DAOAbstractFactoryImp().generaDAOAlmacen();
        almacenesCreados = new ArrayList<>();
    }

    @After
    public void tearDown() {
        // Confirmar y cerrar la transacción
        if (transaccion != null) {
            try {
                transaccion.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Eliminar físicamente los almacenes creados
        for (Integer id : almacenesCreados) {
            eliminarFisicamente(id);
        }
    }

    public int eliminarFisicamente(int id) {
        int filasAfectadas = 0;
        try {
            Connection conexion = DriverManager.getConnection("jdbc:h2:./bd/IS2PrimePC", "sa", "");
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

        int id = daoAlmacen.create(almacen);
        almacenesCreados.add(id);
        assertTrue("El ID del almacén debe ser mayor que 0", id > 0);

        TAlmacen almacenLeido = daoAlmacen.read(id);
        assertNotNull("El almacén leído no debe ser null", almacenLeido);
        assertEquals("AlmacenTest", almacenLeido.getNombre());

        almacenLeido.setCapacidadMaxima(200);
        almacenLeido.setNombre("AlmacenTestAct");
        int filasActualizadas = daoAlmacen.update(almacenLeido);
        assertEquals("Debe actualizarse una fila", 1, filasActualizadas);

        TAlmacen almacenActualizado = daoAlmacen.read(id);
        assertEquals(200, almacenActualizado.getCapacidadMaxima());

        int filasEliminadas = daoAlmacen.delete(id);
        assertEquals("Debe marcarse como inactivo un registro", 1, filasEliminadas);

        TAlmacen almacenEliminado = daoAlmacen.read(id);
        assertNotNull(almacenEliminado);
        assertEquals(0, almacenEliminado.getActivo());
    }

    @Test
    public void testLeerPorNombre() {
        TAlmacen almacen = new TAlmacen();
        almacen.setNombre("AlmacenBuscar");
        almacen.setCapacidadMaxima(50);
        almacen.setOcupacion(10);

        int id = daoAlmacen.create(almacen);
        almacenesCreados.add(id);
        assertTrue(id > 0);

        TAlmacen almacenLeido = daoAlmacen.read_by_name("AlmacenBuscar");
        assertNotNull("El almacén leído no debe ser null", almacenLeido);
        assertEquals("El ID debe coincidir", id, almacenLeido.getId());
    }

    @Test
    public void testLeerTodo() {
        Set<TAlmacen> almacenes = daoAlmacen.read_all();
        assertNotNull("El conjunto de almacenes no debe ser null", almacenes);
        assertTrue("Debe contener al menos 0 elementos", almacenes.size() >= 0);
    }

    @Test
    public void testActualizarOcupacion() {
        TAlmacen almacen = new TAlmacen();
        almacen.setNombre("AlmacenOcupacion");
        almacen.setCapacidadMaxima(300);
        almacen.setOcupacion(0);

        int id = daoAlmacen.create(almacen);
        almacenesCreados.add(id);
        assertTrue(id > 0);

        TAlmacen almacenLeido = daoAlmacen.read(id);
        assertNotNull(almacenLeido);

        almacenLeido.setOcupacion(150);
        int filas = daoAlmacen.update(almacenLeido);
        assertTrue("Debe haberse actualizado al menos una fila", filas > 0);

        TAlmacen almacenActualizado = daoAlmacen.read(id);
        assertEquals("La ocupación debe haberse actualizado correctamente", 150, almacenActualizado.getOcupacion());
    }
}
