package negocio.RemitenteJPA;

public class TRemitente {

	
	private int id_remitente;
	private int activo;
	private String direccion;
	private String telefono;
	private String nombre;
	
	public TRemitente(int id_remitente, int activo, String nombre, String direccion, String telefono) {
		super();
		this.id_remitente = id_remitente;
		this.activo = activo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	
	public TRemitente(Remitente remitente) {
		this.id_remitente = remitente.getId();
		this.activo = remitente.getActivo();
		this.direccion = remitente.getDireccion();
		this.telefono = remitente.getTelefono();
	}
	

	public int getId() {
		return id_remitente;
	}
	public void setId(int id_remitente) {
		this.id_remitente = id_remitente;
	}
	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public TRemitente() {
		
	}
	
}
