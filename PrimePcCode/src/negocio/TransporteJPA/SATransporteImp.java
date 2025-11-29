package negocio.TransporteJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TrabajadorJPA.TTrabajador;

public class SATransporteImp implements SATransporte {

	@Override
	public synchronized int altaTransporte(TTransporte t) {
		int res = -1;		
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			List<Transporte> listaTransporte = em.createNamedQuery("Negocio.TransporteJPA.Transporte.findByMatricula", Transporte.class).setParameter("matricula", t.getMatricula()).getResultList();
			
			Transporte r = listaTransporte.isEmpty() ? null : listaTransporte.get(0);
			
			if(r == null) {
				// Lo persisto como nuevo
				Transporte n = new Transporte(t);
				em.persist(n);
				em.getTransaction().commit();
				res = n.getId();
			}else if(r.getActivo() == 0) {
				// Lo pongo activo
				r.setActivo(1);
				em.getTransaction().commit();
				res = r.getId();
			}else {
				em.getTransaction().rollback();
			}			
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int bajaTransporte(int id) {
		
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Transporte t = em.find(Transporte.class, id, LockModeType.OPTIMISTIC);
			
			if(t == null && t.getActivo() == 1) {
				Set<VinculacionTransporteTrabajador> vinculaciones = t.getVinculaciones();

				
				if(vinculaciones.isEmpty()) {
					t.setActivo(1);
					em.getTransaction().commit();
					res = t.getId();
				}else {
					em.getTransaction().rollback();
				}
				
			}else {
				em.getTransaction().rollback();
			}		
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int modificarTransporte(TTransporte t) {
		
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			Transporte tExistente = em.find(Transporte.class, t.getId(), LockModeType.OPTIMISTIC);
			
			if(tExistente != null && tExistente.getActivo() == 1) {
				
				List<Transporte> tMatricula = em.createNamedQuery("Negocio.TransporteJPA.Transporte.finfByMatricula", Transporte.class).setParameter("matricula", t.getMatricula()).getResultList();
				
				if(tMatricula.isEmpty() || (tMatricula.size() == 1 && tMatricula.get(0).getId() == t.getId())) {
					
					tExistente.setNombre(t.getNombre());
					tExistente.setMatricula(t.getMatricula());
					tExistente.setCapacidad(t.getCapacidad());
					
					em.getTransaction().commit();
					res = tExistente.getId();
				}else {
					em.getTransaction().rollback();
				}
				
			}else {
				em.getTransaction().rollback();
			}			
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public TTransporte leerTransporte(int id) {
		
		TTransporte transporte = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			Transporte transporteById = em.find(Transporte.class, id, LockModeType.OPTIMISTIC);
			
			if(transporteById != null) {
				transporte = transporteById.toTransfer();
				em.getTransaction().commit();
			}else {
				em.getTransaction().rollback();
			}			
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
		return transporte;
	}

	@Override
	public Set<TTransporte> leerTodosTransportes() {
		
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		Set<TTransporte> listaTransportes = null;
		
		try {
			
			em.getTransaction().begin();
			
			TypedQuery<Transporte> query = em.createNamedQuery("negocio.TransporteJPA.Transporte.finAll", Transporte.class);
			query.setLockMode(LockModeType.OPTIMISTIC);
			
			if(!query.getResultList().isEmpty()) {
				
				listaTransportes = new LinkedHashSet<TTransporte>();
				
				for(Transporte t: query.getResultList()) {
					
					em.lock(t, LockModeType.OPTIMISTIC);
					listaTransportes.add(t.toTransfer());
				}
				em.getTransaction().commit();
			}else {
				em.getTransaction().rollback();
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
		return listaTransportes;
	}

	@Override
	public int vincularTransporteTrabajador(TTransporteTrabajador t) {
	
		return 0;
	}

	@Override
	public int desvincularTransporteTrabajador(TTransporteTrabajador t) {
	
		return 0;
	}

	
	@Override
	public Set<TTransporte> leerTransportesPorTrabajador(TTrabajador t) {
		// TODO Auto-generated method stub
		return null;
	}

}
