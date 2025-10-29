/**
 * 
 */
package negocio.Cliente;

import java.util.HashSet;
import java.util.Set;

import integracion.Cliente.DAOCliente;
import integracion.FactoriaDAO.DAOAbstractFactory;
import negocio.Cliente.TCliente;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;


/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author adria
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class SAClienteImp implements SACliente {
	
	private DAOCliente daoCliente;
	
	public SAClienteImp() {

		this.daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
	}
	
	@Override
	public int altaCliente(TCliente tCliente) {
		int id = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();

			if (tCliente != null) {
				TCliente leido = daoCliente.read_by_DNI(tCliente.getDni());

				if (leido == null) {
					id = daoCliente.create(tCliente);
					if (id != -1)
						tr.commit();
					else
						tr.rollback();

				} else if (leido.getActivo() == 0 && leido.getClass().equals(tCliente.getClass())) {
					tCliente.setId(leido.getId());
					int res = daoCliente.update(tCliente);
					if (res != -1) {
						id = tCliente.getId();
						tr.commit();
					} else
						tr.rollback();

				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return id;
	}

	@Override
	public int bajaCliente(Integer id) {
		int res = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();

			if (id != null) {
				TCliente existente = daoCliente.read(id);

				if (existente != null && existente.getActivo() == 1) {
					res = daoCliente.delete(id);
					if (res != -1)
						tr.commit();
					else
						tr.rollback();
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

	@Override
	public int modificarCliente(TCliente cliente) {
		int res = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();

			if (cliente != null) {
				TCliente existente = daoCliente.read(cliente.getId());

				if (existente != null && existente.getActivo() == 1 && existente.getClass().equals(cliente.getClass())
						&& (cliente.getDni().equals(existente.getDni()) 
							|| daoCliente.read_by_DNI(cliente.getDni()) == null)) {

					res = daoCliente.update(cliente);
					if (res != -1)
						tr.commit();
					else
						tr.rollback();
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

	@Override
	public TCliente leerCliente(Integer id) {
		TCliente leido = null;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();

			if (id != null) {
				TCliente t = daoCliente.read(id);
				if (t != null && t.getActivo() == 1) {
					leido = t;
					tr.commit();
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return leido;
	}

	@Override
	public Set<TCliente> leerTodosClientes() {
		Set<TCliente> clientes = null;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();

			clientes = new HashSet<>(daoCliente.read_all());
			if (clientes != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return clientes;
	}
}