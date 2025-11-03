/**
 * 
 */
package negocio.Empleado;

import java.util.Set;

import negocio.Producto.TProducto;

public interface SAEmpleado {

	public int altaEmpleado(TEmpleado tEmpleado);

	public int calcularImporteMasVendido(int idProducto);

	public int bajaEmpleado(int id);

	public int modificarEmpleado(TEmpleado tEmpleado);

	public TEmpleado leerEmpleado(int id);

	public Set<TEmpleado> leerTodosEmpleados();
}