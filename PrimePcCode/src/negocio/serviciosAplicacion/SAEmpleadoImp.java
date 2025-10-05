package negocio.serviciosAplicacion;

import java.util.Collection;

import integracion.daos.DAOEmpleado;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TEmpleado;

public class SAEmpleadoImp implements SAEmpleado {

	private DAOEmpleado daoEmpleado;

	public SAEmpleadoImp() {
		this.daoEmpleado = FactoriaIntegracion.obtenerInstancia().generaDAOEmpleado();
	}

	@Override
	public int altaEmpleado(TEmpleado tEmpleado) {
		int id = -1;
		if (tEmpleado != null) {

			TEmpleado leido = daoEmpleado.leerPorDNI(tEmpleado.getDni());
			if (leido == null) {
				id = daoEmpleado.crear(tEmpleado);
			} else if (leido.getActivo() == 0 && leido.getClass().equals(tEmpleado.getClass())) {
				tEmpleado.setId(leido.getId());
				daoEmpleado.actualizar(tEmpleado);
				id = tEmpleado.getId();
			}
		}
		return id;
	}

	@Override
	public TEmpleado leerEmpleado(int id) {
		TEmpleado leido = daoEmpleado.leer(id);
		if (leido != null && leido.getActivo() == 1) {
			return leido;
		}
		return null;
	}

	@Override
	public Collection<TEmpleado> leerTodosEmpleados() {
		return daoEmpleado.leerTodo();
	}

	@Override
	public int modificarEmpleado(TEmpleado tEmpleado) {
		int res = -1;

		TEmpleado existente = daoEmpleado.leer(tEmpleado.getId());

		if (existente != null && existente.getActivo() == 1 && existente.getClass().equals(tEmpleado.getClass())
				&& (tEmpleado.getDni().equals(existente.getDni())
						|| daoEmpleado.leerPorDNI(tEmpleado.getDni()) == null)) {
			res = daoEmpleado.actualizar(tEmpleado);
		}

		return res;
	}

	@Override
	public int bajaEmpleado(int id) {
		int res = -1;
		TEmpleado existente = daoEmpleado.leer(id);
		if (existente != null && existente.getActivo() == 1) {
			res = daoEmpleado.eliminar(id);
		}
		return res;
	}
}