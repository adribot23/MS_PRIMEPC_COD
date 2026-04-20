package negocio.JPA;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.EMFSingleton.EMFSingleton;
import negocio.FacturaJPA.*;
import negocio.PaqueteJPA.*;
import negocio.RemitenteJPA.*;
import negocio.RutaJPA.Ruta;

public class SAFacturaImpTest {

    private SAFactura saFactura;
    private SARemitente saRemitente;
    private SAPaquete saPaquete;

    private int idRemitenteActivo;
    private int idRutaActiva;
    private int idPaquete1;
    private int idPaquete2;

    @Before
    public void setUp() {
        limpiarBD();
        saFactura = new SAFacturaImp();
        saRemitente = new SARemitenteImp();
        saPaquete = new SAPaqueteImp();

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Ruta r = new Ruta();
        r.setActivo(1);
        r.setOrigen("OrigenFact_" + UUID.randomUUID().toString().substring(0, 6));
        r.setDestino("DestinoFact_" + UUID.randomUUID().toString().substring(0, 6));
        r.setDistancia(100.0);
        em.persist(r);

        tx.commit();
        idRutaActiva = r.getId();
        em.close();

        TEmpresa tEmpresa = new TEmpresa(0, 1, "EmpresaFact_" + UUID.randomUUID().toString().substring(0, 6),
                "Calle Factura 1", "600000001", 9001);
        idRemitenteActivo = saRemitente.altaRemitente(tEmpresa);

        TPaqueteNormal pn1 = new TPaqueteNormal();
        pn1.setNumSerie("FACT_PKG1_" + UUID.randomUUID().toString().substring(0, 6));
        pn1.setPeso(5.0);
        pn1.setPrecio(50.0);
        pn1.setEstado("REGISTRADO");
        pn1.setDescuento(0.0);
        pn1.setActivo(1);
        pn1.setIdRuta(idRutaActiva);
        idPaquete1 = saPaquete.altaPaquete(pn1);

        TPaqueteExpress pe2 = new TPaqueteExpress();
        pe2.setNumSerie("FACT_PKG2_" + UUID.randomUUID().toString().substring(0, 6));
        pe2.setPeso(2.0);
        pe2.setPrecio(30.0);
        pe2.setEstado("REGISTRADO");
        pe2.setPrioridad(1);
        pe2.setActivo(1);
        pe2.setIdRuta(idRutaActiva);
        idPaquete2 = saPaquete.altaPaquete(pe2);
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

    // ===================== abrirFactura =====================

    @Test
    public void testAbrirFacturaConRemitenteActivo() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);

        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        assertNotNull(carrito);
        assertNotNull(carrito.get_tLineasFactura());
        assertTrue(carrito.get_tLineasFactura().isEmpty());
    }

    @Test
    public void testAbrirFacturaConRemitenteInexistente() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(999999);

        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        assertNull(carrito);
    }

    // ===================== anyadirPaquete =====================

    @Test
    public void testAnyadirPaqueteAlCarrito() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(idPaquete1);
        Integer res = saFactura.anyadirPaquete(carrito);

        assertEquals(Integer.valueOf(1), res);
        assertEquals(1, carrito.get_tLineasFactura().size());
    }

    @Test
    public void testAnyadirPaqueteDuplicadoAlCarrito() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);

        carrito.set_idPaquete(idPaquete1);
        Integer res = saFactura.anyadirPaquete(carrito);

        assertEquals(Integer.valueOf(-1), res);
    }

    @Test
    public void testAnyadirPaqueteInexistente() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(999999);
        Integer res = saFactura.anyadirPaquete(carrito);

        assertEquals(Integer.valueOf(-1), res);
    }

    // ===================== eliminarPaquete =====================

    @Test
    public void testEliminarPaqueteDelCarrito() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);

        carrito.set_idPaquete(idPaquete1);
        Integer res = saFactura.eliminarPaquete(carrito);

        assertEquals(Integer.valueOf(1), res);
        assertTrue(carrito.get_tLineasFactura().isEmpty());
    }

    @Test
    public void testEliminarPaqueteNoExisteEnCarrito() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(999999);
        Integer res = saFactura.eliminarPaquete(carrito);

        assertEquals(Integer.valueOf(-1), res);
    }

    // ===================== cerrarFactura =====================

    @Test
    public void testCerrarFacturaConPaquetes() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);

        carrito.set_idPaquete(idPaquete2);
        saFactura.anyadirPaquete(carrito);

        Integer idFactura = saFactura.cerrarFactura(carrito);

        assertTrue(idFactura > 0);
    }

    @Test
    public void testCerrarFacturaSinPaquetes() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);

        Integer idFactura = saFactura.cerrarFactura(carrito);

        assertEquals(Integer.valueOf(-1), idFactura);
    }

    // ===================== buscarFactura =====================

    @Test
    public void testBuscarFacturaExistente() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        Integer idFactura = saFactura.cerrarFactura(carrito);

        TFacturaTOA toa = saFactura.buscarFactura(idFactura);

        assertNotNull(toa);
        assertNotNull(toa.get_tFactura());
        assertEquals(idFactura, toa.get_tFactura().get_idFactura());
        assertNotNull(toa.get_tRemitente());
        assertNotNull(toa.get_tLineasFactura());
        assertFalse(toa.get_tLineasFactura().isEmpty());
    }

    @Test
    public void testBuscarFacturaInexistente() {
        TFacturaTOA toa = saFactura.buscarFactura(999999);
        assertNull(toa);
    }

    // ===================== mostrarFacturas =====================

    @Test
    public void testMostrarFacturas() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        saFactura.cerrarFactura(carrito);

        Set<TFactura> facturas = saFactura.mostrarFacturas();

        assertNotNull(facturas);
        assertTrue(facturas.size() >= 1);
    }

    // ===================== modificarFactura =====================

    @Test
    public void testModificarFacturaPrecio() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        Integer idFactura = saFactura.cerrarFactura(carrito);

        TFactura mod = new TFactura();
        mod.set_idFactura(idFactura);
        mod.set_idRemitente(idRemitenteActivo);
        mod.set_precioTotal(999.99);

        Integer res = saFactura.modificarFactura(mod);

        assertEquals(idFactura, res);

        TFacturaTOA toa = saFactura.buscarFactura(idFactura);
        assertEquals(999.99, toa.get_tFactura().get_precioTotal(), 0.01);
    }

    @Test
    public void testModificarFacturaInexistente() {
        TFactura mod = new TFactura();
        mod.set_idFactura(999999);
        mod.set_idRemitente(idRemitenteActivo);
        mod.set_precioTotal(100.0);

        Integer res = saFactura.modificarFactura(mod);

        assertEquals(Integer.valueOf(-1), res);
    }

    // ===================== devolucion =====================

    @Test
    public void testDevolucionPaquete() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        Integer idFactura = saFactura.cerrarFactura(carrito);

        TLineaFactura tLinea = new TLineaFactura();
        tLinea.set_idFactura(idFactura);
        tLinea.set_idPaquete(idPaquete1);

        Integer res = saFactura.devolucion(tLinea);

        assertEquals(Integer.valueOf(1), res);
    }

    @Test
    public void testDevolucionPaqueteYaDevuelto() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        Integer idFactura = saFactura.cerrarFactura(carrito);

        TLineaFactura tLinea = new TLineaFactura();
        tLinea.set_idFactura(idFactura);
        tLinea.set_idPaquete(idPaquete1);

        saFactura.devolucion(tLinea);
        Integer res = saFactura.devolucion(tLinea);

        assertEquals(Integer.valueOf(-1), res);
    }

    // ===================== listarFacturasPorRemitente =====================

    @Test
    public void testListarFacturasPorRemitente() {
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitenteActivo);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete1);
        saFactura.anyadirPaquete(carrito);
        saFactura.cerrarFactura(carrito);

        Set<TFactura> facturas = saFactura.listarFacturasPorRemitente(idRemitenteActivo);

        assertNotNull(facturas);
        assertEquals(1, facturas.size());
    }

    @Test
    public void testListarFacturasPorRemitenteInexistente() {
        Set<TFactura> facturas = saFactura.listarFacturasPorRemitente(999999);

        assertNotNull(facturas);
        assertTrue(facturas.isEmpty());
    }
}
