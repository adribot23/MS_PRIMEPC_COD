package negocio.PaqueteJPA;

import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Paquete {
	private static final long serialVersionUID = 0;
	
	private int id;
	private String estado;
	private double peso;
	private double precio;
	private int activo;
	
	@Version
	private Integer version;
	
	/*
	@ManyToOne
	private Ruta ruta;
	*/
	
	/*
	@ManyToOne
	private Remitente remitente;
	*/
	
	/*
	@ManyToOne
	private Factura factura;
	*/
	
	public Paquete() {}
	
	public TPaquete entityToTransfer() {
		TPaquete tPaquete = new TPaquete();
		tPaquete.setId(this.id);
		tPaquete.setEstado(this.estado);
		tPaquete.setPeso(this.peso);
		tPaquete.setPrecio(this.precio);
		return tPaquete;
	}
	//getters
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
	
	public int getActivo() {
		return this.activo;
	}
	
	//setters
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
	
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
}
