package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TProveedor;

public interface SAProveedor {
	public int altaProveedor(TProveedor tProveedor);

	public int bajaProveedor(int id);

	public int modificarProveedor(TProveedor tProveedor);

	public TProveedor leerProveedor(int id);

	public Collection<TProveedor> leerTodosProveedores();

	public TProveedor leerProveedorPorProducto(int idProducto);

	public int vincularProductoProveedor(int idProducto, int idProveedor);

	public int desvincularProductoProveedor(int idProducto, int idProveedor);

}
