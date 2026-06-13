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
public class TClienteSocio extends TCliente {
	private int numero_socio;
	private int puntos;

	public TClienteSocio() {
	}

	public TClienteSocio(int id, String nombre, String dni, int puntos) {
		super(id, nombre, dni);
		this.puntos = puntos;
	}

	public TClienteSocio(String nombre, String dni, int puntos) {
		super(nombre, dni);
		this.puntos = puntos;
	}

	public TClienteSocio(int id, String nombre, String dni, int activo, int num_socio, int puntos) {
		super(id, nombre, dni, activo);
		this.numero_socio = num_socio;
		this.puntos = puntos;
	}

	public int getNumeroDeSocio() {
		return this.numero_socio;
	}

	public void setNumeroSocio(int numero_socio) {
		this.numero_socio = numero_socio;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return super.toString() + ", Socio con numero : " + numero_socio + ", Puntos: " + puntos + "]";
	}
}