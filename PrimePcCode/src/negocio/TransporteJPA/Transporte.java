package negocio.TransporteJPA;

public class Transporte {
	
	private int id_transporte;
	
	private String nombre;
	
	private int capacidad;
	
	private String matricula;
	
	private int activo;

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

}
