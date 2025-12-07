package negocio.PaqueteJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;

public class SAPaqueteImp implements SAPaquete {

	@Override
	public synchronized int altaPaquete(TPaquete tPaquete) {
	    int res = -1;
	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

	    try {
	        em.getTransaction().begin();

	        List<Paquete> lista = em
	                .createNamedQuery("Paquete.findByNumSerie", Paquete.class)
	                .setParameter("numSerie", tPaquete.getNumSerie())
	                .getResultList();

	        Paquete existente = lista.isEmpty() ? null : lista.get(0);

	        //No existe y creamos crear nuevo paquete
	        if (existente == null) {

	            Paquete nuevo = null;

	            if (tPaquete instanceof TPaqueteExpress) {
	                nuevo = new PaqueteExpress((TPaqueteExpress) tPaquete);
	            } else if (tPaquete instanceof TPaqueteNormal) {
	                nuevo = new PaqueteNormal((TPaqueteNormal) tPaquete);
	            }

	            if (nuevo == null) {
	                em.getTransaction().rollback();
	                return -1;
	            }
	            em.persist(nuevo);

	            em.getTransaction().commit();
	            res = nuevo.getId();

	        }
	        // Existe pero esta inactivo
	        else if (existente.getActivo() == 0) {

	            existente.setActivo(1);
	            existente.setPeso(tPaquete.getPeso());
	            existente.setPrecio(tPaquete.getPrecio());
	            existente.setEstado(tPaquete.getEstado());

	            em.lock(existente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

	            em.getTransaction().commit();
	            res = existente.getId();
	        }
	        // Existe y esta activo
	        else {
	            em.getTransaction().rollback();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive()) em.getTransaction().rollback();
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

            if (paquete != null && paquete.getActivo() == 1) {
                paquete.setActivo(0);
                em.lock(paquete, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                tr.commit();
                res = paquete.getId();
            } else {
                tr.rollback();
            }
        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return res;
    }

    @Override
    public int modificarPaquete(TPaquete tPaquete) {
        int res = -1;

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();
            Paquete paquete = em.find(Paquete.class, tPaquete.getId());

            if (paquete != null && paquete.getActivo() == 1) {
                paquete.setPeso(tPaquete.getPeso());
                paquete.setPrecio(tPaquete.getPrecio());
                paquete.setEstado(tPaquete.getEstado());

                if (tPaquete instanceof TPaqueteExpress && paquete instanceof PaqueteExpress) {
                    ((PaqueteExpress) paquete).setPrioridad(((TPaqueteExpress) tPaquete).getPrioridad());
                } else if (tPaquete instanceof TPaqueteNormal && paquete instanceof PaqueteNormal) {
                    ((PaqueteNormal) paquete).setDescuento(((TPaqueteNormal) tPaquete).getDescuento());
                }

                em.lock(paquete, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                tr.commit();
                res = paquete.getId();
            } else {
                tr.rollback();
            }
        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return res;
    }

    @Override
    public TPaquete buscarPaquete(int id_paquete) {
        TPaquete tPaquete = null;

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            Paquete paquete = em.find(Paquete.class, id_paquete, LockModeType.OPTIMISTIC);
            if (paquete != null) {
                tPaquete = paquete.entityToTransfer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return tPaquete;
    }

    @Override
    public Set<TPaquete> mostrarPaquetes() {
        Set<TPaquete> setPaquetes = new LinkedHashSet<>();
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Paquete> query = em.createQuery("SELECT p FROM Paquete p", Paquete.class);
            List<Paquete> paquetes = query.getResultList();

            for (Paquete p : paquetes) {
                em.lock(p, LockModeType.OPTIMISTIC);
                setPaquetes.add(p.entityToTransfer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return setPaquetes;
    }
    
    @Override
    public Set<TPaquete> mostrarPaquetesPorFactura(int id_factura) {
        Set<TPaquete> setPaquetes = new LinkedHashSet<>();
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Paquete> query = em.createQuery(
                "SELECT p FROM Paquete p WHERE p.factura.id = :idFactura", Paquete.class);
            query.setParameter("idFactura", id_factura);
            List<Paquete> paquetes = query.getResultList();

            for (Paquete p : paquetes) {
                em.lock(p, LockModeType.OPTIMISTIC);
                setPaquetes.add(p.entityToTransfer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return setPaquetes;
    }

    @Override
    public Set<TPaquete> mostrarPaquetesPorRuta(int id_ruta) {
        Set<TPaquete> setPaquetes = new LinkedHashSet<>();
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Paquete> query = em.createQuery(
                "SELECT p FROM Paquete p WHERE p.ruta.id = :idRuta", Paquete.class);
            query.setParameter("idRuta", id_ruta);
            List<Paquete> paquetes = query.getResultList();

            for (Paquete p : paquetes) {
                em.lock(p, LockModeType.OPTIMISTIC);
                setPaquetes.add(p.entityToTransfer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return setPaquetes;
    }
}
