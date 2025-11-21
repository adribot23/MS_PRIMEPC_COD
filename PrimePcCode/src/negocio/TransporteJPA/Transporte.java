package negocio.TransporteJPA;

import java.util.Set;

public class Transporte {
	
	private int id_transporte;
	
	private String nombre;
	
	private int capacidad;
	
	private String matricula;
	
	private int activo;
	
	private Set<VinculacionTransporteTrabajador> vinculaciones;

	public Transporte(TTransporte t) {
		this.id_transporte = t.getId();
		this.nombre = t.getNombre();
		this.matricula = t.getMatricula();
		this.capacidad = t.getCapacidad();
		this.activo = t.getActivo();
	}

	public int getId() {
		return id_transporte;
	}
	
	public int getActivo() {
		return this.activo;
	}
	
	public void setActivo(int a) {
		this.activo = a;
	}

	public Set<VinculacionTransporteTrabajador> getVinculaciones() {
		return this.vinculaciones;
	}
	
	public void setvinculaciones(Set<VinculacionTransporteTrabajador> vinculaciones) {
		this.vinculaciones = vinculaciones;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getMatricula() {
		return this.matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public int getCapacidad() {
		return this.capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public TTransporte transfer() {
		TTransporte transporte = new TTransporte();
		transporte.setNombre(nombre);
		transporte.setCapacidad(capacidad);
		transporte.setMatricula(matricula);
		transporte.setId(id_transporte);
		transporte.setActivo(activo);
		return transporte;	
	}

}
