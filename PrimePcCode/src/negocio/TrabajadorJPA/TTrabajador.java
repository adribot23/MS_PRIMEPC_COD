package negocio.TrabajadorJPA;

import negocio.RemitenteJPA.Remitente;

public class TTrabajador {
	private int id_trabajador;
	private String DNI;
	private String nombre;
	private int activo;
	
	public TTrabajador() {
	}
	
	public TTrabajador(int id_trabajador, int activo, String nombre, String DNI) {
		super();
		this.id_trabajador = id_trabajador;
		this.activo = activo;
		this.nombre = nombre;
		this.DNI = DNI;
	}

	public TTrabajador(Trabajador trabajador) {
		this.id_trabajador = trabajador.getId();
		this.nombre = trabajador.getNombre();
		this.activo = trabajador.getActivo();
		this.DNI = trabajador.getDNI();
	}

	// getters
	public int getId() {
		return id_trabajador;
	}

	public void setId(int id) {
		this.id_trabajador = id;
	}

	public String getDNI() {
		return DNI;
	}

	// setters
	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int isActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

}
