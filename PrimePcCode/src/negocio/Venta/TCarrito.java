
package negocio.Venta;

import java.util.Set;

public class TCarrito {

	private int id_carrito;

	private int id_empleado;

	private int id_cliente;

	private int estado;

	private Double total;

	private Set<TLineaVenta> lineasVenta;

	public int getId() {
		
		return this.id_carrito;
	}


	public void setId(int id) {
		
		this.id_carrito = id;
	}


	public Integer getIdEmpleado() {
		
		return this.id_empleado;
	}


	public void setIdEmpleado(int id) {
		
		this.id_empleado = id;
	}


	public Integer getIdCliente() {
		
		return this.id_cliente;
	}


	public void setIdCliente(int id) {
		
		this.id_cliente = id;
	}


	public Double getTotal() {
		
		return this.total;
	}


	public void setTotal(Double total) {
		
		this.total = total;
	}


	public Integer getEstado() {
		
		return this.estado;
	}


	public void setEstado(int estado) {
		
		this.estado = estado;
	}


	public Set<TLineaVenta> getLineasVenta() {
		
		return this.lineasVenta;
	}


	public void setLineasVenta(TLineaVenta... lineasVenta) {
		
		for (TLineaVenta lineaVenta : lineasVenta) {
			this.lineasVenta.add(lineaVenta);
		}
		
	}
}