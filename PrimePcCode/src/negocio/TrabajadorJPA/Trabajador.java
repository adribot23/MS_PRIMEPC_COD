package negocio.TrabajadorJPA;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import negocio.RutaJPA.VinculacionRutaTrabajador;
import negocio.TransporteJPA.Transporte;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "DNI") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.TrabajadorJPA.Trabajador.findByid", query = "select t from Trabajador t where :id_trabajador = t.id_trabajador"),
		@NamedQuery(name = "Negocio.TrabajadorJPA.Trabajador.findByDNI", query = "select t from Trabajador t where :DNI = t.DNI"),
		@NamedQuery(name = "Negocio.TrabajadorJPA.Trabajador.findBynombre", query = "select t from Trabajador t where :nombre = t.nombre"),
		@NamedQuery(name = "Negocio.TrabajadorJPA.Trabajador.findByactivo", query = "select t from Trabajador t where :activo = t.activo"),
		@NamedQuery(name = "Negocio.TrabajadorJPA.Trabajador.findByversion", query = "select t from Trabajador t where :version = t.version"),
		@NamedQuery(name = "negocio.TrabajadorJPA.Trabajador.findAll", query = "select t from Trabajador t") })
public class Trabajador {

	private static final long serialVersionUID = 0;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trabajador")
	@SequenceGenerator(name = "seq_trabajador", sequenceName = "TRABAJADOR_SEQ", allocationSize = 1)

	@Id
	@Column(name = "ID")
	private int id_trabajador;
	@Column(unique = true)
	private String DNI;
	private String nombre;
	@Column(name = "ACTIVO")
	private int activo;

	@ManyToMany(mappedBy = "trabajadores")
	@JoinTable(name = "trabajador_transporte", joinColumns = @JoinColumn(name = "id_transporte"), inverseJoinColumns = @JoinColumn(name = "id_trabajador"))
	private Set<Transporte> transportes;

	@OneToMany(mappedBy = "trabajador")
	private Set<VinculacionRutaTrabajador> vinculaciones;
	@Version
	private Integer version;

	public Trabajador() {
	}

	public Trabajador(TTrabajador t) {
		this.id_trabajador = t.getId();
		this.DNI = t.getDNI();
		this.nombre = t.getNombre();
		this.activo = t.isActivo();
	}

	public TTrabajador entityToTransfer() {
		TTrabajador tTrabajador = new TTrabajador();
		tTrabajador.setId(this.id_trabajador);
		tTrabajador.setDNI(DNI);
		tTrabajador.setNombre(nombre);
		tTrabajador.setActivo(activo);
		return tTrabajador;
	}

	// getters
	public int getId() {
		return id_trabajador;
	}

	public void setId(int id) {
		this.id_trabajador = id;
	}

	public String getDNI() {
		return DNI;
	}

	public int getActivo() {
		return activo;
	}

	// setters
	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Set<Transporte> getTransportes() {
		return transportes;
	}

	public Set<VinculacionRutaTrabajador> get_vinculaciones() {
		return this.vinculaciones;
	}

	public void set_vinculaciones(Set<VinculacionRutaTrabajador> vinculaciones) {
		this.vinculaciones = vinculaciones;
	}

}
