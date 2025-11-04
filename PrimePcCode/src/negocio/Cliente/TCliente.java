/**
 * 
 */
package negocio.Cliente;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author adria
 * @generated "UML a Java
 *            (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TCliente {
	protected int id;
	protected String nombre;
	protected String dni;
	protected int activo;

	public TCliente() {
	};

	public TCliente(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	public TCliente(int id, String nombre, String dni) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
	}

	public TCliente(int id, String nombre, String dni, int activo) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.activo = activo;
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

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Cliente [ID: " + id + ", Nombre: " + nombre + ", DNI: " + dni;
	}
}