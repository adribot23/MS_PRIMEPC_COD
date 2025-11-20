package negocio.TransporteJPA;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import integracion.EMFSingleton.EMFSingleton;

public class SATransporteImp implements SATransporte {

	@Override
	public synchronized int AltaTransporte(TTransporte t) {
		int res = -1;		
		EntityManager m = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		
		try {
			m.getTransaction().begin();
			
			List<Transporte> listaTransporte = m.createNamedQuery("Negocio.TransporteJPA.Transporte.findById").setParameter("id", t.getId()).getResultList();
			
			Transporte r = listaTransporte.isEmpty() ? null : listaTransporte.get(0);
			
			if(r == null) {
				// Lo persisto como nuevo
				Transporte n = new Transporte(t);
				m.persist(n);
				m.getTransaction().commit();
				res = n.getId();
			}else if(r.getActivo() == 0) {
				r.setActivo(1);
				m.getTransaction().commit();
				res = r.getId();
			}else {
				m.getTransaction().rollback();
			}			
		}catch(Exception e) {
			
		}
		
		return 0;
	}

	@Override
	public int BajaTransporte(int id) {
		// TODO Auto-generated method stub
		return 0;
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

}
