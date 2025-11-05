package negocio.Empleado;

import java.util.Set;

import integracion.Empleado.DAOEmpleado;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.FactoriaQuery.FactoriaQuery;
import integracion.FactoriaQuery.Query;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import negocio.Producto.TProducto;

public class SAEmpleadoImp implements SAEmpleado {

	@Override
	public int altaEmpleado(TEmpleado tEmpleado) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
			TEmpleado em = daoEmpleado.read_by_DNI(tEmpleado.getDni());

			if (em == null) {
				exito = daoEmpleado.create(tEmpleado);
				if (exito != -1)
					transaction.commit();
				else
					transaction.rollback();
			} else if (em.getActivo() == 0) {
				tEmpleado.setId(em.getId());
				tEmpleado.setActivo(1);
				exito = daoEmpleado.update(tEmpleado);
				if (exito != -1) {
					exito = em.getId();
					transaction.commit();
				} else
					transaction.rollback();
			} else
				transaction.rollback();
		}
		return exito;
	}

	@Override
	public int bajaEmpleado(int id) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
			TEmpleado empleado = daoEmpleado.read(id);

			if (empleado == null || empleado.getActivo() == 0) {
				transaction.rollback();
			} else {
				exito = daoEmpleado.delete(id);
				if (exito != -1)
					transaction.commit();
				else
					transaction.rollback();
			}
		}

		return exito;
	}

	@Override
	public int modificarEmpleado(TEmpleado tEmpleado) {
		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
			TEmpleado existente = daoEmpleado.read(tEmpleado.getId());
			System.err.println();

			if(existente == null || existente.getActivo() == 0 || !tEmpleado.getClass().equals(existente.getClass())) {
				transaction.rollback();
			} else {
				exito = daoEmpleado.update(tEmpleado);
				if (exito != -1) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}
		}

		return exito;
	}

	@Override
	public TEmpleado leerEmpleado(int id) {
	    TEmpleado empleado = null;

	    TManager tManager = TManager.getInstance();
	    tManager.createTransaction();
	    Transaction transaction = tManager.getTransaction();

	    if (transaction != null) {
	        transaction.start();

	        DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
	        TEmpleado temp = daoEmpleado.read(id);

	        if (temp != null && temp.getActivo() == 1) {
	            empleado = temp; 
	            transaction.commit();
	        } else {
	            transaction.rollback();
	        }
	    }

	    return empleado;
	}


	@Override
	public Set<TEmpleado> leerTodosEmpleados() {
		Set<TEmpleado> empleados = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
			empleados = daoEmpleado.read_all();

			transaction.commit();
		}

		return empleados;
	}

	@Override
	public int calcularImporteMasVendido(int idProducto) {
		 int importe = -1; // Valor inicial por defecto en caso de error
		    TManager tm = TManager.getInstance();
		    Transaction tr = tm.createTransaction();

		    if (tr != null) {
		        tr.start();

		        FactoriaQuery fq = FactoriaQuery.getInstance();
		        Query q = fq.getNewQuery("CalcularImporteEmpleado");

		        Object resultado = q.execute(idProducto);

		        if (resultado == null) {
		            tr.rollback();
		            return importe;
		        }

		        if (resultado instanceof Integer) {
		            importe = (Integer) resultado;
		            tr.commit();
		            tr.rollback();
		        }
		    }

		    return importe;
	}
}
