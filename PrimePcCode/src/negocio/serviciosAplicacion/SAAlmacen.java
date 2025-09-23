package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TAlmacen;

public interface SAAlmacen {
	public int altaAlmacen(TAlmacen tAlmacen);
	public TAlmacen leerAlmacen(int id);
	public Collection<TAlmacen> leerTodosAlmacenes();
	public int modificarAlmacen(TAlmacen tAlmacen);
	public int bajaAlmacen (int id);
	public int vincularProductoAlmacen(int idProducto, int idAlmacen);
	public int desvincularProductoAlmacen(int idProducto, int idAlmacen);
}
