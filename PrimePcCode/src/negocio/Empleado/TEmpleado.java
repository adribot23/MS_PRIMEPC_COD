/**
 * 
 */
package negocio.Empleado;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TEmpleado {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated "UML a Java
	 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected int id;
	protected String nombre;
	protected String dni;
	protected String telefono;
	protected int activo;

	public TEmpleado() {
	};

	public TEmpleado(int id, String nombre, String dni, String telefono, int activo) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
		this.activo = activo;

	}

	public TEmpleado(int id, String nombre, String dni, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Empleado [ID: " + id + ", Nombre: " + nombre + ", DNI: " + dni + ", Tlf: " + telefono;
	}
}