package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TProducto;

public interface SAProducto {
	public int altaProducto(TProducto tProducto);

	public TProducto leerProducto(int id);

	public Collection<TProducto> leerTodosProductos();

	public int modificarProducto(TProducto tProdcuto);

	public int bajaProducto(int id);

	public Collection<TProducto> leerProductosPorProveedor(int idProveedor);

	public Collection<TProducto> leerProductosPorAlmacen(int idAlmacen);
}
