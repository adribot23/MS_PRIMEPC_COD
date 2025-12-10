package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import integracion.EMFSingleton.EMFSingleton;
import negocio.RemitenteJPA.*;

public class SARemitenteImpTest {

    private SARemitente sa;

    @Before
    public void setUp() {
        sa = new SARemitenteImp();
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
            em.createNativeQuery("DELETE FROM PARTICULAR").executeUpdate();
            em.createNativeQuery("DELETE FROM EMPRESA").executeUpdate();
            em.createNativeQuery("DELETE FROM REMITENTE").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAltaRemitenteEmpresa() {
        TEmpresa t = new TEmpresa(0, 1, "EmpresaX", "Calle 1", "123456789", 555);
        int id = sa.altaRemitente(t);
        assertTrue(id > 0);

        TRemitente r = sa.buscarRemitente(id);
        assertNotNull(r);
        assertEquals("EmpresaX", r.getNombre());
        assertEquals(1, r.getActivo());
    }

    @Test
    public void testAltaRemitenteParticular() {
        TParticular t = new TParticular(0, 1, "Juan", "Calle 2", "987654321", "01/01/1990");
        int id = sa.altaRemitente(t);
        assertTrue(id > 0);

        TRemitente r = sa.buscarRemitente(id);
        assertNotNull(r);
        assertEquals("Juan", r.getNombre());
    }

    @Test
    public void testModificarRemitente() {
        TEmpresa t = new TEmpresa(0, 1, "EmpresaY", "Calle 3", "111111111", 777);
        int id = sa.altaRemitente(t);
        t.setId(id);

        t.setNombre("EmpresaY Modificada");
        int mod = sa.modificarRemitente(t);
        assertEquals(id, mod);

        TRemitente r = sa.buscarRemitente(id);
        assertEquals("EmpresaY Modificada", r.getNombre());
    }

    @Test
    public void testBajaRemitente() {
        TParticular t = new TParticular(0, 1, "Ana", "Calle 4", "222222222", "02/02/1995");
        int id = sa.altaRemitente(t);

        int baja = sa.bajaRemitente(id);
        assertEquals(id, baja);

        TRemitente r = sa.buscarRemitente(id);
        assertEquals(0, r.getActivo());
    }

    @Test
    public void testListarTodosRemitentes() {
        TEmpresa t1 = new TEmpresa(0, 1, "Emp1", "Calle 5", "333333333", 101);
        TParticular t2 = new TParticular(0, 1, "Part1", "Calle 6", "444444444", "03/03/2000");
        sa.altaRemitente(t1);
        sa.altaRemitente(t2);

        Set<TRemitente> remitentes = sa.listarTodosRemitentes();
        assertEquals(2, remitentes.size());
    }
}

