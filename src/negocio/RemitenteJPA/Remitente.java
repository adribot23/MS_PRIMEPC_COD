package negocio.RemitenteJPA;

import java.io.Serializable;
// import java.util.Set;   // Ya NO hace falta si quitamos facturas
import java.util.Set;

import jakarta.persistence.*;
import negocio.FacturaJPA.Factura;

@Entity
@Table(name = "REMITENTE")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBynombre", query = "select r from Remitente r where r.nombre = :nombre"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBydireccion", query = "select r from Remitente r where r.direccion = :direccion"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findBytelefono", query = "select r from Remitente r where r.telefono = :telefono"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findByactivo", query = "select r from Remitente r where r.activo = :activo"),
		@NamedQuery(name = "Negocio.RemitenteJPA.Remitente.findByversion", query = "select r from Remitente r where r.version = :version") })
public class Remitente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_remitente")
	@SequenceGenerator(name = "seq_remitente", sequenceName = "REMITENTE_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private int id_remitente;

	private String nombre;
	private String direccion;
	private String telefono;

	@Column(name = "ACTIVO")
	private int activo;

	@Version
	private Integer version;
	
	@OneToMany(mappedBy = "remitente")
	private Set<Factura> facturas;


	public Remitente() {
	}

	public Remitente(TRemitente t) {
		// this.id_remitente = t.getId();
		this.nombre = t.getNombre();
		this.direccion = t.getDireccion();
		this.telefono = t.getTelefono();
		this.activo = t.getActivo();
	}

	public TRemitente entityToTransfer() {
		TRemitente t = new TRemitente();
		t.setId(id_remitente);
		t.setNombre(nombre);
		t.setDireccion(direccion);
		t.setTelefono(telefono);
		t.setActivo(activo);
		return t;
	}

	public int getId() {
		return id_remitente;
	}

	public void setId(int id) {
		this.id_remitente = id;
	}

	public String getNombre() {
		return nombre;
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

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Integer getVersion() {
		return version;
	}
	
    public Set<Factura> getFactura() { 
    	return facturas;
    }
	
    public void setFactura(Set<Factura> factura){ 
    	this.facturas = factura; 
    }


}