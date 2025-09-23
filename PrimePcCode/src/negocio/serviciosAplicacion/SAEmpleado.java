package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TEmpleado;

public interface SAEmpleado {
	public int altaEmpleado(TEmpleado tEmpleado);
	public TEmpleado leerEmpleado(int id);
	public Collection<TEmpleado> leerTodosEmpleados();
	public int modificarEmpleado(TEmpleado tEmpleado);
	public int bajaEmpleado (int id);
}
