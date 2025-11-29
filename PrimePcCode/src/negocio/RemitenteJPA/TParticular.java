package negocio.RemitenteJPA;

public class TParticular extends TRemitente{

	public String fechaNacimiento;
	
	public TParticular(int id_remitente, int activo, String nombre, String direccion, String telefono, String fechaNacimiento) {
		super(id_remitente, activo, nombre, direccion, telefono);
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
