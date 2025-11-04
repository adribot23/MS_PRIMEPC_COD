package negocio.Producto;

import java.util.HashSet;
import java.util.Set;

import integracion.Almacen.DAOAlmacen;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.Producto.DAOProducto;
import integracion.Proveedor.DAOProveedor;
import integracion.Proveedor.DAOProveedorProducto;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Almacen.TAlmacen;
import negocio.Proveedor.TProveedor;
import negocio.Proveedor.TProveedorProducto;

public class SAProductoImp implements SAProducto {

	@Override
	public int altaProducto(TProducto producto) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();

			TProducto pr = daoProducto.read_by_modelo(producto.getModelo());

			TAlmacen almacen = daoAlmacen.read(producto.getIdAlmacen());

			if (almacen == null || almacen.getActivo() == 0) {

				transaction.rollback();
				return -1;
			}

			int capacidadMax = almacen.getCapacidadMaxima();
			int ocupacionActual = almacen.getOcupacion();
			int nuevasUnidades = producto.getUnidades();

			if (ocupacionActual + nuevasUnidades > capacidadMax) {

				transaction.rollback();
				return -1;
			}
			if (pr == null) {
				exito = daoProducto.create(producto);
				if (exito != -1) {
					almacen.setOcupacion(ocupacionActual + nuevasUnidades);
					int actualizado = daoAlmacen.update(almacen);

					if (actualizado != -1)
						transaction.commit();
					else {
						transaction.rollback();
						exito = -1;
					}
				} else
					transaction.rollback();

			} else if (pr.getActivo() == 0) {
				producto.setId(pr.getId());
				producto.setActivo(1);
				exito = daoProducto.update(producto);
				if (exito != -1) {
					almacen.setOcupacion(ocupacionActual + nuevasUnidades);
					int actualizado = daoAlmacen.update(almacen);
					if (actualizado != -1) {
						exito = pr.getId();
						transaction.commit();
					} else {
						transaction.rollback();
					}
				} else
					transaction.rollback();
			} else {
				transaction.rollback();
				exito = -1;
			}
		}

		return exito;
	}

	@Override
	public Set<TProducto> leerProductosPorAlmacen(int idAlmacen) {

		Set<TProducto> tprT = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			tprT = daoProducto.read_by_almacen(idAlmacen);

			if (tprT == null)
				transaction.rollback();
			else
				transaction.commit();

		}
		return tprT;

	}

	@Override
	public Set<TProducto> leerProductosPorProveedor(int idProveedor) {

		DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia().generaDAOProveedorProducto();
		DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();

		Set<TProveedorProducto> vinculaciones;
		Set<TProducto> productos = new HashSet<>();

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			vinculaciones = daoProveedorProducto.read_all_by_proveedor(idProveedor);

			if (vinculaciones != null) {
				for (TProveedorProducto v : vinculaciones) {
					productos.add(daoProducto.read(v.getIdProducto()));
				}
				transaction.commit();
			} else {
				transaction.rollback();
			}

		}

		return productos;

	}

	@Override
	public int bajaProducto(int id) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
			DAOProveedorProducto daoProveedorProducto = DAOAbstractFactory.getInstancia().generaDAOProveedorProducto();
			TProducto tpr = daoProducto.read(id);

			if (tpr == null) {
				transaction.rollback();
				return -1;
			}

			if (tpr.getActivo() == 1 && daoProveedorProducto.read_all_by_producto(tpr.getId()).isEmpty()) {

				TAlmacen almacen = daoAlmacen.read(tpr.getIdAlmacen());
				if (almacen != null && almacen.getActivo() == 1) {

					int nuevaOcupacion = almacen.getOcupacion() - tpr.getUnidades();
					almacen.setOcupacion(Math.max(0, nuevaOcupacion)); // evitar negativos
					int actualizado = daoAlmacen.update(almacen);

					if (actualizado != -1) {
						
						exito = daoProducto.delete(id);
						if (exito > 0)
							transaction.commit();
						else
							transaction.rollback();
					} else
						transaction.rollback();
				} else
					transaction.rollback();
			} else
				transaction.rollback();
		}

		return exito;
	}

	@Override
	public int modificarProducto(TProducto producto) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();

			TProducto productoAntiguo = daoProducto.read(producto.getId());
			if (productoAntiguo == null) {
				transaction.rollback();
				return -1;
			}

			TProducto tpByModelo = daoProducto.read_by_modelo(producto.getModelo());
			if (tpByModelo != null && tpByModelo.getId() != producto.getId()) {

				transaction.rollback();
				return -1;
			}

			if (productoAntiguo.getIdAlmacen() != producto.getIdAlmacen()) {
				TAlmacen almacenAntiguo = daoAlmacen.read(productoAntiguo.getIdAlmacen());
				TAlmacen almacenNuevo = daoAlmacen.read(producto.getIdAlmacen());

				if (almacenNuevo == null || almacenNuevo.getActivo() == 0 || almacenAntiguo == null) {
					transaction.rollback();
					return -1;
				}

				int capacidadDisponible = almacenNuevo.getCapacidadMaxima() - almacenNuevo.getOcupacion();
				if (capacidadDisponible < producto.getUnidades()) {

					transaction.rollback();
					return -1;
				}

				almacenAntiguo.setOcupacion(Math.max(0, almacenAntiguo.getOcupacion() - productoAntiguo.getUnidades()));
				daoAlmacen.update(almacenAntiguo);

				almacenNuevo.setOcupacion(almacenNuevo.getOcupacion() + producto.getUnidades());
				daoAlmacen.update(almacenNuevo);
			}

			exito = daoProducto.update(producto);
			if (exito != -1)
				transaction.commit();
			else
				transaction.rollback();
		}

		return exito;
	}

	@Override
	public TProducto leerProducto(int id) {

	    TProducto tpr = null;

	    TManager tManager = TManager.getInstance();
	    tManager.createTransaction();
	    Transaction transaction = tManager.getTransaction();

	    if (transaction != null) {
	        transaction.start();

	        DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
	        TProducto productoLeido = daoProducto.read(id);

	        if (productoLeido != null && productoLeido.getActivo() == 1) {
	            tpr = productoLeido;
	            transaction.commit();
	        } else {
	            transaction.rollback();
	        }
	    }

	    return tpr;
	}


	@Override
	public Set<TProducto> leerTodosProductos() {

		Set<TProducto> tpr = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			tpr = daoProducto.read_all();

			if (tpr == null)
				transaction.rollback();
			else
				transaction.commit();
		}
		return tpr;

	}

}