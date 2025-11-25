package negocio.TrabajadorJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import integracion.EMFSingleton.EMFSingleton;
import negocio.TransporteJPA.TTransporte;
import negocio.TransporteJPA.Transporte;
import negocio.TransporteJPA.VinculacionTransporteTrabajador;

public class SATrabajadorImp implements SATrabajador{

	@Override
	public synchronized int AltaTrabajador(TTrabajador trabajador) {
		int res = -1;		
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			List<Trabajador> listaTrabajador = em.createNamedQuery("Negocio.TrabajadorJPA.Trabajador.findByDNI", Trabajador.class).setParameter("DNI", trabajador.getDNI()).getResultList();
			
			Trabajador r = listaTrabajador.isEmpty() ? null : listaTrabajador.get(0);
			
			if(r == null) {
				// Lo persisto como nuevo
				Trabajador n = new Trabajador(trabajador);
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
	public int BajaTrabajador(int id_trabajador) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Trabajador t = em.find(Trabajador.class, id_trabajador);
			
			
			em.getTransaction().rollback();		
		}catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int ModificarTrabajador(TTrabajador trabajador) {
		
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			Trabajador tExistente = em.find(Trabajador.class, trabajador.getId());
			
			if(tExistente != null && tExistente.getActivo() == 1) {
				
				List<Trabajador> listaTrabajador = em.createNamedQuery("Negocio.TrabajadorJPA.Trabajador.findByDNI", Trabajador.class).setParameter("DNI", trabajador.getDNI()).getResultList();
				
				if(listaTrabajador.isEmpty() || (listaTrabajador.size() == 1 && listaTrabajador.get(0).getId() == trabajador.getId())) {
					
					tExistente.setNombre(trabajador.getNombre());
					tExistente.setDNI(trabajador.getDNI());
					tExistente.setActivo(trabajador.isActivo());
					
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
	public TTrabajador leerTrabajador(int id_trabajador) {

		TTrabajador trabajador = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			Trabajador trabajadorByID = em.find(Trabajador.class, LockModeType.OPTIMISTIC);
			
			if(trabajadorByID != null) {
				trabajador = trabajadorByID.entityToTransfer();
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
		
		return trabajador;
	}


	@Override
	public Set<TTrabajador> leerTodosTrabajadores() {
	EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		Set<TTrabajador> listaTrabajador = null;
		
		try {
			
			em.getTransaction().begin();
			
			TypedQuery<Trabajador> query = em.createNamedQuery("negocio.TrabajadorJPA.Trabajador.findAll", Trabajador.class);
			
			if(!query.getResultList().isEmpty()) {
				
				listaTrabajador = new LinkedHashSet<TTrabajador>();
				
				for(Trabajador t: query.getResultList()) {
					
					em.lock(t, LockModeType.OPTIMISTIC);
					listaTrabajador.add(t.entityToTransfer());
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
		
		return listaTrabajador;
	}

}
