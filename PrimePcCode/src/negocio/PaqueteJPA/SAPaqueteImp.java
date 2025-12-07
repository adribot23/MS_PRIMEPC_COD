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
        int id = -1;

        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();

            Paquete paqueteExistente = em.find(Paquete.class, tPaquete.getId());

            Paquete nuevoPaquete = null;

            if (paqueteExistente == null) {
                if (tPaquete instanceof TPaqueteExpress) {
                    nuevoPaquete = new PaqueteExpress((TPaqueteExpress) tPaquete);
                } else if (tPaquete instanceof TPaqueteNormal) {
                    nuevoPaquete = new PaqueteNormal((TPaqueteNormal) tPaquete);
                }

                if (nuevoPaquete != null) {
                    em.persist(nuevoPaquete);
                    tr.commit();
                    id = nuevoPaquete.getId();
                } else {
                    tr.rollback();
                }
            } else if (paqueteExistente.getActivo() == 0) {
                // Reactivar paquete
                paqueteExistente.setPeso(tPaquete.getPeso());
                paqueteExistente.setPrecio(tPaquete.getPrecio());
                paqueteExistente.setEstado(tPaquete.getEstado());
                paqueteExistente.setActivo(1);
                em.lock(paqueteExistente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                tr.commit();
                id = paqueteExistente.getId();
            } else {
                tr.rollback();
            }

        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return id;
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
