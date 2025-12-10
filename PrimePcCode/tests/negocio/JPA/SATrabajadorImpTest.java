package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TrabajadorJPA.SATrabajador;
import negocio.TrabajadorJPA.SATrabajadorImp;
import negocio.TrabajadorJPA.TTrabajador;

public class SATrabajadorImpTest {

    private SATrabajador sa;

    @Before
    public void setUp() {
        sa = new SATrabajadorImp();
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
            em.createNativeQuery("DELETE FROM vinculacion_ruta_trabajador").executeUpdate();
            em.createNativeQuery("DELETE FROM trabajador").executeUpdate();
            em.createNativeQuery("DELETE FROM transporte").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAltaTrabajador() {
        TTrabajador t = new TTrabajador("Juan Perez", "DNI123", 1);
        int id = sa.altaTrabajador(t);
        assertTrue(id > 0);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertNotNull(tLeido);
        assertEquals("Juan Perez", tLeido.getNombre());
    }

    @Test
    public void testModificarTrabajador() {
        TTrabajador t = new TTrabajador("Ana Lopez", "DNI456", 1);
        int id = sa.altaTrabajador(t);
        t.setId(id);

        t.setNombre("Ana Modificada");
        int mod = sa.modificarTrabajador(t);
        assertEquals(id, mod);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertEquals("Ana Modificada", tLeido.getNombre());
    }

    @Test
    public void testBajaTrabajador() {
        TTrabajador t = new TTrabajador("Pedro Gómez", "DNI789", 1);
        int id = sa.altaTrabajador(t);

        int baja = sa.bajaTrabajador(id);
        assertEquals(id, baja);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertEquals(0, tLeido.isActivo());
    }

    @Test
    public void testLeerTodosTrabajadores() {
        TTrabajador t1 = new TTrabajador("Luis", "DNI001", 1);
        TTrabajador t2 = new TTrabajador("Marta", "DNI002", 1);
        sa.altaTrabajador(t1);
        sa.altaTrabajador(t2);

        Set<TTrabajador> trabajadores = sa.leerTodosTrabajadores();
        assertEquals(2, trabajadores.size());
    }

    @Test
    public void testLeerTrabajadorPorTransporte() {
        TTrabajador t = new TTrabajador("Carlos", "DNI555", 1);
        int idTrab = sa.altaTrabajador(t);

        negocio.TransporteJPA.TTransporte transporte = new negocio.TransporteJPA.TTransporte("BusX", 40, "TX555", 1);
        negocio.TransporteJPA.SATransporte saTrans = new negocio.TransporteJPA.SATransporteImp();
        int idTrans = saTrans.altaTransporte(transporte);

        negocio.TransporteJPA.TTransporteTrabajador vinc = new negocio.TransporteJPA.TTransporteTrabajador(idTrans, idTrab);
        saTrans.vincularTransporteTrabajador(vinc);

        Set<TTrabajador> trabajadores = sa.leerTrabajadorPorTransporte(idTrans);
        assertEquals(1, trabajadores.size());
        assertTrue(trabajadores.stream().anyMatch(tr -> tr.getId() == idTrab));
    }
}

