package negocio.TrabajadorJPA;

public class TTrabajador {
	private int id;
	private int DNI;
	
	//getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDNI() {
		return DNI;
	}
	
	//setters
	public void setDNI(int DNI) {
		this.DNI = DNI;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	private String nombre;
	private int activo;

	public int isActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
}
