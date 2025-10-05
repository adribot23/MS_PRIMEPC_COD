package negocio.transfers;

import java.io.Serializable;

public class TProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String marca;
	protected String modelo;
	protected double precio;
	protected int unidades;
	protected int id_proveedor;
	protected int id_almacen;
	protected int activo;

	public TProducto() {
	};

	public TProducto(double precio, String modelo, int unidades, String marca) {
		this.precio = precio;
		this.modelo = modelo;
		this.unidades = unidades;
		this.marca = marca;
	}

	public TProducto(int id, double precio, String modelo, int unidades, String marca) {
		this.id = id;
		this.precio = precio;
		this.modelo = modelo;
		this.unidades = unidades;
		this.marca = marca;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getUnidades() {
		return this.unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getIdProveedor() {
		return this.id_proveedor;
	}

	public void setIdProveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public int getIdAlmacen() {
		return this.id_almacen;
	}

	public void setIdAlmacen(int id_proveedor) {
		this.id_almacen = id_proveedor;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Producto [ID: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio
				+ ", Unidades: " + unidades + ", ID Proveedor: " + id_proveedor + ", ID Almacen: " + id_almacen + "]";
	}
}
