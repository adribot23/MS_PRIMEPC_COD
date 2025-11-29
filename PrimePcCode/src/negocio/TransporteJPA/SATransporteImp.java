package negocio.TransporteJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TrabajadorJPA.TTrabajador;
import negocio.TrabajadorJPA.Trabajador;

public class SATransporteImp implements SATransporte {

	@Override
	public synchronized int altaTransporte(TTransporte t) {
		int res = -1;		
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			List<Transporte> listaTransporte = em.createNamedQuery("negocio.TransporteJPA.Transporte.findByMatricula", Transporte.class).setParameter("matricula", t.getMatricula()).getResultList();
			
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
				/*Set<VinculacionTransporteTrabajador> vinculaciones = t.getVinculaciones();

				
				if(vinculaciones.isEmpty()) {
					t.setActivo(1);
					em.getTransaction().commit();
					res = t.getId();
				}else {
					em.getTransaction().rollback();
				}*/
				
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
	public int vincularTransporteTrabajador(int id_transporte, int id_trabajador) {
		
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Transporte transporte = em.find(Transporte.class, id_transporte);
			Trabajador trabajador = em.find(Trabajador.class, id_trabajador);
			
			if(transporte != null && trabajador != null && transporte.getActivo() == 1 && trabajador.getActivo() == 1) {
				
				transporte.getTrabajadores().add(trabajador);
				// trabajador.getTransportes.add(transporte); -> (preguntar si hay que ponerlo)
				
				em.getTransaction().commit();
				res = 1;
			}else {
				em.getTransaction().rollback();
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int desvincularTransporteTrabajador(int id_transporte, int id_trabajador) {
		
		int res = -1;		
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Transporte transporte = em.find(Transporte.class, id_transporte);
			Trabajador trabajador = em.find(Trabajador.class, id_trabajador);
			
			if(transporte != null && trabajador != null && transporte.getActivo() == 1 && trabajador.getActivo() == 1) {
				
				transporte.getTrabajadores().remove(trabajador);
				em.getTransaction().commit();
				res = 1;
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
	public Set<TTransporte> leerTransportesPorTrabajador(int id_trabajador) {
		
		Set<TTransporte> transportes = new LinkedHashSet<>();	
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Trabajador trabajador = em.find(Trabajador.class, id_trabajador, LockModeType.OPTIMISTIC);
			
			if(trabajador != null && trabajador.getActivo() == 1) {
				
				
				for(Transporte transporte: Trabajador.getTransportes()) {
					em.lock(transporte, LockModeType.OPTIMISTIC);
					
					if(transporte.getActivo() == 1)
						transportes.add(transporte.toTransfer());
				}
				
				em.getTransaction().commit();
			}else {
				em.getTransaction().rollback();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
			
		return transportes;
	}

}
