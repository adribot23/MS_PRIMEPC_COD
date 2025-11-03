/**
 * 
 */
package integracion.Almacen;

import negocio.Almacen.TAlmacen;
import java.util.Set;


public interface DAOAlmacen {

	public Integer create(TAlmacen almacen);

	public TAlmacen read(Integer id);

	public Integer update(TAlmacen almacen);

	public Integer delete(Integer id);

	public TAlmacen read_by_name(String nombre);

	public Set<TAlmacen> read_all();
}