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
			Remitente remitente = em.find(Remitente.class, tFactura.get_idRemitente(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT); // NO SE SI HACE FALTA FORCE INCREMENT
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
			Remitente remitente = em.find(Remitente.class, tCarritoFactura.get_tFactura().get_idRemitente(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT); // NO SE SI HACE FALTA FORCE INCREMENT
			if (remitente != null && remitente.getActivo() != 0) {
				double total = 0;
				Factura factura = new Factura();
				factura.set_Remitente(remitente);
				factura.set_activo(1);
				em.persist(factura);
				Set<LineaFactura> lineasFacturaPersistentes = new HashSet<LineaFactura>();
				Set<TLineaFactura> tLineasFacturaPersistente = new HashSet<TLineaFactura>();
				int cuenta = 0;
				for (TLineaFactura tLineaFactura : tCarritoFactura.get_tLineasFactura()) {
					Paquete paquete = em.find(Paquete.class, tLineaFactura.get_idPaquete());

					TypedQuery<LineaFactura> queryf = em
							.createNamedQuery("negocio.FacturaJPA.LineaFactura.findBypaquete", LineaFactura.class)
							.setParameter("paquete", paquete);

					if (paquete != null && paquete.getActivo() == 1) {

						for (LineaFactura l : queryf.getResultList()) {
							if (l.get_devuelto() != 1)
								cuenta++;
						}
						if (cuenta == 0) {
							LineaFactura lineaFactura = new LineaFactura(factura, paquete);
							lineaFactura.set_devuelto(0);
							lineaFactura.set_precioTotal(paquete.getPrecio());
							tLineaFactura.set_precioTotal(lineaFactura.get_precioTotal());
							tLineasFacturaPersistente.add(tLineaFactura);
							em.persist(lineaFactura);
							total += lineaFactura.get_precioTotal();
							lineasFacturaPersistentes.add(lineaFactura);
						} else {
							break;
						}
					}
				}
				if (total != 0 && cuenta == 0) {
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
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Factura f = em.find(Factura.class, tFactura.get_idFactura());

			if (f != null && f.get_activo() == 1 && tFactura.get_precioTotal() >= 0) {
				Remitente remitenteActual = em.find(Remitente.class, f.get_Remitente().getId());
				Remitente nuevoRemitente = em.find(Remitente.class, tFactura.get_idRemitente());

				if (nuevoRemitente != null && nuevoRemitente.getActivo() == 1) {
					if (nuevoRemitente.getId() != remitenteActual.getId()) {
						em.lock(nuevoRemitente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						em.lock(remitenteActual, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						f.set_Remitente(nuevoRemitente);
					}
					f.set_precioTotal(tFactura.get_precioTotal());
					em.persist(f);
					em.getTransaction().commit();
					res = f.get_idFactura();
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
			TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f", Factura.class); 
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
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaFacturas;
	}

	public Integer devolucion(TLineaFactura tLineaFactura) {
		Integer res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Factura f = em.find(Factura.class, tLineaFactura.get_idFactura());
			if (f != null && f.get_activo() == 1) {
				LineaFacturaID lineaFacturaID = new LineaFacturaID(tLineaFactura.get_idFactura(),
						tLineaFactura.get_idPaquete());
				LineaFactura lineaFactura = em.find(LineaFactura.class, lineaFacturaID);
				if (lineaFactura != null) {

					if (lineaFactura.get_devuelto() == 0) {

						Paquete paquete = em.find(Paquete.class, tLineaFactura.get_idPaquete(),
								LockModeType.OPTIMISTIC);
						if (paquete != null) {
							lineaFactura.set_devuelto(1);
							f.set_precioTotal(f.get_precioTotal() - lineaFactura.get_precioTotal());
							if (f.get_precioTotal() < 0)
								f.set_precioTotal(0);
							em.getTransaction().commit();
							res = 1;
						} else
							em.getTransaction().rollback();
					} else
						em.getTransaction().rollback();
				} else
					em.getTransaction().rollback();
			} else
				em.getTransaction().rollback();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
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
				em.getTransaction().commit();
			} else
				em.getTransaction().rollback();

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