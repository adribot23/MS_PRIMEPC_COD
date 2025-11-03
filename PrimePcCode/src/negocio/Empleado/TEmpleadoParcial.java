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
public class TEmpleadoParcial extends TEmpleado {

	private int horas_semanales;

	public TEmpleadoParcial(int id, String nombre, String dni, String tlf, int activo, int horas_semanales) {
		super(id, nombre, dni, tlf, activo);
		this.horas_semanales = horas_semanales;
	}

	public TEmpleadoParcial(int id, String nombre, String dni, String tlf, int horas_semanales) {
		super(id, nombre, dni, tlf);
		this.horas_semanales = horas_semanales;
	}

	public TEmpleadoParcial() {
		// TODO Auto-generated constructor stub
	}

	public int getHorasSemanales() {
		return this.horas_semanales;
	}

	public void setHorasSemanales(int horas) {
		this.horas_semanales = horas;
	}

	public String toString() {
		return super.toString() + ",Tipo: Parcial, Horas Semanales: " + this.horas_semanales + "]";

	}
}