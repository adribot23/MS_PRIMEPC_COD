
package integracion.Venta;

import java.util.Set;

import negocio.Venta.TVenta;

public interface DAOVenta {

	public Integer create(TVenta venta);

	public Set<TVenta> read_by_cliente(Integer idCliente);

	public Set<TVenta> read_by_empleado(Integer idEmpleado);

	public TVenta read(Integer id_venta);

	public Integer update(TVenta venta);

	public Integer delete(Integer id_venta);

	public Set<TVenta> read_all();
}