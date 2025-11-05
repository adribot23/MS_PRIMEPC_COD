/**
 * 
 */
package negocio.Empleado;

import java.util.AbstractMap;
import java.util.Set;

import negocio.Producto.TProducto;

public interface SAEmpleado {

	public int altaEmpleado(TEmpleado tEmpleado);

	public AbstractMap.SimpleEntry<Integer, Integer> calcularImporteMasVendido(int idProducto);

	public int bajaEmpleado(int id);

	public int modificarEmpleado(TEmpleado tEmpleado);

	public TEmpleado leerEmpleado(int id);

	public Set<TEmpleado> leerTodosEmpleados();
}