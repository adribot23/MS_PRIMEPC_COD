package negocio.transfers;

import java.io.Serializable;
import java.util.Map;

public class TVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String metodoPago;
	protected double precio;
	protected double descuento;
	protected int id_empleado;
	protected int id_cliente;
	protected int activo;
	protected Map<Integer, Integer> productos; // id de producto, cantidad de
												// producto

	public TVenta() {
	};

	public TVenta(int id, String metodoPago, double precio, double descuento, int id_empleado, int id_cliente,
			Map<Integer, Integer> productos) {
		this.id = id;
		this.metodoPago = metodoPago;
		this.precio = precio;
		this.descuento = descuento;
		this.id_empleado = id_empleado;
		this.id_cliente = id_cliente;
		this.productos = productos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetodoPago() {
		return this.metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public double getPrecio() {
		return this.precio;
	}

	public double getImporteTotal() {
		return precio - descuento;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getDescuento() {
		return this.descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public int getIdEmpleado() {
		return this.id_empleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.id_empleado = idEmpleado;
	}

	public int getIdCliente() {
		return this.id_cliente;
	}

	public void setIdCliente(int idCliente) {
		this.id_cliente = idCliente;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Map<Integer, Integer> getProductos() {
		return productos;
	}

	public void setProductos(Map<Integer, Integer> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Venta [ID: " + id + ", Método de Pago: " + metodoPago + ", Precio: " + precio + ", Descuento: "
				+ descuento + ", Importe Total: " + getImporteTotal() + ", ID Empleado: " + id_empleado
				+ ", ID Cliente: " + id_cliente + ", Activo: " + activo + "]";
	}
}
