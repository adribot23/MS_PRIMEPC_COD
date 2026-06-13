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
import negocio.PaqueteJPA.*;
import negocio.RutaJPA.Ruta;

public class SAPaqueteImpTest {

    private SAPaquete sa;
    private int idRutaActiva;
    private String uid;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SAPaqueteImp();
        uid = UUID.randomUUID().toString().substring(0, 6);

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Ruta r = new Ruta();
        r.setActivo(1);
        r.setDestino("Dest_" + uid);
        r.setOrigen("Orig_" + uid);
        r.setDistancia(10.0);
        em.persist(r);
        tx.commit();
        idRutaActiva = r.getId();
        em.close();
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

    // ===================== altaPaquete =====================

    @Test
    public void testAltaPaqueteNormal() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("PN_" + uid);
        t.setPeso(5.0);
        t.setPrecio(50.0);
        t.setEstado("EN_CAMINO");
        t.setDescuento(10.0);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);

        int id = sa.altaPaquete(t);
        assertTrue(id > 0);

        TPaquete buscado = sa.buscarPaquete(id);
        assertEquals("PN_" + uid, buscado.getNumSerie());
    }

    @Test
    public void testAltaPaqueteExpress() {
        TPaqueteExpress t = new TPaqueteExpress();
        t.setNumSerie("PE_" + uid);
        t.setPeso(2.0);
        t.setPrecio(20.0);
        t.setEstado("REGISTRADO");
        t.setPrioridad(1);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);

        int id = sa.altaPaquete(t);
        assertTrue(id > 0);

        TPaquete result = sa.buscarPaquete(id);
        assertEquals("PE_" + uid, result.getNumSerie());
    }

    @Test(expected = RuntimeException.class)
    public void testAltaPaqueteDuplicadoNumSerie() {
        TPaqueteNormal t1 = new TPaqueteNormal();
        t1.setNumSerie("DUP_" + uid);
        t1.setPeso(1.0);
        t1.setPrecio(10.0);
        t1.setEstado("OK");
        t1.setDescuento(0.0);
        t1.setActivo(1);
        t1.setIdRuta(idRutaActiva);
        sa.altaPaquete(t1);

        TPaqueteNormal t2 = new TPaqueteNormal();
        t2.setNumSerie("DUP_" + uid);
        t2.setPeso(2.0);
        t2.setPrecio(20.0);
        t2.setEstado("OK");
        t2.setDescuento(0.0);
        t2.setActivo(1);
        t2.setIdRuta(idRutaActiva);
        sa.altaPaquete(t2);
    }

    @Test(expected = RuntimeException.class)
    public void testAltaPaqueteRutaInexistente() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("NORUTA_" + uid);
        t.setPeso(1.0);
        t.setPrecio(10.0);
        t.setEstado("OK");
        t.setDescuento(0.0);
        t.setActivo(1);
        t.setIdRuta(999999);
        sa.altaPaquete(t);
    }

    @Test
    public void testAltaPaqueteReactivacion() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("REACT_" + uid);
        t.setPeso(3.0);
        t.setPrecio(30.0);
        t.setEstado("REGISTRADO");
        t.setDescuento(5.0);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);
        sa.bajaPaquete(id);

        TPaqueteNormal t2 = new TPaqueteNormal();
        t2.setNumSerie("REACT_" + uid);
        t2.setPeso(4.0);
        t2.setPrecio(40.0);
        t2.setEstado("REGISTRADO");
        t2.setDescuento(8.0);
        t2.setActivo(1);
        t2.setIdRuta(idRutaActiva);
        int id2 = sa.altaPaquete(t2);

        assertEquals(id, id2);
        TPaquete buscado = sa.buscarPaquete(id2);
        assertEquals(1, buscado.getActivo());
    }

    // ===================== bajaPaquete =====================

    @Test
    public void testBajaPaquete() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("BAJA_" + uid);
        t.setPeso(4.0);
        t.setPrecio(40.0);
        t.setEstado("REGISTRADO");
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);

        int idBaja = sa.bajaPaquete(id);
        assertEquals(id, idBaja);

        TPaquete buscado = sa.buscarPaquete(id);
        assertEquals(0, buscado.getActivo());
    }

    @Test(expected = RuntimeException.class)
    public void testBajaPaqueteInexistente() {
        sa.bajaPaquete(999999);
    }

    @Test(expected = RuntimeException.class)
    public void testBajaPaqueteYaInactivo() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("BAJAINACT_" + uid);
        t.setPeso(1.0);
        t.setPrecio(10.0);
        t.setEstado("OK");
        t.setDescuento(0.0);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);
        sa.bajaPaquete(id);
        sa.bajaPaquete(id);
    }

    // ===================== modificarPaquete =====================

    @Test
    public void testModificarPaqueteNormal() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("MOD_" + uid);
        t.setPeso(3.0);
        t.setPrecio(30.0);
        t.setEstado("REGISTRADO");
        t.setDescuento(5.0);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);

        TPaqueteNormal mod = new TPaqueteNormal();
        mod.setId(id);
        mod.setNumSerie("MOD2_" + uid);
        mod.setPeso(10.0);
        mod.setPrecio(99.0);
        mod.setEstado("EN_CAMINO");
        mod.setDescuento(15.0);
        mod.setActivo(1);
        mod.setIdRuta(idRutaActiva);

        int res = sa.modificarPaquete(mod);
        assertEquals(id, res);

        TPaquete t2 = sa.buscarPaquete(id);
        assertEquals("MOD2_" + uid, t2.getNumSerie());
        assertEquals(10.0, t2.getPeso(), 0.01);
    }

    @Test
    public void testModificarPaqueteExpress() {
        TPaqueteExpress t = new TPaqueteExpress();
        t.setNumSerie("MODEXP_" + uid);
        t.setPeso(2.0);
        t.setPrecio(20.0);
        t.setEstado("REGISTRADO");
        t.setPrioridad(1);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);

        TPaqueteExpress mod = new TPaqueteExpress();
        mod.setId(id);
        mod.setNumSerie("MODEXP2_" + uid);
        mod.setPeso(5.0);
        mod.setPrecio(55.0);
        mod.setEstado("EN_CAMINO");
        mod.setPrioridad(3);
        mod.setActivo(1);
        mod.setIdRuta(idRutaActiva);

        int res = sa.modificarPaquete(mod);
        assertEquals(id, res);

        TPaquete t2 = sa.buscarPaquete(id);
        assertEquals("MODEXP2_" + uid, t2.getNumSerie());
    }

    @Test(expected = RuntimeException.class)
    public void testModificarPaqueteInexistente() {
        TPaqueteNormal mod = new TPaqueteNormal();
        mod.setId(999999);
        mod.setNumSerie("NOEXIST_" + uid);
        mod.setPeso(1.0);
        mod.setPrecio(10.0);
        mod.setEstado("OK");
        mod.setDescuento(0.0);
        mod.setActivo(1);
        mod.setIdRuta(idRutaActiva);
        sa.modificarPaquete(mod);
    }

    // ===================== buscarPaquete =====================

    @Test
    public void testBuscarPaqueteExistente() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("BUSCAR_" + uid);
        t.setPeso(1.0);
        t.setPrecio(10.0);
        t.setEstado("OK");
        t.setDescuento(0.0);
        t.setActivo(1);
        t.setIdRuta(idRutaActiva);
        int id = sa.altaPaquete(t);

        TPaquete result = sa.buscarPaquete(id);
        assertNotNull(result);
        assertEquals("BUSCAR_" + uid, result.getNumSerie());
    }

    @Test(expected = RuntimeException.class)
    public void testBuscarPaqueteInexistente() {
        sa.buscarPaquete(999999);
    }

    // ===================== mostrarPaquetes =====================

    @Test
    public void testMostrarPaquetes() {
        TPaqueteNormal a = new TPaqueteNormal();
        a.setNumSerie("S1_" + uid);
        a.setPeso(1.0);
        a.setPrecio(10.0);
        a.setEstado("OK");
        a.setDescuento(0.0);
        a.setActivo(1);
        a.setIdRuta(idRutaActiva);
        sa.altaPaquete(a);

        TPaqueteExpress b = new TPaqueteExpress();
        b.setNumSerie("S2_" + uid);
        b.setPeso(2.0);
        b.setPrecio(20.0);
        b.setEstado("OK");
        b.setPrioridad(1);
        b.setActivo(1);
        b.setIdRuta(idRutaActiva);
        sa.altaPaquete(b);

        Set<TPaquete> set = sa.mostrarPaquetes();
        assertNotNull(set);
        assertTrue(set.size() >= 2);
    }

    // ===================== mostrarPaquetesPorRuta =====================

    @Test
    public void testMostrarPaquetesPorRuta() {
        TPaqueteNormal a = new TPaqueteNormal();
        a.setNumSerie("R1_" + uid);
        a.setPeso(1.0);
        a.setPrecio(10.0);
        a.setEstado("OK");
        a.setDescuento(0.0);
        a.setActivo(1);
        a.setIdRuta(idRutaActiva);
        sa.altaPaquete(a);

        Set<TPaquete> set = sa.mostrarPaquetesPorRuta(idRutaActiva);
        assertNotNull(set);
        assertTrue(set.size() >= 1);
    }

    @Test
    public void testMostrarPaquetesPorRutaInexistente() {
        Set<TPaquete> set = sa.mostrarPaquetesPorRuta(999999);
        assertNotNull(set);
        assertTrue(set.isEmpty());
    }
}

