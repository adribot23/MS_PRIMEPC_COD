package integracion.daos;


import java.util.Collection;

import negocio.transfers.TCliente;

public interface DAOCliente {
	public int crear (TCliente cliente);
	public TCliente leer(int id);
	public int actualizar(TCliente cliente);
	public int eliminar(int id);
	public int eliminarFisicamente(int id);  //solo para el test
	public TCliente leerPorDNI(String nombre);
	public Collection<TCliente> leerTodo();
	
}
