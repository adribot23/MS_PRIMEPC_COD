/**
 * 
 */
package integracion.Cliente;

import java.util.Set;

import negocio.Cliente.TCliente;

public interface DAOCliente {

	public int create(TCliente cliente);

	public TCliente read(int id);

	public int update(TCliente cliente);

	public int delete(int id);

	public TCliente read_by_DNI(String dni);

	public Set<TCliente> read_all();
}