
package negocio.Venta;

public class TVenta {

	private Integer id_venta;

	private String metodoPago;

	private Double precio;

	private Double descuento;

	private Integer id_empleado;

	private Integer id_cliente;

	private int activo;

	public Integer getId() {
		
		return this.id_venta;
	}


	public void setId(Integer id) {
		this.id_venta = id;
		
	}


	public String getMetodoPago() {
		return this.metodoPago;
	}


	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}


	public int getActivo() {
		return this.activo;
	}


	public void setActivo(int activo) {
		
		this.activo = activo;
	}


	public Double getPrecio() {
		
		return this.precio;
	}


	public void setPrecio(Double precio) {
		
		this.precio = precio;
	}


	public Double getDescuento() {
		
		return this.descuento;
	}


	public void setDescuento(Double descuento) {
		
		this.descuento = descuento;
	}


	public Integer getIdCliente() {
		
		return this.id_cliente;
	}


	public void setIdCliente(Integer idCliente) {
		
		this.id_cliente = idCliente;
	}


	public Integer getIdEmpleado() {
		
		return this.id_empleado;
	}


	public void setIdEmpleado(Integer idEmpleado) {
		
		this.id_empleado = idEmpleado;
	}
}