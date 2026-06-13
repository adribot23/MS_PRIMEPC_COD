/**
 * 
 */
package negocio.Almacen;

import java.util.Set;

public interface SAAlmacen {

	public Integer altaAlmacen(TAlmacen almacen);

	public Integer bajaAlmacen(Integer id);

	public Integer modificarAlmacen(TAlmacen almacen);

	public TAlmacen leerAlmacen(Integer id);

	public Set<TAlmacen> leerTodosAlmacenes();

	// public Integer vincularProductoAlmacen(Integer idProducto, Integer
	// idAlmacen);

	// public Integer desvincularProductoAlmacen(Integer idProducto, Integer
	// idAlmacen);
}