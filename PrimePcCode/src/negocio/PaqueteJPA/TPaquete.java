package negocio.PaqueteJPA;

public class TPaquete {
	int id;
	String estado;
	double peso;
	double precio;
	
	// getters
	
	public int getId() {
		return this.id;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public double getPeso() {
		return this.peso;
	}
	
	public double getPrecio() {
		return this.precio;
	}
	
	// setters
	
	public void setId(int id) {
		this.id = id;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
