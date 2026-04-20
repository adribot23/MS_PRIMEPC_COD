package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.UUID;

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
    private String uid;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SATrabajadorImp();
        uid = UUID.randomUUID().toString().substring(0, 6);
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
            em.createNativeQuery("DELETE FROM LINEAFACTURA").executeUpdate();
            em.createNativeQuery("DELETE FROM VINCULACION_RUTA_TRABAJADOR").executeUpdate();
            em.createNativeQuery("DELETE FROM TRABAJADOR_TRANSPORTE").executeUpdate();
            em.createNativeQuery("DELETE FROM NORMAL").executeUpdate();
            em.createNativeQuery("DELETE FROM EXPRESS").executeUpdate();
            em.createNativeQuery("DELETE FROM PAQUETE").executeUpdate();
            em.createNativeQuery("DELETE FROM FACTURA").executeUpdate();
            em.createNativeQuery("DELETE FROM PARTICULAR").executeUpdate();
            em.createNativeQuery("DELETE FROM EMPRESA").executeUpdate();
            em.createNativeQuery("DELETE FROM REMITENTE").executeUpdate();
            em.createNativeQuery("DELETE FROM RUTA").executeUpdate();
            em.createNativeQuery("DELETE FROM TRABAJADOR").executeUpdate();
            em.createNativeQuery("DELETE FROM TRANSPORTE").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // ===================== altaTrabajador =====================

    @Test
    public void testAltaTrabajador() {
        TTrabajador t = new TTrabajador("Trab_" + uid, "DNI_" + uid, 1);
        int id = sa.altaTrabajador(t);
        assertTrue(id > 0);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertNotNull(tLeido);
        assertEquals("Trab_" + uid, tLeido.getNombre());
        assertEquals("DNI_" + uid, tLeido.getDNI());
    }

    @Test
    public void testAltaTrabajadorDuplicadoDNI() {
        TTrabajador t1 = new TTrabajador("Trab1_" + uid, "DNID_" + uid, 1);
        int id1 = sa.altaTrabajador(t1);
        assertTrue(id1 > 0);

        TTrabajador t2 = new TTrabajador("Trab2_" + uid, "DNID_" + uid, 1);
        int id2 = sa.altaTrabajador(t2);
        assertEquals(-1, id2);
    }

    @Test
    public void testAltaTrabajadorReactivacion() {
        TTrabajador t = new TTrabajador("TrabReact_" + uid, "DNIR_" + uid, 1);
        int id = sa.altaTrabajador(t);
        sa.bajaTrabajador(id);

        TTrabajador t2 = new TTrabajador("TrabReact2_" + uid, "DNIR_" + uid, 1);
        int id2 = sa.altaTrabajador(t2);

        assertEquals(id, id2);
        TTrabajador tLeido = sa.leerTrabajador(id2);
        assertEquals(1, tLeido.isActivo());
    }

    // ===================== bajaTrabajador =====================

    @Test
    public void testBajaTrabajador() {
        TTrabajador t = new TTrabajador("TrabBaja_" + uid, "DNIB_" + uid, 1);
        int id = sa.altaTrabajador(t);

        int baja = sa.bajaTrabajador(id);
        assertEquals(id, baja);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertEquals(0, tLeido.isActivo());
    }

    @Test
    public void testBajaTrabajadorInexistente() {
        int baja = sa.bajaTrabajador(999999);
        assertEquals(-1, baja);
    }

    @Test
    public void testBajaTrabajadorConTransporteVinculado() {
        TTrabajador t = new TTrabajador("TrabBajaT_" + uid, "DNIBT_" + uid, 1);
        int idTrab = sa.altaTrabajador(t);

        negocio.TransporteJPA.SATransporte saTrans = new negocio.TransporteJPA.SATransporteImp();
        negocio.TransporteJPA.TTransporte trans = new negocio.TransporteJPA.TTransporte("TransBaja_" + uid, 40, "MAT_BT_" + uid, 1);
        int idTrans = saTrans.altaTransporte(trans);

        negocio.TransporteJPA.TTransporteTrabajador vinc = new negocio.TransporteJPA.TTransporteTrabajador(idTrans, idTrab);
        saTrans.vincularTransporteTrabajador(vinc);

        int baja = sa.bajaTrabajador(idTrab);
        assertEquals(-1, baja);

        // Limpiar
        saTrans.desvincularTransporteTrabajador(vinc);
    }

    // ===================== modificarTrabajador =====================

    @Test
    public void testModificarTrabajador() {
        TTrabajador t = new TTrabajador("TrabMod_" + uid, "DNIM_" + uid, 1);
        int id = sa.altaTrabajador(t);
        t.setId(id);
        t.setNombre("TrabMod2_" + uid);

        int mod = sa.modificarTrabajador(t);
        assertEquals(id, mod);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertEquals("TrabMod2_" + uid, tLeido.getNombre());
    }

    @Test
    public void testModificarTrabajadorInexistente() {
        TTrabajador t = new TTrabajador(999999, 1, "NoExiste_" + uid, "DNINX_" + uid);
        int mod = sa.modificarTrabajador(t);
        assertEquals(-1, mod);
    }

    @Test
    public void testModificarTrabajadorDNIDuplicado() {
        TTrabajador t1 = new TTrabajador("TrabModD1_" + uid, "DNIMD1_" + uid, 1);
        int id1 = sa.altaTrabajador(t1);

        TTrabajador t2 = new TTrabajador("TrabModD2_" + uid, "DNIMD2_" + uid, 1);
        int id2 = sa.altaTrabajador(t2);

        TTrabajador mod = new TTrabajador(id2, 1, "TrabModD2Mod_" + uid, "DNIMD1_" + uid);
        int res = sa.modificarTrabajador(mod);
        assertEquals(-1, res);
    }

    // ===================== leerTrabajador =====================

    @Test
    public void testLeerTrabajadorExistente() {
        TTrabajador t = new TTrabajador("TrabLeer_" + uid, "DNIL_" + uid, 1);
        int id = sa.altaTrabajador(t);

        TTrabajador tLeido = sa.leerTrabajador(id);
        assertNotNull(tLeido);
        assertEquals("TrabLeer_" + uid, tLeido.getNombre());
    }

    @Test
    public void testLeerTrabajadorInexistente() {
        TTrabajador tLeido = sa.leerTrabajador(999999);
        assertNull(tLeido);
    }

    // ===================== leerTodosTrabajadores =====================

    @Test
    public void testLeerTodosTrabajadores() {
        TTrabajador t1 = new TTrabajador("TrabList1_" + uid, "DNILT1_" + uid, 1);
        TTrabajador t2 = new TTrabajador("TrabList2_" + uid, "DNILT2_" + uid, 1);
        sa.altaTrabajador(t1);
        sa.altaTrabajador(t2);

        Set<TTrabajador> trabajadores = sa.leerTodosTrabajadores();
        assertNotNull(trabajadores);
        assertTrue(trabajadores.size() >= 2);
    }

    // ===================== leerTrabajadorPorTransporte =====================

    @Test
    public void testLeerTrabajadorPorTransporte() {
        TTrabajador t = new TTrabajador("TrabTrans_" + uid, "DNITT_" + uid, 1);
        int idTrab = sa.altaTrabajador(t);

        negocio.TransporteJPA.SATransporte saTrans = new negocio.TransporteJPA.SATransporteImp();
        negocio.TransporteJPA.TTransporte transporte = new negocio.TransporteJPA.TTransporte("TransLeer_" + uid, 40, "MAT_LT_" + uid, 1);
        int idTrans = saTrans.altaTransporte(transporte);

        negocio.TransporteJPA.TTransporteTrabajador vinc = new negocio.TransporteJPA.TTransporteTrabajador(idTrans, idTrab);
        saTrans.vincularTransporteTrabajador(vinc);

        Set<TTrabajador> trabajadores = sa.leerTrabajadorPorTransporte(idTrans);
        assertNotNull(trabajadores);
        assertEquals(1, trabajadores.size());
        assertTrue(trabajadores.stream().anyMatch(tr -> tr.getId() == idTrab));
    }

    @Test
    public void testLeerTrabajadorPorTransporteInexistente() {
        Set<TTrabajador> trabajadores = sa.leerTrabajadorPorTransporte(999999);
        assertNotNull(trabajadores);
        assertTrue(trabajadores.isEmpty());
    }
}

