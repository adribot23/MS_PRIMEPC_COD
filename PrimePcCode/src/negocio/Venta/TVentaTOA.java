
package negocio.Venta;

import java.util.Set;

import negocio.Empleado.TEmpleado;
import negocio.Producto.TProducto;


public class TVentaTOA {

	private Set<TLineaVenta> lista_lineasVenta;

	private TVenta venta;

	private TEmpleado empleado;

	private Set<TProducto> lista_producto;


	public void colocar_datos(TVenta venta, Object empleado, Set<TProducto> lista_producto) {
		
		this.venta = venta;
		this.empleado = (TEmpleado) empleado;
		this.lista_producto = lista_producto;
		
	}


	public Set<TLineaVenta> get_lista_lineasVenta() {
		
		return this.lista_lineasVenta;
	}


	public void set_lista_lineasVenta(Set<TLineaVenta> lista_lineasVenta) {
		
		this.lista_lineasVenta = lista_lineasVenta;
	}

	
	public TVenta get_venta() {
		
		return this.venta;
	}


	public void set_venta(TVenta venta) {
		
		this.venta = venta;
	}

	
	public TEmpleado get_empleado() {
		
		return this.empleado;
	}


	public void set_empleado(TEmpleado empleado) {
		
		this.empleado = empleado;
	}


	public Set<TProducto> get_lista_producto() {
		
		return this.lista_producto;
	}

	
	public void set_lista_producto(TProducto... lista_producto) {
		
		for (TProducto producto : lista_producto) {
			this.lista_producto.add(producto);
		}
	}
}