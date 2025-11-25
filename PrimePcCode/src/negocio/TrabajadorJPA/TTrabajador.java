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
	public void setDNI(int dNI) {
		DNI = dNI;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	private String nombre;
	
}
