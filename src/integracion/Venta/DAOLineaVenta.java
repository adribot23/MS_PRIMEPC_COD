
package integracion.Venta;

import java.util.Set;

import negocio.Venta.TLineaVenta;

public interface DAOLineaVenta {

	public int create(TLineaVenta lineaVenta);

	public int delete(TLineaVenta tlineaVenta);

	public TLineaVenta read(TLineaVenta tlineaVenta);

	public Set<TLineaVenta> read_all(int id_venta);

	public int update(TLineaVenta tlineaVenta);

	public TLineaVenta read_all_by_producto(int id_producto);
}