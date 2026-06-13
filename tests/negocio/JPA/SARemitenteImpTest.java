package negocio.JPA;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import integracion.EMFSingleton.EMFSingleton;
import negocio.RemitenteJPA.*;
import negocio.FacturaJPA.*;
import negocio.PaqueteJPA.*;
import negocio.RutaJPA.Ruta;

public class SARemitenteImpTest {

    private SARemitente sa;
    private String uid;

    @Before
    public void setUp() {
        limpiarBD();
        sa = new SARemitenteImp();
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

    // ===================== altaRemitente =====================

    @Test
    public void testAltaRemitenteEmpresa() {
        TEmpresa t = new TEmpresa(0, 1, "Emp_" + uid, "Calle 1", "600000001", 555);
        int id = sa.altaRemitente(t);
        assertTrue(id > 0);

        TRemitente r = sa.buscarRemitente(id);
        assertNotNull(r);
        assertEquals("Emp_" + uid, r.getNombre());
        assertEquals(1, r.getActivo());
    }

    @Test
    public void testAltaRemitenteParticular() {
        TParticular t = new TParticular(0, 1, "Part_" + uid, "Calle 2", "600000002", "01/01/1990");
        int id = sa.altaRemitente(t);
        assertTrue(id > 0);

        TRemitente r = sa.buscarRemitente(id);
        assertNotNull(r);
        assertEquals("Part_" + uid, r.getNombre());
    }

    @Test
    public void testAltaRemitenteDuplicado() {
        TEmpresa t1 = new TEmpresa(0, 1, "Dup_" + uid, "Calle 3", "600000003", 100);
        int id1 = sa.altaRemitente(t1);
        assertTrue(id1 > 0);

        TEmpresa t2 = new TEmpresa(0, 1, "Dup_" + uid, "Calle 4", "600000004", 200);
        int id2 = sa.altaRemitente(t2);
        assertEquals(-1, id2);
    }

    @Test
    public void testAltaRemitenteReactivacion() {
        TEmpresa t = new TEmpresa(0, 1, "React_" + uid, "Calle 5", "600000005", 300);
        int id = sa.altaRemitente(t);
        sa.bajaRemitente(id);

        TEmpresa t2 = new TEmpresa(0, 1, "React_" + uid, "Calle 6", "600000006", 400);
        int id2 = sa.altaRemitente(t2);

        assertEquals(id, id2);
        TRemitente r = sa.buscarRemitente(id2);
        assertEquals(1, r.getActivo());
    }

    // ===================== bajaRemitente =====================

    @Test
    public void testBajaRemitente() {
        TParticular t = new TParticular(0, 1, "Baja_" + uid, "Calle 7", "600000007", "02/02/1995");
        int id = sa.altaRemitente(t);

        int baja = sa.bajaRemitente(id);
        assertEquals(id, baja);

        TRemitente r = sa.buscarRemitente(id);
        assertEquals(0, r.getActivo());
    }

    @Test
    public void testBajaRemitenteInexistente() {
        int baja = sa.bajaRemitente(999999);
        assertEquals(-1, baja);
    }

    @Test
    public void testBajaRemitenteConFacturasActivas() {
        TEmpresa t = new TEmpresa(0, 1, "BajaFact_" + uid, "Calle 8", "600000008", 500);
        int idRemitente = sa.altaRemitente(t);

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Ruta ruta = new Ruta();
        ruta.setActivo(1);
        ruta.setOrigen("OrigBaja_" + uid);
        ruta.setDestino("DestBaja_" + uid);
        ruta.setDistancia(50.0);
        em.persist(ruta);
        tx.commit();
        em.close();

        SAPaquete saPaquete = new SAPaqueteImp();
        TPaqueteNormal pn = new TPaqueteNormal();
        pn.setNumSerie("BAJAPKG_" + uid);
        pn.setPeso(1.0);
        pn.setPrecio(10.0);
        pn.setEstado("REGISTRADO");
        pn.setDescuento(0.0);
        pn.setActivo(1);
        pn.setIdRuta(ruta.getId());
        int idPaquete = saPaquete.altaPaquete(pn);

        SAFactura saFactura = new SAFacturaImp();
        TFactura tFactura = new TFactura();
        tFactura.set_idRemitente(idRemitente);
        TCarritoFactura carrito = saFactura.abrirFactura(tFactura);
        carrito.set_idPaquete(idPaquete);
        saFactura.anyadirPaquete(carrito);
        saFactura.cerrarFactura(carrito);

        int baja = sa.bajaRemitente(idRemitente);
        assertEquals(-1, baja);
    }

    // ===================== modificarRemitente =====================

    @Test
    public void testModificarRemitenteEmpresa() {
        TEmpresa t = new TEmpresa(0, 1, "ModEmp_" + uid, "Calle 9", "600000009", 777);
        int id = sa.altaRemitente(t);
        t.setId(id);
        t.setNombre("ModEmpMod_" + uid);

        int mod = sa.modificarRemitente(t);
        assertEquals(id, mod);

        TRemitente r = sa.buscarRemitente(id);
        assertEquals("ModEmpMod_" + uid, r.getNombre());
    }

    @Test
    public void testModificarRemitenteParticular() {
        TParticular t = new TParticular(0, 1, "ModPart_" + uid, "Calle 10", "600000010", "05/05/1985");
        int id = sa.altaRemitente(t);
        t.setId(id);
        t.setDireccion("Calle Nueva 10");

        int mod = sa.modificarRemitente(t);
        assertEquals(id, mod);

        TRemitente r = sa.buscarRemitente(id);
        assertEquals("Calle Nueva 10", r.getDireccion());
    }

    @Test
    public void testModificarRemitenteInexistente() {
        TEmpresa t = new TEmpresa(999999, 1, "NoExiste_" + uid, "Calle 11", "600000011", 888);
        int mod = sa.modificarRemitente(t);
        assertEquals(-1, mod);
    }

    // ===================== buscarRemitente =====================

    @Test
    public void testBuscarRemitenteExistente() {
        TEmpresa t = new TEmpresa(0, 1, "Buscar_" + uid, "Calle 12", "600000012", 999);
        int id = sa.altaRemitente(t);

        TRemitente r = sa.buscarRemitente(id);
        assertNotNull(r);
        assertEquals("Buscar_" + uid, r.getNombre());
    }

    @Test
    public void testBuscarRemitenteInexistente() {
        TRemitente r = sa.buscarRemitente(999999);
        assertNull(r);
    }

    // ===================== listarTodosRemitentes =====================

    @Test
    public void testListarTodosRemitentes() {
        TEmpresa t1 = new TEmpresa(0, 1, "List1_" + uid, "Calle 13", "600000013", 101);
        TParticular t2 = new TParticular(0, 1, "List2_" + uid, "Calle 14", "600000014", "03/03/2000");
        sa.altaRemitente(t1);
        sa.altaRemitente(t2);

        Set<TRemitente> remitentes = sa.listarTodosRemitentes();
        assertNotNull(remitentes);
        assertTrue(remitentes.size() >= 2);
    }

    // ===================== calcularPrecioPaquetesRemitente =====================

    @Test
    public void testCalcularPrecioPaquetesRemitente() {
        TEmpresa t = new TEmpresa(0, 1, "Calc_" + uid, "Calle 15", "600000015", 600);
        int idRemitente = sa.altaRemitente(t);

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Ruta ruta = new Ruta();
        ruta.setActivo(1);
        ruta.setOrigen("OrigCalc_" + uid);
        ruta.setDestino("DestCalc_" + uid);
        ruta.setDistancia(20.0);
        em.persist(ruta);
        tx.commit();
        em.close();

        SAPaquete saPaquete = new SAPaqueteImp();
        TPaqueteNormal pn = new TPaqueteNormal();
        pn.setNumSerie("CALCPKG_" + uid);
        pn.setPeso(5.0);
        pn.setPrecio(100.0);
        pn.setEstado("REGISTRADO");
        pn.setDescuento(10.0);
        pn.setActivo(1);
        pn.setIdRuta(ruta.getId());
        int idPaq = saPaquete.altaPaquete(pn);

        SAFactura saFactura = new SAFacturaImp();
        TFactura tf = new TFactura();
        tf.set_idRemitente(idRemitente);
        TCarritoFactura carrito = saFactura.abrirFactura(tf);
        carrito.set_idPaquete(idPaq);
        saFactura.anyadirPaquete(carrito);
        saFactura.cerrarFactura(carrito);

        double total = sa.calcularPrecioPaquetesRemitente(idRemitente);
        assertTrue(total > 0);
    }

    @Test
    public void testCalcularPrecioPaquetesRemitenteSinFacturas() {
        TEmpresa t = new TEmpresa(0, 1, "CalcVacio_" + uid, "Calle 16", "600000016", 700);
        int idRemitente = sa.altaRemitente(t);

        double total = sa.calcularPrecioPaquetesRemitente(idRemitente);
        assertEquals(0.0, total, 0.01);
    }
}

