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
import negocio.TransporteJPA.SATransporte;
import negocio.TransporteJPA.SATransporteImp;
import negocio.TransporteJPA.TTransporte;
import negocio.TransporteJPA.TTransporteTrabajador;
import negocio.TrabajadorJPA.SATrabajador;
import negocio.TrabajadorJPA.SATrabajadorImp;
import negocio.TrabajadorJPA.TTrabajador;

public class SATransporteImpTest {

    private SATransporte sa;
    private String uid;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SATransporteImp();
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

    // ===================== altaTransporte =====================

    @Test
    public void testAltaTransporte() {
        TTransporte t = new TTransporte("Bus_" + uid, 50, "MAT_" + uid, 1);
        int id = sa.altaTransporte(t);
        assertTrue(id > 0);

        TTransporte tLeido = sa.leerTransporte(id);
        assertNotNull(tLeido);
        assertEquals("Bus_" + uid, tLeido.getNombre());
        assertEquals("MAT_" + uid, tLeido.getMatricula());
    }

    @Test
    public void testAltaTransporteDuplicadoMatricula() {
        TTransporte t1 = new TTransporte("Bus1_" + uid, 50, "MATDUP_" + uid, 1);
        int id1 = sa.altaTransporte(t1);
        assertTrue(id1 > 0);

        TTransporte t2 = new TTransporte("Bus2_" + uid, 30, "MATDUP_" + uid, 1);
        int id2 = sa.altaTransporte(t2);
        assertEquals(-1, id2);
    }

    @Test
    public void testAltaTransporteReactivacion() {
        TTransporte t = new TTransporte("BusReact_" + uid, 40, "MATR_" + uid, 1);
        int id = sa.altaTransporte(t);
        sa.bajaTransporte(id);

        TTransporte t2 = new TTransporte("BusReact2_" + uid, 45, "MATR_" + uid, 1);
        int id2 = sa.altaTransporte(t2);

        assertEquals(id, id2);
        TTransporte tLeido = sa.leerTransporte(id2);
        assertEquals(1, tLeido.getActivo());
    }

    // ===================== bajaTransporte =====================

    @Test
    public void testBajaTransporte() {
        TTransporte t = new TTransporte("BusBaja_" + uid, 30, "MATB_" + uid, 1);
        int id = sa.altaTransporte(t);
        int baja = sa.bajaTransporte(id);
        assertEquals(id, baja);

        TTransporte tLeido = sa.leerTransporte(id);
        assertEquals(0, tLeido.getActivo());
    }

    @Test
    public void testBajaTransporteInexistente() {
        int baja = sa.bajaTransporte(999999);
        assertEquals(-1, baja);
    }

    @Test
    public void testBajaTransporteConTrabajadoresVinculados() {
        TTransporte t = new TTransporte("BusBajaT_" + uid, 35, "MATBT_" + uid, 1);
        int idTrans = sa.altaTransporte(t);

        SATrabajador saTrab = new SATrabajadorImp();
        TTrabajador trab = new TTrabajador("TrabBajaT_" + uid, "DNIBT_" + uid, 1);
        int idTrab = saTrab.altaTrabajador(trab);

        TTransporteTrabajador vinc = new TTransporteTrabajador(idTrans, idTrab);
        sa.vincularTransporteTrabajador(vinc);

        int baja = sa.bajaTransporte(idTrans);
        assertEquals(-1, baja);

        // Limpiar
        sa.desvincularTransporteTrabajador(vinc);
    }

    // ===================== modificarTransporte =====================

    @Test
    public void testModificarTransporte() {
        TTransporte t = new TTransporte("BusMod_" + uid, 40, "MATM_" + uid, 1);
        int id = sa.altaTransporte(t);
        t.setId(id);
        t.setNombre("BusMod2_" + uid);

        int mod = sa.modificarTransporte(t);
        assertEquals(id, mod);

        TTransporte tLeido = sa.leerTransporte(id);
        assertEquals("BusMod2_" + uid, tLeido.getNombre());
    }

    @Test
    public void testModificarTransporteInexistente() {
        TTransporte t = new TTransporte("NoExist_" + uid, 10, "MATNX_" + uid, 1);
        t.setId(999999);
        int mod = sa.modificarTransporte(t);
        assertEquals(-1, mod);
    }

    // ===================== leerTransporte =====================

    @Test
    public void testLeerTransporteExistente() {
        TTransporte t = new TTransporte("BusLeer_" + uid, 55, "MATL_" + uid, 1);
        int id = sa.altaTransporte(t);

        TTransporte tLeido = sa.leerTransporte(id);
        assertNotNull(tLeido);
        assertEquals("BusLeer_" + uid, tLeido.getNombre());
    }

    @Test
    public void testLeerTransporteInexistente() {
        TTransporte tLeido = sa.leerTransporte(999999);
        assertNull(tLeido);
    }

    // ===================== leerTodosTransportes =====================

    @Test
    public void testLeerTodosTransportes() {
        TTransporte t1 = new TTransporte("BusA_" + uid, 20, "MATA_" + uid, 1);
        TTransporte t2 = new TTransporte("BusB_" + uid, 25, "MATB2_" + uid, 1);
        sa.altaTransporte(t1);
        sa.altaTransporte(t2);

        Set<TTransporte> transportes = sa.leerTodosTransportes();
        assertNotNull(transportes);
        assertTrue(transportes.size() >= 2);
    }

    // ===================== vincularTransporteTrabajador =====================

    @Test
    public void testVincularTransporteTrabajador() {
        TTransporte t = new TTransporte("BusVinc_" + uid, 60, "MATV_" + uid, 1);
        int idTrans = sa.altaTransporte(t);

        SATrabajador saTrab = new SATrabajadorImp();
        TTrabajador trab = new TTrabajador("TrabVinc_" + uid, "DNIV_" + uid, 1);
        int idTrab = saTrab.altaTrabajador(trab);

        TTransporteTrabajador vinc = new TTransporteTrabajador(idTrans, idTrab);
        int res = sa.vincularTransporteTrabajador(vinc);
        assertTrue(res > 0 || res == 1);

        Set<TTransporte> transportes = sa.leerTransportesPorTrabajador(idTrab);
        assertNotNull(transportes);
        assertTrue(transportes.stream().anyMatch(tr -> tr.getId() == idTrans));
    }

    @Test
    public void testVincularTransporteTrabajadorInexistente() {
        TTransporteTrabajador vinc = new TTransporteTrabajador(999999, 999998);
        int res = sa.vincularTransporteTrabajador(vinc);
        assertEquals(-1, res);
    }

    // ===================== desvincularTransporteTrabajador =====================

    @Test
    public void testDesvincularTransporteTrabajador() {
        TTransporte t = new TTransporte("BusDesv_" + uid, 45, "MATD_" + uid, 1);
        int idTrans = sa.altaTransporte(t);

        SATrabajador saTrab = new SATrabajadorImp();
        TTrabajador trab = new TTrabajador("TrabDesv_" + uid, "DNID_" + uid, 1);
        int idTrab = saTrab.altaTrabajador(trab);

        TTransporteTrabajador vinc = new TTransporteTrabajador(idTrans, idTrab);
        sa.vincularTransporteTrabajador(vinc);

        int res = sa.desvincularTransporteTrabajador(vinc);
        assertTrue(res > 0 || res == 1);

        Set<TTransporte> transportes = sa.leerTransportesPorTrabajador(idTrab);
        assertTrue(transportes == null || transportes.isEmpty() || transportes.stream().noneMatch(tr -> tr.getId() == idTrans));
    }

    // ===================== leerTransportesPorTrabajador =====================

    @Test
    public void testLeerTransportesPorTrabajador() {
        TTransporte t = new TTransporte("BusTrab_" + uid, 50, "MATPT_" + uid, 1);
        int idTrans = sa.altaTransporte(t);

        SATrabajador saTrab = new SATrabajadorImp();
        TTrabajador trab = new TTrabajador("TrabPT_" + uid, "DNIPT_" + uid, 1);
        int idTrab = saTrab.altaTrabajador(trab);

        TTransporteTrabajador vinc = new TTransporteTrabajador(idTrans, idTrab);
        sa.vincularTransporteTrabajador(vinc);

        Set<TTransporte> transportes = sa.leerTransportesPorTrabajador(idTrab);
        assertNotNull(transportes);
        assertEquals(1, transportes.size());
    }

    @Test
    public void testLeerTransportesPorTrabajadorInexistente() {
        Set<TTransporte> transportes = sa.leerTransportesPorTrabajador(999999);
        assertNotNull(transportes);
        assertTrue(transportes.isEmpty());
    }
}


