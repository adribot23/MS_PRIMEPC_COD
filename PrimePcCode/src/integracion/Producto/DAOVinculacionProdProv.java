package integracion.Producto;

import java.util.Set;

import negocio.Producto.TVinculacionProdProv;


public interface DAOVinculacionProdProv {
	public int create(TVinculacionProdProv producto);
	
	public TVinculacionProdProv read(int id);

	public int update(TVinculacionProdProv producto);

	public int delete(int id);

	public Set<TVinculacionProdProv> read_all();

	public Set<TVinculacionProdProv> read_all_by_almacen(int idAlmacen);

	public Set<TVinculacionProdProv> read_all_by_proveedor(int idProveedor);
}
