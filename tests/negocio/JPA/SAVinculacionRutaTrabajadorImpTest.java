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
import negocio.TrabajadorJPA.SATrabajador;
import negocio.TrabajadorJPA.SATrabajadorImp;
import negocio.TrabajadorJPA.TTrabajador;

public class SAVinculacionRutaTrabajadorImpTest {

    private SAVinculacionRutaTrabajador sa;
    private SARuta saRuta;
    private SATrabajador saTrab;
    private String uid;
    private int idRuta;
    private int idTrab;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SAVinculacionRutaTrabajadorImp();
        saRuta = new SARutaImp();
        saTrab = new SATrabajadorImp();
        uid = UUID.randomUUID().toString().substring(0, 6);

        // Crear ruta y trabajador base para tests
        TRuta tRuta = new TRuta();
        tRuta.set_origen("OrigVinc_" + uid);
        tRuta.set_destino("DestVinc_" + uid);
        tRuta.set_distancia(100.0);
        tRuta.set_activo(1);
        idRuta = saRuta.alta_ruta(tRuta);

        TTrabajador tTrab = new TTrabajador("TrabVinc_" + uid, "DNIV_" + uid, 1);
        idTrab = saTrab.altaTrabajador(tTrab);
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

    private TVinculacionRutaTrabajador crearVinculacion(int idRuta, int idTrab) {
        TVinculacionRutaTrabajador v = new TVinculacionRutaTrabajador();
        v.set_id_ruta(idRuta);
        v.set_id_trabajador(idTrab);
        v.set_hora_salida("08:00");
        v.set_estado("ACTIVA");
        v.set_fecha_asignacion("2025-01-01");
        return v;
    }

    // ===================== vincular_ruta_trabajador =====================

    @Test
    public void testVincularRutaTrabajador() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        int res = sa.vincular_ruta_trabajador(v);
        assertEquals(1, res);
    }

    @Test
    public void testVincularRutaTrabajadorDuplicado() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        sa.vincular_ruta_trabajador(v);

        TVinculacionRutaTrabajador v2 = crearVinculacion(idRuta, idTrab);
        int res = sa.vincular_ruta_trabajador(v2);
        assertEquals(-1, res);
    }

    @Test
    public void testVincularRutaInexistente() {
        TVinculacionRutaTrabajador v = crearVinculacion(999999, idTrab);
        int res = sa.vincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    @Test
    public void testVincularTrabajadorInexistente() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, 999999);
        int res = sa.vincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    @Test
    public void testVincularRutaInactiva() {
        saRuta.baja_ruta(idRuta);
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        int res = sa.vincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    @Test
    public void testVincularTrabajadorInactivo() {
        saTrab.bajaTrabajador(idTrab);
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        int res = sa.vincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    // ===================== desvincular_ruta_trabajador =====================

    @Test
    public void testDesvincularRutaTrabajador() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        sa.vincular_ruta_trabajador(v);

        int res = sa.desvincular_ruta_trabajador(v);
        assertEquals(1, res);
    }

    @Test
    public void testDesvincularNoExistente() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        int res = sa.desvincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    @Test
    public void testDesvincularRutaInexistente() {
        TVinculacionRutaTrabajador v = crearVinculacion(999999, idTrab);
        int res = sa.desvincular_ruta_trabajador(v);
        assertEquals(-1, res);
    }

    // ===================== listar_vinculaciones_por_trabajador =====================

    @Test
    public void testListarVinculacionesPorTrabajador() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        sa.vincular_ruta_trabajador(v);

        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_trabajador(idTrab);
        assertNotNull(vinculaciones);
        assertEquals(1, vinculaciones.size());

        TVinculacionRutaTrabajador vResult = vinculaciones.iterator().next();
        assertEquals(idRuta, vResult.get_id_ruta());
        assertEquals(idTrab, vResult.get_id_trabajador());
    }

    @Test
    public void testListarVinculacionesPorTrabajadorSinVinculaciones() {
        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_trabajador(idTrab);
        assertNotNull(vinculaciones);
        assertTrue(vinculaciones.isEmpty());
    }

    @Test
    public void testListarVinculacionesPorTrabajadorInexistente() {
        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_trabajador(999999);
        assertNotNull(vinculaciones);
        assertTrue(vinculaciones.isEmpty());
    }

    @Test
    public void testListarVinculacionesPorTrabajadorMultiples() {
        // Crear segunda ruta
        TRuta tRuta2 = new TRuta();
        tRuta2.set_origen("OrigVinc2_" + uid);
        tRuta2.set_destino("DestVinc2_" + uid);
        tRuta2.set_distancia(200.0);
        tRuta2.set_activo(1);
        int idRuta2 = saRuta.alta_ruta(tRuta2);

        sa.vincular_ruta_trabajador(crearVinculacion(idRuta, idTrab));
        sa.vincular_ruta_trabajador(crearVinculacion(idRuta2, idTrab));

        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_trabajador(idTrab);
        assertNotNull(vinculaciones);
        assertEquals(2, vinculaciones.size());
    }

    // ===================== listar_vinculaciones_por_ruta =====================

    @Test
    public void testListarVinculacionesPorRuta() {
        TVinculacionRutaTrabajador v = crearVinculacion(idRuta, idTrab);
        sa.vincular_ruta_trabajador(v);

        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_ruta(idRuta);
        assertNotNull(vinculaciones);
        assertEquals(1, vinculaciones.size());

        TVinculacionRutaTrabajador vResult = vinculaciones.iterator().next();
        assertEquals(idRuta, vResult.get_id_ruta());
    }

    @Test
    public void testListarVinculacionesPorRutaSinVinculaciones() {
        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_ruta(idRuta);
        assertNotNull(vinculaciones);
        assertTrue(vinculaciones.isEmpty());
    }

    @Test
    public void testListarVinculacionesPorRutaInexistente() {
        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_ruta(999999);
        assertNotNull(vinculaciones);
        assertTrue(vinculaciones.isEmpty());
    }

    @Test
    public void testListarVinculacionesPorRutaMultiplesTrabajadores() {
        // Crear segundo trabajador
        TTrabajador tTrab2 = new TTrabajador("TrabVinc2_" + uid, "DNIV2_" + uid, 1);
        int idTrab2 = saTrab.altaTrabajador(tTrab2);

        sa.vincular_ruta_trabajador(crearVinculacion(idRuta, idTrab));
        sa.vincular_ruta_trabajador(crearVinculacion(idRuta, idTrab2));

        Set<TVinculacionRutaTrabajador> vinculaciones = sa.listar_vinculaciones_por_ruta(idRuta);
        assertNotNull(vinculaciones);
        assertEquals(2, vinculaciones.size());
    }
}
