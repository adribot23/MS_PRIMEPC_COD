/**
 * 
 */
package negocio.Cliente;

import java.util.Set;

public interface SACliente {

	public int altaCliente(TCliente tCliente);

	public int bajaCliente(int id);

	public int modificarCliente(TCliente tCliente);

	public TCliente leerCliente(int id);

	public Set<TCliente> leerTodosClientes();
}