package negocio.TrabajadorJPA;

import javax.persistence.Entity;
import javax.persistence.Version;

import negocio.TransporteJPA.Transporte;

@Entity
public class Trabajador {
	private static final long serialVersionUID = 0;

	private int id;
	private int DNI;
	private String nombre;
	private int activo;

	@Version
	private Integer version;

	/*
	 * @ManyToMany private Ruta ruta;
	 */

	/*
	 * @ManyToMany private Transporte transporte;
	 */

	public Trabajador() {
	}

	public Trabajador(TTrabajador t) {
		this.id = t.getId();
		this.DNI = t.getDNI();
		this.nombre = t.getNombre();
		this.activo = t.isActivo();
	}

	public TTrabajador entityToTransfer() {
		TTrabajador tTrabajador = new TTrabajador();
		tTrabajador.setId(this.id);
		tTrabajador.setDNI(DNI);
		tTrabajador.setNombre(nombre);
		return tTrabajador;
	}

	// getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDNI() {
		return DNI;
	}

	public int getActivo() {
		return activo;
	}

	// setters
	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public static Transporte[] getTransportes() {
		// TODO Auto-generated method stub
		return null;
	}

}
