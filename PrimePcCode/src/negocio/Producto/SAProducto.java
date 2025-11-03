/**
 * 
 */
package negocio.Producto;

import java.util.Set;

public interface SAProducto {
	public int altaProducto(TProducto producto);

	public Set<TProducto> leerProductosPorAlmacen(int idAlmacen);

	public Set<TProducto> leerProductosPorProveedor(int idProveedor);

	public int bajaProducto(int id);

	public int modificarProducto(TProducto producto);

	public TProducto leerProducto(int id);

	public Set<TProducto> leerTodosProductos();
}