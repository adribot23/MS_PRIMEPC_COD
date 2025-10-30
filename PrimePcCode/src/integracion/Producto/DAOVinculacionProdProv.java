package integracion.Producto;

import java.util.Set;

import negocio.Producto.TVinculacionProdProv;


public interface DAOVinculacionProdProv {
	public int create(TVinculacionProdProv producto);
	
	public TVinculacionProdProv read(int idProducto, int idProveedor);

	public int delete(int idProducto, int idProveedor);

	public Set<TVinculacionProdProv> read_all();

	public Set<TVinculacionProdProv> read_all_by_producto(int idProducto);

	public Set<TVinculacionProdProv> read_all_by_proveedor(int idProveedor);
}
