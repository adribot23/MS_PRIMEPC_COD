package negocio.transfers;

import java.io.Serializable;

public class TProveedor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String nombre;
	protected int activo;

	public TProveedor() {
	};

	public TProveedor(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Proveedor [ID: " + id + ", Nombre: " + nombre +"]";
	}
}
