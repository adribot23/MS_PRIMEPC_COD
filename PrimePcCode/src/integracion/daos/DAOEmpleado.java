package integracion.daos;

import java.util.Collection;

import negocio.transfers.TEmpleado;

public interface DAOEmpleado {
	public int crear (TEmpleado empleado);
	public TEmpleado leer(int id);
	public int actualizar(TEmpleado empleado);
	public int eliminar(int id);
	public int eliminarFisicamente(int id);  //solo para el test
	public TEmpleado leerPorDNI(String nombre);
	public Collection<TEmpleado> leerTodo();
}
