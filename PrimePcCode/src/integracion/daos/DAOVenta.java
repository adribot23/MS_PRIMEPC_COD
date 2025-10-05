package integracion.daos;

import java.util.Collection;

import negocio.transfers.TVenta;

public interface DAOVenta {
	public int crear(TVenta venta);

	public TVenta leer(int id);

	public int actualizar(TVenta venta);

	public int eliminar(int id);

	public int eliminarFisicamente(int id); // solo para el test

	public Collection<TVenta> leerTodo();

	public int vincularProducto(int id_venta, int id_producto, int cantidad, double importe);

	Collection<TVenta> leerPorEmpleado(int idEmpleado);

	Collection<TVenta> leerPorCliente(int idCliente);
}
