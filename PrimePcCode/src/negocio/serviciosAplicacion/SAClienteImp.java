package negocio.serviciosAplicacion;

import java.util.Collection;

import integracion.daos.DAOCliente;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TCliente;

public class SAClienteImp implements SACliente {

	private DAOCliente daoCliente;

	public SAClienteImp() {

		this.daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
	}

	@Override
	public int altaCliente(TCliente tCliente) {
		int id = -1;
		if (tCliente != null) {

			TCliente leido = daoCliente.leerPorDNI(tCliente.getDni());
			if (leido == null) {
				id = daoCliente.crear(tCliente);
			} else if (leido.getActivo() == 0 && leido.getClass().equals(tCliente.getClass())) {
				tCliente.setId(leido.getId());
				daoCliente.actualizar(tCliente);
				id = tCliente.getId();
			}
		}
		return id;
	}

	@Override
	public TCliente leerCliente(int id) {
		TCliente leido = daoCliente.leer(id);
		if (leido != null && leido.getActivo() == 1) {
			return leido;
		}
		return null;
	}

	@Override
	public Collection<TCliente> leerTodosClientes() {
		return daoCliente.leerTodo();
	}

	@Override
	public int modificarCliente(TCliente cliente) {
		int res = -1;
		TCliente existente = daoCliente.leer(cliente.getId());

		if (existente != null && existente.getActivo() == 1 && existente.getClass().equals(cliente.getClass())
				&& (cliente.getDni().equals(existente.getDni())
						|| daoCliente.leerPorDNI(cliente.getDni()) == null)) {
			res = daoCliente.actualizar(cliente);
		}

		return res;
	}

	@Override
	public int bajaCliente(int id) {
		int res = -1;
		TCliente existente = daoCliente.leer(id);
		if (existente != null && existente.getActivo() == 1) {
			res = daoCliente.eliminar(id);
		}
		return res;
	}
}
