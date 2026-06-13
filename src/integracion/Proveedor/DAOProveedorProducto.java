package integracion.Proveedor;

import java.util.Set;

import negocio.Producto.TProducto;
import negocio.Proveedor.TProveedor;
import negocio.Proveedor.TProveedorProducto;

public interface DAOProveedorProducto {
	public int create(TProveedorProducto tProveedorProducto);

	public TProveedorProducto read(int id_proveedor, int id_producto);

	public int update(TProveedorProducto tProveedorProducto);

	public int delete(int id_proveedor, int id_producto);

	public Set<TProveedorProducto> read_all();

	public Set<TProveedorProducto> read_all_by_producto(int id_producto);

	public Set<TProveedorProducto> read_all_by_proveedor(int id_proveedor);
}