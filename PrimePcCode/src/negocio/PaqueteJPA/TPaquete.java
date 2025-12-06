package negocio.PaqueteJPA;

public class TPaquete {
	int id;
	String numSerie;
	String estado;
	double peso;
	double precio;
	int activo;

	// getters

	public int getId() {
		return this.id;
	}

	public String getNumSerie() {
		return this.numSerie;
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
	
	public int getActivo() {
		return this.activo;
	}

	// setters

	public void setId(int id) {
		this.id = id;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
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
	
	public void setActivo(int activo) {
		this.activo = activo;
	}

}
