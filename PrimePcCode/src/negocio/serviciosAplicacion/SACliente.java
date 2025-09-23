package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TCliente;


public interface SACliente {
	public int altaCliente(TCliente tCliente);
	public TCliente leerCliente(int id);
	public Collection<TCliente> leerTodosClientes();
	public int modificarCliente(TCliente tCliente);
	public int bajaCliente (int id);
}
