
package integracion.daos;


import java.util.Collection;


import negocio.transfers.TProveedor;

public interface DAOProveedor {
	public int crear (TProveedor proveedor);
	public TProveedor leer(int id);
	public int actualizar(TProveedor proveedor);
	public int eliminar(int id);
	int eliminarFisicamente(int id); //solo para el test
	public TProveedor leerPorNombre(String nombre);
	public Collection<TProveedor> leerTodo();
}

