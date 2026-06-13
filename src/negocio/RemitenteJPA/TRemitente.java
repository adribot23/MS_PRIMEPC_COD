package negocio.RemitenteJPA;

public class TRemitente {

	private int id_remitente;
	private int id_factura;
	private int activo;
	private String direccion;
	private String telefono;
	private String nombre;

	public TRemitente() {}
	
	public TRemitente(int id_remitente, int activo, String nombre, String direccion, String telefono) {
		this.id_remitente = id_remitente;
		this.activo = activo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		//this.id_factura = id_factura;
	}

	public TRemitente(Remitente remitente) {
		this.id_remitente = remitente.getId();
		this.activo = remitente.getActivo();
		this.direccion = remitente.getDireccion();
		this.telefono = remitente.getTelefono();
		//this.id_factura = remitente.getIdFactura();
	}

	public int getId() { return id_remitente; }
	public int getIdFactura() { return this.id_factura; }
	public int getActivo() { return activo; }
	public String getNombre() { return this.nombre; }
	public String getDireccion() { return direccion; }
	public String getTelefono() { return telefono; }

	public void setId(int id_remitente) { this.id_remitente = id_remitente; }
	public void setActivo(int activo) { this.activo = activo; }
	public void setDireccion(String direccion) { this.direccion = direccion; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	public void setIdFactura(int idFactura) { this.id_factura = idFactura; }


}
