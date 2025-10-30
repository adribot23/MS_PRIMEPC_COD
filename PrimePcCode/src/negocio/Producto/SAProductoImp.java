package negocio.Producto;
import java.util.Set;
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

		Set<TProducto> tprT = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			tprT = daoProducto.read_by_proveedor(idProveedor);

			if (tprT == null)
				transaction.rollback();
			else
				transaction.commit();

		}
		return tprT;


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

		int exito = -1;
		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
			TProducto tpByModelo = daoProducto.read_by_modelo(producto.getModelo());
			TProducto tpById = daoProducto.read(producto.getId());

			if (tpByModelo == null
					|| (tpByModelo.getId() == tpById.getId() && tpByModelo.getModelo().equals(tpById.getModelo()))) {
				exito = daoProducto.update(producto);
				if (exito != -1)
					transaction.commit();
				else
					transaction.rollback();
			} else
				transaction.rollback();
		}
		return exito;

		// TODO Apéndice de método generado automáticamente


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
			tpr = daoProducto.read(id);

			if (tpr == null)
				transaction.rollback();
			else
				transaction.commit();
		}
		return tpr;

		// TODO Apéndice de método generado automáticamente


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

		/

	}

	
	
}