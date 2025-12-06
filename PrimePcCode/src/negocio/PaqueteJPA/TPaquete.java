package negocio.PaqueteJPA;

public class TPaquete {
	int id;
	String numSerie;
	String estado;
	double peso;
	double precio;
	int idRuta;
	int idFactura;
	int activo;
	
	public TPaquete() {}
	public TPaquete(int id, double peso, String estado, int activo, int idRuta) {
        this.id = id;
        this.peso = peso;
        this.estado = estado;
        this.activo = activo;
        this.idRuta = idRuta;
    }
	
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
	
	public int getIdRuta() {
		return this.activo;
	}
	
	public int getIdFactura() {
		return this.activo;
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
	
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	
	public void setActivo(int activo) {
		this.activo = activo;
	}

}
