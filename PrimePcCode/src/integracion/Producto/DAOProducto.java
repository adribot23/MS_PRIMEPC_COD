package integracion.Producto;

import java.util.Set;

import negocio.Producto.TProducto;

public interface DAOProducto {

	public int create(TProducto producto);

	public TProducto read(int id);

	public int update(TProducto producto);

	public int delete(int id);

	public TProducto read_by_modelo(String nombre);

	public Set<TProducto> read_by_marca_and_modelo(String modelo, String marca);

	public Set<TProducto> read_all();

	public Set<TProducto> read_by_almacen(int idAlmacen);

	public Set<TProducto> read_by_proveedor(int idProveedor);
}