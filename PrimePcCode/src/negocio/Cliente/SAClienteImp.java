/**
 * 
 */
package negocio.Cliente;

import java.util.HashSet;
import java.util.Set;

import integracion.Cliente.DAOCliente;
import integracion.FactoriaDAO.DAOAbstractFactory;
import negocio.Cliente.TCliente;
import negocio.Proveedor.TProveedor;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;

public class SAClienteImp implements SACliente {

	public SAClienteImp() {

	}

	@Override
	public int altaCliente(TCliente tCliente) {
		int id = -1;
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
		if (tr != null) {
			tr.start();
			if (tCliente != null) {
				TCliente leido = daoCliente.read_by_DNI(tCliente.getDni());
				if (leido == null) {
					id = daoCliente.create(tCliente);
					tr.commit();
				} else if (leido.getActivo() == 0) {
					tCliente.setId(leido.getId());
					daoCliente.update(tCliente);
					id = tCliente.getId();
					tr.commit();
				} else {
					tr.rollback();
				}
			}
		}
		return id;
	}

	@Override
	public int bajaCliente(int id) {
		int res = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
			TCliente cliente = daoCliente.read(id);

			if (cliente != null) {
				if (cliente.getActivo() == 1) {
					res = daoCliente.delete(id);
					if (res != -1)
						tr.commit();
					else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
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
	        TCliente existente = daoCliente.read(cliente.getId());

	        if (existente != null && existente.getActivo() == 1) {

	            TCliente clienteConMismoDni = daoCliente.read_by_DNI(cliente.getDni());
	            if (clienteConMismoDni == null || clienteConMismoDni.getId() == cliente.getId()) {

	                res = daoCliente.update(cliente);

	                if (res != -1)
	                    tr.commit();
	                else
	                    tr.rollback();

	            } 

	        }
	    }

	    return res;
	}



	@Override
	public TCliente leerCliente(int id) {
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		TCliente leido = null;
		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
			leido = daoCliente.read(id);
			if (leido != null && leido.getActivo() == 1)
				tr.commit();
			else {
				tr.rollback();
			}

		}
		return leido;
	}

	@Override
	public Set<TCliente> leerTodosClientes() {
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		Set<TCliente> clientes = null;

		if (tr != null) {
			tr.start();
			DAOCliente daoCliente = DAOAbstractFactory.getInstancia().generaDAOCliente();
			clientes = daoCliente.read_all();
			tr.commit();
		}
		
		return clientes;
	}
}