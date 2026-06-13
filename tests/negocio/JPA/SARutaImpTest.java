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
import negocio.RutaJPA.*;

public class SARutaImpTest {

    private SARuta sa;
    private String uid;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SARutaImp();
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

    // ===================== alta_ruta =====================

    @Test
    public void testAltaRuta() {
        TRuta t = new TRuta();
        t.set_origen("Orig_" + uid);
        t.set_destino("Dest_" + uid);
        t.set_distancia(100.0);
        t.set_activo(1);

        int id = sa.alta_ruta(t);
        assertTrue(id > 0);

        TRuta r = sa.buscar_ruta(id);
        assertNotNull(r);
        assertEquals("Orig_" + uid, r.get_origen());
        assertEquals("Dest_" + uid, r.get_destino());
        assertEquals(100.0, r.get_distancia(), 0.01);
    }

    @Test
    public void testAltaRutaDuplicada() {
        TRuta t1 = new TRuta();
        t1.set_origen("DupOrig_" + uid);
        t1.set_destino("DupDest_" + uid);
        t1.set_distancia(50.0);
        t1.set_activo(1);
        int id1 = sa.alta_ruta(t1);
        assertTrue(id1 > 0);

        TRuta t2 = new TRuta();
        t2.set_origen("DupOrig_" + uid);
        t2.set_destino("DupDest_" + uid);
        t2.set_distancia(60.0);
        t2.set_activo(1);
        int id2 = sa.alta_ruta(t2);

        assertEquals(-1, id2);
    }

    @Test
    public void testAltaRutaReactivacion() {
        TRuta t = new TRuta();
        t.set_origen("ReactOrig_" + uid);
        t.set_destino("ReactDest_" + uid);
        t.set_distancia(80.0);
        t.set_activo(1);
        int id = sa.alta_ruta(t);
        sa.baja_ruta(id);

        TRuta t2 = new TRuta();
        t2.set_origen("ReactOrig_" + uid);
        t2.set_destino("ReactDest_" + uid);
        t2.set_distancia(90.0);
        t2.set_activo(1);
        int id2 = sa.alta_ruta(t2);

        assertEquals(id, id2);
        TRuta r = sa.buscar_ruta(id2);
        assertEquals(1, r.get_activo());
        assertEquals(90.0, r.get_distancia(), 0.01);
    }

    // ===================== baja_ruta =====================

    @Test
    public void testBajaRuta() {
        TRuta t = new TRuta();
        t.set_origen("BajaOrig_" + uid);
        t.set_destino("BajaDest_" + uid);
        t.set_distancia(30.0);
        t.set_activo(1);
        int id = sa.alta_ruta(t);

        int baja = sa.baja_ruta(id);
        assertEquals(id, baja);

        TRuta r = sa.buscar_ruta(id);
        assertEquals(0, r.get_activo());
    }

    @Test
    public void testBajaRutaInexistente() {
        int baja = sa.baja_ruta(999999);
        assertEquals(-1, baja);
    }

    @Test
    public void testBajaRutaConVinculaciones() {
        TRuta t = new TRuta();
        t.set_origen("BajaVincOrig_" + uid);
        t.set_destino("BajaVincDest_" + uid);
        t.set_distancia(50.0);
        t.set_activo(1);
        int idRuta = sa.alta_ruta(t);

        negocio.TrabajadorJPA.SATrabajador saTrab = new negocio.TrabajadorJPA.SATrabajadorImp();
        negocio.TrabajadorJPA.TTrabajador trab = new negocio.TrabajadorJPA.TTrabajador("TrabBaja_" + uid, "DNI_BV_" + uid, 1);
        int idTrab = saTrab.altaTrabajador(trab);

        SAVinculacionRutaTrabajador saVinc = new SAVinculacionRutaTrabajadorImp();
        TVinculacionRutaTrabajador vinc = new TVinculacionRutaTrabajador();
        vinc.set_id_ruta(idRuta);
        vinc.set_id_trabajador(idTrab);
        vinc.set_hora_salida("08:00");
        vinc.set_estado("ACTIVA");
        vinc.set_fecha_asignacion("2025-01-01");
        saVinc.vincular_ruta_trabajador(vinc);

        int baja = sa.baja_ruta(idRuta);
        assertEquals(-3, baja);

        // Limpiar vinculación y trabajador
        saVinc.desvincular_ruta_trabajador(vinc);
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createNativeQuery("DELETE FROM trabajador_transporte").executeUpdate();
        em.createNativeQuery("DELETE FROM vinculacion_ruta_trabajador").executeUpdate();
        em.createNativeQuery("DELETE FROM trabajador").executeUpdate();
        tx.commit();
        em.close();
    }

    // ===================== modificar_ruta =====================

    @Test
    public void testModificarRuta() {
        TRuta t = new TRuta();
        t.set_origen("ModOrig_" + uid);
        t.set_destino("ModDest_" + uid);
        t.set_distancia(70.0);
        t.set_activo(1);
        int id = sa.alta_ruta(t);

        TRuta mod = new TRuta();
        mod.set_id(id);
        mod.set_origen("ModOrigNew_" + uid);
        mod.set_destino("ModDestNew_" + uid);
        mod.set_distancia(120.0);

        int res = sa.modificar_ruta(mod);
        assertEquals(id, res);

        TRuta r = sa.buscar_ruta(id);
        assertEquals("ModOrigNew_" + uid, r.get_origen());
        assertEquals("ModDestNew_" + uid, r.get_destino());
        assertEquals(120.0, r.get_distancia(), 0.01);
    }

    @Test
    public void testModificarRutaInexistente() {
        TRuta mod = new TRuta();
        mod.set_id(999999);
        mod.set_origen("NoExist_" + uid);
        mod.set_destino("NoExistDest_" + uid);
        mod.set_distancia(10.0);

        int res = sa.modificar_ruta(mod);
        assertEquals(-1, res);
    }

    // ===================== buscar_ruta =====================

    @Test
    public void testBuscarRutaExistente() {
        TRuta t = new TRuta();
        t.set_origen("BuscarOrig_" + uid);
        t.set_destino("BuscarDest_" + uid);
        t.set_distancia(55.0);
        t.set_activo(1);
        int id = sa.alta_ruta(t);

        TRuta r = sa.buscar_ruta(id);
        assertNotNull(r);
        assertEquals("BuscarOrig_" + uid, r.get_origen());
    }

    @Test
    public void testBuscarRutaInexistente() {
        TRuta r = sa.buscar_ruta(999999);
        assertNull(r);
    }

    // ===================== listar_rutas =====================

    @Test
    public void testListarRutas() {
        TRuta t1 = new TRuta();
        t1.set_origen("ListOrig1_" + uid);
        t1.set_destino("ListDest1_" + uid);
        t1.set_distancia(10.0);
        t1.set_activo(1);
        sa.alta_ruta(t1);

        TRuta t2 = new TRuta();
        t2.set_origen("ListOrig2_" + uid);
        t2.set_destino("ListDest2_" + uid);
        t2.set_distancia(20.0);
        t2.set_activo(1);
        sa.alta_ruta(t2);

        Set<TRuta> rutas = sa.listar_rutas();
        assertNotNull(rutas);
        assertTrue(rutas.size() >= 2);
    }
}
