/**
 * 
 */
package negocio.Almacen;

import java.util.Set;

import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TProducto;
import integracion.Almacen.DAOAlmacen;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.Producto.DAOProducto;

public class SAAlmacenImp implements SAAlmacen {

	public Integer altaAlmacen(TAlmacen almacen) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int id = -1;
		if (tr != null) {
			tr.start();

			if (almacen != null) {

				DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
				TAlmacen leido = daoAlmacen.read_by_name(almacen.getNombre());
				if (almacen.getCapacidadMaxima() > 0) {
					if (leido == null) {
						id = daoAlmacen.create(almacen);
						if (id != -1) {
							tr.commit();
						} else {
							tr.rollback();
						}
					} else if (leido.getActivo() == 0) {
						almacen.setId(leido.getId());
						daoAlmacen.update(almacen);
						id = almacen.getId();
						if (id != -1) {
							tr.commit();
						} else
							tr.rollback();

					} else
						tr.rollback();
				} else {
					tr.rollback();
				}
			}
		}
		return id;
	}

	public Integer bajaAlmacen(Integer id) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;
		if (tr != null) {
			tr.start();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			TAlmacen almacen = daoAlmacen.read(id);

			if (almacen != null && almacen.getActivo() == 1 && daoProducto.read_by_almacen(almacen.getId()).isEmpty()) {
				res = daoAlmacen.delete(id);
				if (res > 0)
					tr.commit();
				else
					tr.rollback();
			} else
				tr.rollback();

		}
		return res;
	}

	public Integer modificarAlmacen(TAlmacen almacen) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;
		if (tr != null) {
			tr.start();
			if (almacen != null) {
				DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
				TAlmacen existente = daoAlmacen.read(almacen.getId());
				if (existente != null) {

					if (existente.getActivo() == 1 && almacen.getCapacidadMaxima() >= existente.getOcupacion()
							&& almacen.getCapacidadMaxima() > 0 && (almacen.getNombre().equals(existente.getNombre())
									|| daoAlmacen.read_by_name(almacen.getNombre()) == null)) {
						res = daoAlmacen.update(almacen);
						if (res > 0)
							tr.commit();
						else
							tr.rollback();
					} else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
		}
		return res;
	}

	public TAlmacen leerAlmacen(Integer id) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
			TAlmacen leido = daoAlmacen.read(id);
			if (leido != null) {
				tr.commit();
				return leido;

			} else
				tr.rollback();

		}
		return null;
	}

	public Set<TAlmacen> leerTodosAlmacenes() {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TAlmacen> lista = null;
		if (tr != null) {
			tr.start();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
			lista = daoAlmacen.read_all();
			if (lista != null) {
				tr.commit();
			} else
				tr.rollback();
		}
		return lista;
	}

}