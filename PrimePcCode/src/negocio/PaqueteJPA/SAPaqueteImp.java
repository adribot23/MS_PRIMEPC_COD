package negocio.PaqueteJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import negocio.FacturaJPA.Factura;
import negocio.FacturaJPA.LineaFactura;
import negocio.RutaJPA.Ruta;
import integracion.EMFSingleton.EMFSingleton;

public class SAPaqueteImp implements SAPaquete{

	@Override
	public synchronized int altaPaquete(TPaquete tPaquete) {
	    int res = -1;
	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

	    try {
	        em.getTransaction().begin();

	        Ruta ruta = em.find(Ruta.class,tPaquete.getIdRuta(),LockModeType.OPTIMISTIC_FORCE_INCREMENT);
	        if (ruta == null || ruta.getActivo() == 0) {
	            em.getTransaction().rollback();
	            throw new RuntimeException("La ruta no existe o está inactiva.");
	        }

	        List<Paquete> lista = em
	                .createNamedQuery("Paquete.findByNumSerie", Paquete.class)
	                .setParameter("numSerie", tPaquete.getNumSerie())
	                .getResultList();

	        Paquete existente = lista.isEmpty() ? null : lista.get(0);
	     
	        if (existente == null) {
	            Paquete nuevo = null;

	            if (tPaquete instanceof TPaqueteExpress) {
	                nuevo = new PaqueteExpress((TPaqueteExpress) tPaquete);
	            } else if (tPaquete instanceof TPaqueteNormal) {
	                nuevo = new PaqueteNormal((TPaqueteNormal) tPaquete);
	            }

	            if (nuevo == null) {
	                em.getTransaction().rollback();
	                throw new RuntimeException("Error al crear el paquete: tipo desconocido.");
	            }
	            nuevo.setRuta(ruta);
	            em.persist(nuevo);
	            em.getTransaction().commit();
	            res = nuevo.getId();

	        }
	        else if (existente.getActivo() == 0) {
	            boolean mismoTipo = (tPaquete instanceof TPaqueteExpress && existente instanceof PaqueteExpress) ||
	                                (tPaquete instanceof TPaqueteNormal && existente instanceof PaqueteNormal);

	            if (!mismoTipo) {
	                em.getTransaction().rollback();
	                throw new RuntimeException("Existe un paquete inactivo con el mismo número de serie pero de distinto tipo.");
	            }

	            existente.setActivo(1);
	            existente.setPeso(tPaquete.getPeso());
	            existente.setPrecio(tPaquete.getPrecio());
	            existente.setEstado(tPaquete.getEstado());
	            existente.setRuta(ruta);

	 
	            em.getTransaction().commit();
	            res = existente.getId();

	        } else {
	            em.getTransaction().rollback();
	            throw new RuntimeException("Ya existe un paquete activo con el mismo número de serie.");
	        }

	    } catch (RuntimeException e) {
	        if (em.getTransaction().isActive()) em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }

	    return res;
	}




	@Override
	public int bajaPaquete(int id_paquete) {
	    int res = -1;

	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
	    EntityTransaction tr = em.getTransaction();

	    try {
	        tr.begin();
	        Paquete paquete = em.find(Paquete.class, id_paquete);
	        if (paquete == null) {
	            tr.rollback();
	            throw new RuntimeException("El paquete con ID " + id_paquete + " no existe.");
	        }
	        Ruta ruta = paquete.getRuta();
	        if (ruta != null) {
	            em.lock(ruta, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
	        }
	        if (paquete.getActivo() == 0) {
	            tr.rollback();
	            throw new RuntimeException("El paquete con ID " + id_paquete + " ya está desactivado.");
	        }
	        paquete.setActivo(0);
	        paquete.setRuta(null);

	        tr.commit();
	        res = paquete.getId();

	    } catch (RuntimeException e) {
	        if (tr.isActive()) tr.rollback();
	        throw e;
	    } finally {
	        em.close();
	    }

	    return res;
	}


	@Override
	public int modificarPaquete(TPaquete t) {
	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
	    int res = -1;

	    try {
	        em.getTransaction().begin();

	        Paquete pExistente = em.find(Paquete.class, t.getId()); // @Version controla concurrencia
	        if (pExistente == null || pExistente.getActivo() == 0) {
	            em.getTransaction().rollback();
	            throw new RuntimeException("El paquete no existe o está inactivo.");
	        }

	        boolean tipoCoincide = (pExistente instanceof PaqueteExpress && t instanceof TPaqueteExpress) ||
	                               (pExistente instanceof PaqueteNormal && t instanceof TPaqueteNormal);
	        if (!tipoCoincide) {
	            em.getTransaction().rollback();
	            throw new RuntimeException("El tipo de paquete a modificar no coincide con el existente.");
	        }

	        Ruta ruta = em.find(Ruta.class, t.getIdRuta());
	        if (ruta == null || ruta.getActivo() == 0) {
	            em.getTransaction().rollback();
	            throw new RuntimeException("La ruta indicada no existe o está inactiva.");
	        }

	        List<Paquete> paquetesNumSerie = em
	                .createNamedQuery("Paquete.findByNumSerie", Paquete.class)
	                .setParameter("numSerie", t.getNumSerie())
	                .getResultList();
	        
	        for (Paquete p : paquetesNumSerie) {
	            if (!p.getId().equals(t.getId()) && p.getActivo() == 1) {
	                em.getTransaction().rollback();
	                throw new RuntimeException("Ya existe otro paquete activo con el mismo número de serie.");
	            }
	        }
	        pExistente.setNumSerie(t.getNumSerie());
	        pExistente.setPeso(t.getPeso());
	        pExistente.setEstado(t.getEstado());
	        pExistente.setPrecio(t.getPrecio());
	        pExistente.setRuta(ruta);

	        if (pExistente instanceof PaqueteExpress && t instanceof TPaqueteExpress) {
	            ((PaqueteExpress) pExistente).setPrioridad(((TPaqueteExpress) t).getPrioridad());
	        } else if (pExistente instanceof PaqueteNormal && t instanceof TPaqueteNormal) {
	            ((PaqueteNormal) pExistente).setDescuento(((TPaqueteNormal) t).getDescuento());
	        }
	        em.getTransaction().commit();
	        res = pExistente.getId();

	    } catch (RuntimeException e) {
	        if (em.getTransaction().isActive()) em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }

	    return res;
	}





    @Override
    public TPaquete buscarPaquete(int id_paquete) {
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            Paquete paquete = em.find(Paquete.class, id_paquete);

            if (paquete == null) {
                em.getTransaction().rollback();
                throw new RuntimeException("No existe ningún paquete con ID " + id_paquete + ".");
            }

            TPaquete tPaquete = paquete.entityToTransfer();
            em.getTransaction().commit();
            return tPaquete;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    @Override
    public Set<TPaquete> mostrarPaquetes() {
        Set<TPaquete> listaPaquetes = null;
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            TypedQuery<Paquete> query = em.createNamedQuery(
                    "negocio.PaqueteJPA.Paquete.findAll",
                    Paquete.class
            );
            //query.setLockMode(LockModeType.OPTIMISTIC);

            List<Paquete> resultados = query.getResultList();

            if (!resultados.isEmpty()) {
                listaPaquetes = new LinkedHashSet<>();

                for (Paquete p : resultados) {
                    listaPaquetes.add(p.entityToTransfer());
                }

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

        return listaPaquetes;
    }

    
    @Override
    public Set<TPaquete> mostrarPaquetesPorFactura(int id_factura) {
        Set<TPaquete> setPaquetes = new LinkedHashSet<>();
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
        	em.getTransaction().begin();
            TypedQuery<Paquete> query = em.createNamedQuery(
                "negocio.PaqueteJPA.Paquete.findByFactura", Paquete.class);
            query.setParameter("idFactura", id_factura);
            List<Paquete> paquetes = query.getResultList();

            for (Paquete p : paquetes) {
            	em.lock(p, LockModeType.OPTIMISTIC);
                setPaquetes.add(p.entityToTransfer());
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return setPaquetes;
    }
	
    
    @Override
    public Set<TPaquete> mostrarPaquetesPorRuta(int id_ruta) {
        Set<TPaquete> setPaquetes = new LinkedHashSet<>();
        EntityManager em = EMFSingleton.getInstancia()
                                       .getEntityManagerFactory()
                                       .createEntityManager();

        try {
        	em.getTransaction().begin();
            TypedQuery<Paquete> query = em.createNamedQuery(
                    "negocio.PaqueteJPA.Paquete.findByRuta",
                    Paquete.class
            );

            query.setParameter("idRuta", id_ruta);

            List<Paquete> paquetes = query.getResultList();

            for (Paquete p : paquetes) {
                em.lock(p, LockModeType.OPTIMISTIC);
                setPaquetes.add(p.entityToTransfer());
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return setPaquetes;
    }

}