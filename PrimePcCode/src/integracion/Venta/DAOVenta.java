
package integracion.Venta;

import java.util.Set;

import negocio.Venta.TVenta;

public interface DAOVenta {

	public int create(TVenta venta);

	public Set<TVenta> read_by_cliente(int idCliente);

	public Set<TVenta> read_by_empleado(int idEmpleado);

	public TVenta read(int id_venta);

	public int update(TVenta venta);

	public int delete(int id_venta);

	public Set<TVenta> read_all();
}