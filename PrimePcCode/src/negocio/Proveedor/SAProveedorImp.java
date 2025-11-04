/**
 * 
 */
package negocio.Proveedor;

import java.util.HashSet;
import java.util.Set;

import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.FactoriaQuery.FactoriaQuery;
import integracion.FactoriaQuery.Query;
import integracion.Producto.DAOProducto;
import integracion.Proveedor.DAOProveedor;
import integracion.Proveedor.DAOProveedorProducto;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TProducto;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SAProveedorImp implements SAProveedor {

	@Override
	public int altaProveedor(TProveedor proveedor) {
		int id = -1;
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
		if (tr != null) {
			tr.start();
			if (proveedor != null) {
				TProveedor leido = daoProveedor.read_by_name(proveedor.getNombre());
				if (leido == null) {
					id = daoProveedor.create(proveedor);
					tr.commit();
				} else if (leido.getActivo() == 0) {
					proveedor.setId(leido.getId());
					daoProveedor.update(proveedor);
					id = proveedor.getId();
					tr.commit();
				} else {
					tr.rollback();
				}
			}
		}
		return id;
	}

	@Override
	public Set<TProveedor> leerProveedorPorProducto(int idProducto) {
	
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		Set<TProveedorProducto> vinculaciones;
		Set<TProveedor> proveedores = new HashSet<>();
		if (tr != null) {
			DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia().generaDAOProveedorProducto();
			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
			

			tr.start();
			vinculaciones = daoProveedorProducto.read_all_by_producto(idProducto);

			if (vinculaciones != null) {
				for (TProveedorProducto v : vinculaciones) {
					proveedores.add(daoProveedor.read(v.getIdProveedor()));
				}

				tr.commit();
			} else
				tr.rollback();
		}

		return proveedores;
	}

	@Override
	public int vincularProductoProveedor(TProveedorProducto vinculacion) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {
			tr.start();
			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();

			TProveedor proveedor = daoProveedor.read(vinculacion.getIdProveedor());
			TProducto producto = daoProducto.read(vinculacion.getIdProducto());

			if (proveedor != null && producto != null && proveedor.getActivo() == 1 && producto.getActivo() == 1) {

				DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia()
						.generaDAOProveedorProducto();

				TProveedorProducto vinculacion2 = new TProveedorProducto();

				vinculacion2 = daoProveedorProducto.read(vinculacion.getIdProveedor(), vinculacion.getIdProducto());
				if (vinculacion2 == null) {
					int res = daoProveedorProducto.create(vinculacion);
					if (res != -1) {
						tr.commit();
						exito = 1;
					} else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
		}

		return exito;
	}

	@Override
	public int desvincularProductoProveedor(TProveedorProducto vinculacion) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {
			tr.start();

			DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia().generaDAOProveedorProducto();
			TProveedorProducto vinculacion2 = daoProveedorProducto.read(vinculacion.getIdProveedor(),
					vinculacion.getIdProducto());

			if (vinculacion2 != null) {
				exito = daoProveedorProducto.delete(vinculacion.getIdProveedor(), vinculacion.getIdProducto());
				if (exito != -1)
					tr.commit();
				else
					tr.rollback();
			} else
				tr.rollback();

		}

		return exito;
	}

	@Override
	public int bajaProveedor(int idProveedor) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
			DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia().generaDAOProveedorProducto();
			TProveedor proveedor = daoProveedor.read(idProveedor);

			if (proveedor != null && proveedor.getActivo() == 1 && daoProveedorProducto.read_all_by_proveedor(idProveedor).isEmpty()) {
				if (proveedor.getActivo() == 1) {
					exito = daoProveedor.delete(idProveedor);
					if (exito != -1)
						tr.commit();
					else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
		}

		return exito;

	}

	@Override
	public int modificarProveedor(TProveedor tProveedor) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {
			tr.start();

			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();

			TProveedor existente = daoProveedor.read(tProveedor.getId());

			if (existente != null && existente.getActivo() == 1 && (tProveedor.getNombre().equals(existente.getNombre())
					|| daoProveedor.read_by_name(tProveedor.getNombre()) == null)) {
				exito = daoProveedor.update(tProveedor);
				if (exito != -1) {
					tr.commit();
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}
		return exito;

	}

	@Override
	public Set<TProveedor> leerTodosProveedores() {
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		Set<TProveedor> proveedores = null;

		if (tr != null) {
			tr.start();
			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
			proveedores = daoProveedor.read_all();
			tr.commit();
		}

		return proveedores;
	}

	@Override
	public TProveedor leerProveedor(int id) {
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		TProveedor proveedor = null;
		if (tr != null) {
			tr.start();
			DAOProveedor daoProveedor = DAOAbstractFactory.getInstancia().generaDAOProveedor();
			proveedor = daoProveedor.read(id);
			if (proveedor != null && proveedor.getActivo() == 1)
				tr.commit();
			else {
				tr.rollback();
			}

		}
		return proveedor;
	}

	@Override
	public TProveedor proveedorConMasUnidadesDeProductoVendidas(int idProducto) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		TProveedor proveedor = null;

		if (tr != null) {

			tr.start();
			Query q = FactoriaQuery.getInstance().getNewQuery("CalcularProveedorMasUnidades");
			proveedor = (TProveedor) q.execute(idProducto);

			if (proveedor != null)
				tr.commit();
			else
				tr.rollback();

		}

		return proveedor;
	}

}