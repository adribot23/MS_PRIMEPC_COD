package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;

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
    private EntityManager em;

    private int idRutaActiva;

    @Before
    public void setUp() {
        sa = new SAPaqueteImp();
        em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        // Crear ruta activa para pruebas
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Ruta r = new Ruta();
        r.setActivo(1);
        r.setDestino("TestDestino");
        r.setOrigen("TestOrigen");
        r.setDistancia(10.0);
        em.persist(r);
        tx.commit();

        idRutaActiva = r.getId();
    }

    @After
    public void tearDown() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // BORRADO FÍSICO COMPLETO
        em.createQuery("DELETE FROM LineaFactura").executeUpdate();
        em.createQuery("DELETE FROM PaqueteNormal").executeUpdate();
        em.createQuery("DELETE FROM PaqueteExpress").executeUpdate();
        em.createQuery("DELETE FROM Paquete").executeUpdate();
        em.createQuery("DELETE FROM Ruta").executeUpdate();

        tx.commit();
        em.close();
    }

    @Test
    public void testAltaPaqueteNormal() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("ABC123");
        t.setPeso(5.0);
        t.setPrecio(50.0);
        t.setEstado("EN_CAMINO");
        t.setDescuento(10.0);
        t.setIdRuta(idRutaActiva);

        int id = sa.altaPaquete(t);

        assertTrue(id > 0);

        // Comprobar existencia en BD
        TPaquete buscado = sa.buscarPaquete(id);
        assertEquals("ABC123", buscado.getNumSerie());
    }

    @Test
    public void testAltaPaqueteExpress() {
        TPaqueteExpress t = new TPaqueteExpress();
        t.setNumSerie("EXP001");
        t.setPeso(2.0);
        t.setPrecio(20.0);
        t.setEstado("REGISTRADO");
        t.setPrioridad(1);
        t.setIdRuta(idRutaActiva);

        int id = sa.altaPaquete(t);
        assertTrue(id > 0);

        TPaquete result = sa.buscarPaquete(id);
        assertEquals("EXP001", result.getNumSerie());
    }

    @Test
    public void testBajaPaquete() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("XYZ001");
        t.setPeso(4.0);
        t.setPrecio(40.0);
        t.setEstado("REGISTRADO");
        t.setIdRuta(idRutaActiva);
        t.setActivo(1);
        int id = sa.altaPaquete(t);
        assertTrue(id > 0);

        int idBaja = sa.bajaPaquete(id);
        assertEquals(id, idBaja);
    }

    @Test
    public void testModificarPaquete() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setNumSerie("MOD123");
        t.setPeso(3.0);
        t.setPrecio(30.0);
        t.setEstado("REGISTRADO");
        t.setDescuento(5.0);
        t.setIdRuta(idRutaActiva);
        t.setActivo(1);

        int id = sa.altaPaquete(t);

        // modificar
        TPaqueteNormal mod = new TPaqueteNormal();
        mod.setId(id);
        mod.setNumSerie("MOD999");
        mod.setPeso(10.0);
        mod.setPrecio(99.0);
        mod.setEstado("EN_CAMINO");
        mod.setDescuento(15.0);
        mod.setIdRuta(idRutaActiva);
        mod.setActivo(1);

        int res = sa.modificarPaquete(mod);
        assertEquals(id, res);

        TPaquete t2 = sa.buscarPaquete(id);
        assertEquals("MOD999", t2.getNumSerie());
        assertEquals(10.0, t2.getPeso(), 0.01);
    }

    @Test
    public void testMostrarPaquetes() {
        TPaqueteNormal a = new TPaqueteNormal();
        a.setNumSerie("S1");
        a.setPeso(1.0);
        a.setPrecio(10.0);
        a.setEstado("OK");
        a.setDescuento(0.0);
        a.setIdRuta(idRutaActiva);
        sa.altaPaquete(a);

        TPaqueteNormal b = new TPaqueteNormal();
        b.setNumSerie("S2");
        b.setPeso(2.0);
        b.setPrecio(20.0);
        b.setEstado("OK");
        b.setDescuento(0.0);
        b.setIdRuta(idRutaActiva);
        sa.altaPaquete(b);

        Set<TPaquete> set = sa.mostrarPaquetes();
        assertEquals(2, set.size());
    }

    @Test
    public void testMostrarPaquetesPorRuta() {
        TPaqueteNormal a = new TPaqueteNormal();
        a.setNumSerie("R1");
        a.setPeso(1.0);
        a.setPrecio(10.0);
        a.setEstado("OK");
        a.setDescuento(0.0);
        a.setIdRuta(idRutaActiva);
        sa.altaPaquete(a);

        Set<TPaquete> set = sa.mostrarPaquetesPorRuta(idRutaActiva);
        assertEquals(1, set.size());
    }
}

