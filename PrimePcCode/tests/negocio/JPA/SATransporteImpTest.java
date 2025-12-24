package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TransporteJPA.SATransporte;
import negocio.TransporteJPA.SATransporteImp;
import negocio.TransporteJPA.TTransporte;
import negocio.TransporteJPA.TTransporteTrabajador;

public class SATransporteImpTest {

    private SATransporte sa;

    @Before
    public void setUp() {
        sa = new SATransporteImp();
        limpiarBD();
    }

    @After
    public void tearDown() {
        limpiarBD();
    }

    private void limpiarBD() {
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DELETE FROM trabajador_transporte").executeUpdate();
            em.createNativeQuery("DELETE FROM transporte").executeUpdate();
            em.createNativeQuery("DELETE FROM trabajador").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAltaTransporte() {
        TTransporte t = new TTransporte("Bus1", 50, "ABC123", 1);
        int id = sa.altaTransporte(t);
        assertTrue(id > 0);

        TTransporte tLeido = sa.leerTransporte(id);
        assertNotNull(tLeido);
        assertEquals("Bus1", tLeido.getNombre());
    }

    @Test
    public void testModificarTransporte() {
        TTransporte t = new TTransporte("Bus2", 40, "XYZ987", 1);
        int id = sa.altaTransporte(t);
        t.setId(id);

        t.setNombre("BusModificado");
        int mod = sa.modificarTransporte(t);
        assertEquals(id, mod);

        TTransporte tLeido = sa.leerTransporte(id);
        assertEquals("BusModificado", tLeido.getNombre());
    }

    @Test
    public void testBajaTransporte() {
        TTransporte t = new TTransporte("Bus3", 30, "LMN456", 1);
        int id = sa.altaTransporte(t);
        int baja = sa.bajaTransporte(id);
        assertEquals(id, baja);

        TTransporte tLeido = sa.leerTransporte(id);
        assertEquals(0, tLeido.getActivo());
    }

    @Test
    public void testLeerTodosTransportes() {
        TTransporte t1 = new TTransporte("BusA", 20, "AAA111", 1);
        TTransporte t2 = new TTransporte("BusB", 25, "BBB222", 1);
        sa.altaTransporte(t1);
        sa.altaTransporte(t2);

        Set<TTransporte> transportes = sa.leerTodosTransportes();
        assertEquals(2, transportes.size());
    }

}


