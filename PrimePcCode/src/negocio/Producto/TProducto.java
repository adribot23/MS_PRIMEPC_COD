/**
 * 
 */
package negocio.Producto;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author adria
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TProducto {
	protected int id;
	protected String marca;
	protected String modelo;
	protected double precio;
	protected int unidades;
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

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Producto [ID: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Precio: " + precio
				+ ", Unidades: " + unidades + "]";
	}
}