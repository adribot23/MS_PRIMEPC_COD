package integracion.daos;

import java.util.Collection;

import negocio.transfers.TProducto;

public interface DAOProducto {
	public int crear (TProducto producto);
	public int eliminar(int id);
	public int eliminarFisicamente(int id);  //solo para el test
	public int actualizar(TProducto producto);
	public TProducto leer(int id);
	public TProducto leerPorModelo(String nombre);
	public TProducto leerPorMarcaYModelo(String modelo,String marca);
	public Collection<TProducto> leerTodo();
	public Collection<TProducto> leerPorAlmacen(int idAlmacen);
	public Collection<TProducto> leerPorProveedor(int idProveedor);
}
