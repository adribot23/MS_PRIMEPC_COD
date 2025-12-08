/**
 * 
 */
package negocio.FacturaJPA;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import integracion.EMFSingleton.EMFSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import negocio.PaqueteJPA.Paquete;
import negocio.RemitenteJPA.Remitente;

public class SAFacturaImp implements SAFactura {

	public TCarritoFactura abrirFactura(TFactura tFactura) {
		TCarritoFactura carrito = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Remitente remitente = em.find(Remitente.class, tFactura.get_idRemitente(), LockModeType.OPTIMISTIC);
			if (remitente != null && remitente.getActivo() == 1) {
				carrito = new TCarritoFactura();
				carrito.set_tLineasFactura(new HashSet<TLineaFactura>());
				carrito.set_tFactura(tFactura);
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return carrito;
	}

	public Integer cerrarFactura(TCarritoFactura tCarritoFactura) {
		Integer res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Remitente remitente = em.find(Remitente.class, tCarritoFactura.get_tFactura().get_idRemitente());
			if (remitente != null && remitente.getActivo() != 0) {
				double total = 0;
				Factura factura = new Factura();
				factura.set_Remitente(remitente);
				factura.set_activo(1);
				em.persist(factura);
				Set<LineaFactura> lineasFacturaPersistentes = new HashSet<LineaFactura>();
				Set<TLineaFactura> tLineasFacturaPersistente = new HashSet<TLineaFactura>();
				for (TLineaFactura tLineaFactura : tCarritoFactura.get_tLineasFactura()) {
					Paquete paquete = em.find(Paquete.class, tLineaFactura.get_idPaquete());
					if (paquete != null && paquete.getActivo() == 1 && paquete.getFactura().get_idFactura() == null) {
						LineaFactura lineaFactura = new LineaFactura(factura, paquete);
						lineaFactura.set_devuelto(0);
						lineaFactura.set_precioTotal(paquete.getPrecio());
						tLineaFactura.set_precioTotal(lineaFactura.get_precioTotal());
						tLineasFacturaPersistente.add(tLineaFactura);
						em.persist(lineaFactura);
						total += lineaFactura.get_precioTotal();
						lineasFacturaPersistentes.add(lineaFactura);
					}
				}
				if (total != 0) {
					factura.set_precioTotal(total);
					factura.set_lineaFactura(lineasFacturaPersistentes);
					em.persist(factura);
					em.getTransaction().commit();
					res = factura.get_idFactura();
					tCarritoFactura.set_tFactura(factura.toTransfer());
					tCarritoFactura.set_tLineasFactura(tLineasFacturaPersistente);
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return res;
	}

	public Integer modificarFactura(TFactura tFactura) {
		// begin-user-code
		return -1;
	}

	public TFacturaTOA buscarFactura(Integer idFactura) {
		TFacturaTOA ftoa = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {

			Factura f = em.find(Factura.class, idFactura, LockModeType.OPTIMISTIC);
			if (f != null) {
				ftoa = new TFacturaTOA();
				Remitente r = f.get_Remitente();
				em.lock(r, LockModeType.OPTIMISTIC);

				Set<TLineaFactura> listaTLineasFactura = new HashSet<TLineaFactura>();

				for (LineaFactura linea : f.get_lineaFactura()) {
					em.lock(linea, LockModeType.OPTIMISTIC);
					listaTLineasFactura.add(linea.toTransfer());
				}

				ftoa.set_tFactura(f.toTransfer());
				ftoa.set_tLineasFactura(listaTLineasFactura);
				ftoa.set_tRemitente(r.entityToTransfer());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return ftoa;
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
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Integer res = -1;
		try {
			em.getTransaction().begin();
			Paquete paquete = em.find(Paquete.class, tCarritoFactura.get_idPaquete(), LockModeType.OPTIMISTIC);
			Set<TLineaFactura> set = tCarritoFactura.get_tLineasFactura();
			TLineaFactura lineaFactura = (buscar_en_carrito(set, tCarritoFactura.get_idPaquete()));

			if (lineaFactura == null) {
				lineaFactura = new TLineaFactura();
				lineaFactura.set_idPaquete(paquete.getId());
				lineaFactura.set_precioTotal(paquete.getPrecio());
				set.add(lineaFactura);
				tCarritoFactura.set_tLineasFactura(set);
				res = 1;
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public Integer eliminarPaquete(TCarritoFactura tCarritoFactura) {
		Integer res = -1;

		Set<TLineaFactura> lineasFactura = tCarritoFactura.get_tLineasFactura();
		TLineaFactura linea = buscar_en_carrito(lineasFactura, tCarritoFactura.get_idPaquete());

		if (linea != null) {
			lineasFactura.remove(linea);
			tCarritoFactura.set_tLineasFactura(lineasFactura);
			res = 1;
		}

		return res;
	}

	public Set<TFactura> listarFacturasPorRemitente(Integer idRemitente) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Set<TFactura> listaFactura = new HashSet<>();
		try {
			em.getTransaction().begin();

			Remitente remitente = em.find(Remitente.class, idRemitente, LockModeType.OPTIMISTIC);

			if (remitente != null && remitente.getActivo() == 1) {
				TypedQuery<Factura> queryf = em
						.createNamedQuery("negocio.FacturaJPA.Factura.findByremitente", Factura.class)
						.setParameter("idRemitente", idRemitente);

				for (Factura f : queryf.getResultList()) {
					if (f.get_activo() == 1) {
						listaFactura.add(f.toTransfer());
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaFactura;
	}

	private TLineaFactura buscar_en_carrito(Set<TLineaFactura> lineasFactura, int id) {

		Iterator<TLineaFactura> it = lineasFactura.iterator();
		TLineaFactura res = null;
		while (it.hasNext()) {
			TLineaFactura lineaFactura = it.next();
			if (lineaFactura.get_idPaquete() == id)
				res = lineaFactura;
		}

		return res;
	}

}