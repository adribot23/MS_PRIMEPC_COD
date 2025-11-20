package negocio.TransporteJPA;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import integracion.EMFSingleton.EMFSingleton;

public class SATransporteImp implements SATransporte {

	@Override
	public synchronized int AltaTransporte(TTransporte t) {
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
	public int BajaTransporte(int id) {
		
		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Transporte t = em.find(Transporte.class, id);
			
			if(t == null && t.getActivo() == 1) {
				
				
				
			}else if(t.getActivo() == 0) {
				
			}else {
				
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
	public int ModificarTransporte(TTransporte t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TTransporte leerTransporte(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TTransporte> leerTodosTransportes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int vincularTransporteTrabajador(TTransporteTrabajador t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int desvincularTransporteTrabajador(TTransporteTrabajador t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<TTransporte> leerTransportesPorTrabajador(TTrabajador t) {
		// TODO Auto-generated method stub
		return null;
	}

}
