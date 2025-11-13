package negocio.TransporteJPA;

public class TTransporte {

	private int id_transporte;
	
	private String nombre;
	
	private int capacidad;
	
	private String matricula;
	
	
	
	public int getId() {
		return id_transporte;
	}
	
	public void setId(int id) {
		this.id_transporte = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
