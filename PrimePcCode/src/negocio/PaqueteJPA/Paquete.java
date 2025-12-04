package negocio.PaqueteJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Paquete {
	private static final long serialVersionUID = 0;
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@SequenceGenerator(name = "seq", sequenceName = "SEQ_ID", allocationSize = 1)
	@Id
	private int id;
	String numSerie;
	private String estado;
	private double peso;
	private double precio;
	private int activo;

	@Version
	private Integer version;

	/*
	 * @ManyToOne private Ruta ruta;
	 */

	/*
	 * @ManyToOne private Remitente remitente;
	 */

	/*
	 * @ManyToOne private Factura factura;
	 */

	public Paquete() {
	}

	public TPaquete entityToTransfer() {
		TPaquete tPaquete = new TPaquete();
		tPaquete.setId(this.id);
		tPaquete.setEstado(this.estado);
		tPaquete.setPeso(this.peso);
		tPaquete.setPrecio(this.precio);
		return tPaquete;
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
