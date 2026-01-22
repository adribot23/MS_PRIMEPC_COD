package negocio.RemitenteJPA;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import negocio.FacturaJPA.Factura;
import negocio.FacturaJPA.LineaFactura;
import negocio.PaqueteJPA.Paquete;
import negocio.TransporteJPA.Transporte;
import integracion.EMFSingleton.EMFSingleton;

public class SARemitenteImp implements SARemitente {
	
	@Override
	public synchronized int altaRemitente(TRemitente tRemitente) {
	    int res = -1;
	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

	    try {
	        em.getTransaction().begin();

	        List<Remitente> lista = em
	                .createNamedQuery("Negocio.RemitenteJPA.Remitente.findBynombre", Remitente.class)
	                .setParameter("nombre", tRemitente.getNombre())
	                .getResultList();

	        Remitente existente = lista.isEmpty() ? null : lista.get(0);

	        if (existente == null) {

	            Remitente nuevo = null;

	            if (tRemitente instanceof TEmpresa) {
	                nuevo = new Empresa((TEmpresa) tRemitente);

	            } else if (tRemitente instanceof TParticular) {
	                nuevo = new Particular((TParticular) tRemitente);
	            }

	            if (nuevo == null) {
	                em.getTransaction().rollback();
	                return -1;
	            }

	            em.persist(nuevo);
	            em.getTransaction().commit();
	            res = nuevo.getId();
	        }


	        else if (existente.getActivo() == 0) {

	            if (existente instanceof Empresa && tRemitente instanceof TEmpresa) {

	                Empresa e = (Empresa) existente;
	                TEmpresa te = (TEmpresa) tRemitente;

	                e.setActivo(1);
	                e.setDireccion(te.getDireccion());
	                e.setTelefono(te.getTelefono());
	                e.setNombre(te.getNombre());
	                e.setNumRegistroFiscal(te.getNumRegistroFiscal());

	            } else if (existente instanceof Particular && tRemitente instanceof TParticular) {

	                Particular p = (Particular) existente;
	                TParticular tp = (TParticular) tRemitente;

	                p.setActivo(1);
	                p.setDireccion(tp.getDireccion());
	                p.setTelefono(tp.getTelefono());
	                p.setNombre(tp.getNombre());
	                p.setFechaNacimiento(tp.getFechaNacimiento());

	            } else {
	                em.getTransaction().rollback();
	                return -1;
	            }

	            em.lock(existente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
	            em.getTransaction().commit();
	            res = existente.getId();
	        }
	        else {
	            em.getTransaction().rollback();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive())
	            em.getTransaction().rollback();
	    } finally {
	        em.close();
	    }

	    return res;
	}

	@Override
	public int bajaRemitente(int id_remitente) {
		int exito = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();

			Remitente remitente = em.find(Remitente.class, id_remitente);

			Set<Factura> facturas = remitente.getFactura();
			int facturasActivas = 0;
			for(Factura f: facturas)
			{
				if(f.get_activo() == 1)
				{
					facturasActivas = 1;
					break;
				};
			}
			
			if (remitente != null && remitente.getActivo() == 1 && facturasActivas == 0) {

				remitente.setActivo(0);

				tr.commit();
				exito = remitente.getId();

			} else {
				tr.rollback();
			}
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return exito;
	}

	@Override
	public int modificarRemitente(TRemitente tRem) {
		int res = -1;
        EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            Remitente rExistente = em.find(Remitente.class, tRem.getId()); // No lock necesario al modificar con @Version
            TRemitente transferExistente = rExistente.entityToTransfer();
            
            if(!tRem.getClass().equals(transferExistente.getClass())) {
            	em.getTransaction().rollback();
				em.close();
				return res;
            }
            
            if (rExistente != null && rExistente.getActivo() == 1) {

                List<Remitente> tNombre = em
                        .createNamedQuery("Negocio.RemitenteJPA.Remitente.findBynombre", Remitente.class)
                        .setParameter("nombre", tRem.getNombre()).getResultList();

                if (tNombre.isEmpty() || (tNombre.size() == 1 && tNombre.get(0).getId() == tRem.getId())) {
                    rExistente.setNombre(tRem.getNombre());
                    rExistente.setDireccion(tRem.getDireccion());
                    rExistente.setTelefono(tRem.getTelefono());
                    
                    if(rExistente instanceof Empresa) {
                    	Empresa emp = (Empresa)rExistente;
                    	TEmpresa tEmp = (TEmpresa) tRem;
                    	emp.setNumRegistroFiscal(tEmp.getNumRegistroFiscal());
                    } else if (rExistente instanceof Particular) {
                    	Particular part = (Particular) rExistente;
                    	TParticular tPart = (TParticular) tRem;
                    	part.setFechaNacimiento(tPart.getFechaNacimiento());
                    }

                    em.getTransaction().commit();
                    res = rExistente.getId();
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

	@Override
	public TRemitente buscarRemitente(int id_remitente) {
		TRemitente tremitente = null;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Remitente remitente = em.find(Remitente.class, id_remitente, LockModeType.OPTIMISTIC);

			if (remitente != null) {
				tremitente = remitente.entityToTransfer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();

		return tremitente;
	}

	@Override
	public Set<TRemitente> listarTodosRemitentes() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();
		Set<TRemitente> remitentes = new LinkedHashSet<>();

		try {
			tr.begin();
			TypedQuery<Remitente> query = em.createQuery("SELECT r FROM Remitente r", Remitente.class);
			Set<Remitente> listaRemitentes = new LinkedHashSet<>(query.getResultList());

			for (Remitente r : listaRemitentes) {
				em.lock(r, LockModeType.OPTIMISTIC);

				TRemitente t = r.entityToTransfer();
				remitentes.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();

		return remitentes;
	}
	
	@Override
	public double calcularPrecioPaquetesRemitente(int id_remitente) {
		
	    EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    double total = 0;

	    try {
	        tx.begin(); 
	        Remitente remitente = em.find(Remitente.class, id_remitente, LockModeType.OPTIMISTIC);

	        if (remitente != null) {
	        	
	            for (Factura factura : remitente.getFactura()) {
	            	em.lock(factura, LockModeType.OPTIMISTIC);
	                for (LineaFactura lf : factura.get_lineaFactura()) {
	                	em.lock(lf, LockModeType.OPTIMISTIC);
	                    Paquete p = lf.get_Paquete();
	                    em.lock(p, LockModeType.OPTIMISTIC);
	                    if (p != null) 
	                        total += p.calculaPrecioFinal();
	                    
	                }
	            }
	        }

	        tx.commit();
	        
	    } catch (OptimisticLockException e) {
	        total = -1; 
	        if (tx.isActive()) 
	        	tx.rollback();
	        
	    } catch (Exception e) {
	    	
	        if (tx.isActive()) tx.rollback();
	        e.printStackTrace();
	        
	    } finally {
	    	
	        em.close();
	    }

	    return total;
	}


}

