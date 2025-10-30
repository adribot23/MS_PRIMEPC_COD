package negocio.Producto;

import java.util.List;
import java.util.Set;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Negocio.Producto.TProducto;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.Producto.DAOProducto;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

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
			TProducto pr = daoProducto.read_by_modelo(producto.getModelo());

			if (pr == null) {
				exito = daoProducto.create(producto);
				if (exito != -1) {
					transaction.commit();
				} else
					transaction.rollback();

			} else if (pr.getActivo() == 0) {
				producto.setId(pr.getId());
				producto.setActivo(1);
				exito = daoProducto.update(producto);
				if (exito != -1) {
					exito = pr.getId();
					transaction.commit();
				} else
					transaction.rollback();
			} else
				transaction.rollback();

		}
		return exito;
	}

	@Override
	public Set<TProducto> leerProductosPorAlmacen(int idAlmacen) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TProducto> leerProductosPorProveedor(int idProveedor) {
		// TODO Apéndice de método generado automáticamente
		return null;
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
	        TProducto tpr = daoProducto.read(id);

	        if (tpr == null) {
	            transaction.rollback();
	        } else {
	            if (tpr.getActivo() == 1) {
	                tpr.setActivo(0);
	                exito = daoProducto.update(tpr);
	                if (exito > 0)
	                    transaction.commit();
	                else {
	                    transaction.rollback();
	                }
	            } else {
	                transaction.rollback();
	            }
	        }
	    }

	    return exito;
	}

	@Override
	public int modificarProducto(TProducto producto) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public TProducto leerProducto(int id) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public Set<TProducto> leerTodosProductos() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	
	
}