package negocio.Empleado;

import java.util.Set;

import integracion.Empleado.DAOEmpleado;
import integracion.FactoriaDAO.DAOAbstractFactory;
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
	public void calcularImporteMasVendido(TProducto tProducto) {
		
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

			if (existente == null || existente.getActivo() == 0) {
				transaction.rollback();
			} else {
				exito = daoEmpleado.update(tEmpleado);
				if (exito != -1)
					transaction.commit();
				else
					transaction.rollback();
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
			empleado = daoEmpleado.read(id);

			transaction.commit();
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
}
