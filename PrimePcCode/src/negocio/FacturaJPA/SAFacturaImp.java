/**
 * 
 */
package negocio.FacturaJPA;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import integracion.EMFSingleton.EMFSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import negocio.RemitenteJPA.Remitente;

public class SAFacturaImp implements SAFactura {
	public TCarritoFactura abrirFactura(TFactura tFactura) {
		TCarritoFactura carrito = new TCarritoFactura();
		carrito.set_tLineasFactura(new HashSet<TLineaFactura>());
		carrito.set_tFactura(tFactura);

		return carrito;
	}

	public Integer cerrarFactura(TCarritoFactura tCarritoFactura) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public Integer modificarFactura(TFactura tFactura) {
		// begin-user-code
		return -1;
	}

	public TFacturaTOA buscarFactura(Integer idFactura) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public Set<TFactura> mostrarFacturas() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager(); 
		Set<TFactura> listaFacturas = new HashSet<TFactura>();
		try {
			em.getTransaction().begin();
			TypedQuery<Factura> query = em.createQuery("SELECT a FROM Factura f", Factura.class); 

			List<Factura> facturas = query.getResultList();
			for (Factura f : facturas) {
				// OPTIMISTA por si se modifica factura en otro hilo y altera la version
				em.lock(f, LockModeType.OPTIMISTIC);
				if (f.get_activo() == 1) {
					listaFacturas.add(f.toTransfer());
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaFacturas;
	}

	public Integer devolucion(TLineaFactura tLineaFactura) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public Integer anyadirPaquete(TCarritoFactura tCarritoFactura) {
		return -1;

	}

	public Integer eliminarPaquete(TCarritoFactura tCarritoFactura) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public Set<TFactura> listarFacturasPorRemitente(Integer idRemitente) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Set<TFactura> listaFactura = new HashSet<>();
		try {
			em.getTransaction().begin();

			Remitente remitente = em.find(Remitente.class, idRemitente, LockModeType.OPTIMISTIC);
			TypedQuery<Factura> queryf = em.createNamedQuery("negocio.FacturaJPA.Factura.findByremitente",
					Factura.class); // no sé la verdad habrá
			// q meter el remitente
			// en alfun lao
			if (remitente != null && remitente.getActivo() == 1) {
				for (Factura f : queryf.getResultList()) {
					if (f.get_activo() == 1) {
						listaFactura.add(f.toTransfer());
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaFactura;
	}
}