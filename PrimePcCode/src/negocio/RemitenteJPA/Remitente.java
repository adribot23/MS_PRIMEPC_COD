package negocio.RemitenteJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;

import java.io.Serializable;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;
import jakarta.persistence.InheritanceType;

import jakarta.persistence.NamedQueries;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findByid", query = "select r from Remitente r where :id_remitente = r.id_remitente"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBydireccion", query = "select r from Remitente r where :direccion = r.direccion"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBynombre", query = "select r from Remitente r where :nombre = r.nombre"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBytelefono", query = "select r from Remitente r where :telefono = r.telefono"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findByactivo", query = "select r from Remitente r where :activo = r.activo"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findByversion", query = "select r from Remitente r where :version = r.version") })
public class Remitente implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@SequenceGenerator(name = "seq", sequenceName = "SEQ_ID", allocationSize = 1)
	@Id
	private int id_remitente;

	private int activo;
	private String direccion;
	private String telefono;
	private String nombre;

	public Remitente() {

	}

	public Remitente(TRemitente remitente) {
		this.id_remitente = remitente.getId();
		this.nombre = remitente.getNombre();
		this.telefono = remitente.getTelefono();
		this.activo = remitente.getActivo();
		this.direccion = remitente.getDireccion();
	}

	public TRemitente entityToTransfer() {

		TRemitente tremitente = new TRemitente();
		tremitente.setId(this.id_remitente);
		tremitente.setNombre(this.nombre);
		tremitente.setActivo(this.activo);
		tremitente.setDireccion(this.direccion);

		return tremitente;

	}

	@Version
	private Integer version;

	public Integer get_version() {
		return version;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
