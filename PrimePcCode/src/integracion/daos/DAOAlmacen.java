package integracion.daos;

import java.util.Collection;

import negocio.transfers.TAlmacen;

public interface DAOAlmacen {
	public int crear(TAlmacen almacen);

	public TAlmacen leer(int id);

	public int actualizar(TAlmacen almacen);

	public int eliminar(int id);

	public int eliminarFisicamente(int id); // solo para el test

	public TAlmacen leerPorNombre(String nombre);

	public Collection<TAlmacen> leerTodo();
}
