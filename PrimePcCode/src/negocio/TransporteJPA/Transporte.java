package negocio.TransporteJPA;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.persistence.JoinColumn;

import negocio.TrabajadorJPA.Trabajador;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "matricula") })
@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.TransporteJPA.Transporte.findByMatricula", query = "select t from Transporte t where t.matricula = :matricula"),
		@NamedQuery(name = "negocio.TransporteJPA.Transporte.findAll", query = "select t from Transporte t") })

public class Transporte implements Serializable {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@SequenceGenerator(name = "seq", sequenceName = "TRANSPORTE_SEQ", allocationSize = 1)

	@Id
	@Column(name = "ID")
	private int id_transporte;

	@Version
	private Integer version;
	private String nombre;
	private int capacidad;
	private String matricula;
	@Column(name = "ACTIVO")
	private int activo;

	// No tiene argumentos la relación por lo que no se necesita clase intermedia
	@ManyToMany
	@JoinTable(name = "transporte_trabajador", joinColumns = @JoinColumn(name = "id_transporte"), inverseJoinColumns = @JoinColumn(name = "id_trabajador"))
	private Set<Trabajador> trabajadores;
	
	public Transporte() {
    }
	
	public Transporte(TTransporte t) {
		this.id_transporte = t.getId();
		this.nombre = t.getNombre();
		this.matricula = t.getMatricula();
		this.capacidad = t.getCapacidad();
		this.activo = t.getActivo();
	}

	public int getId() {
		return id_transporte;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int a) {
		this.activo = a;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public Set<Trabajador> getTrabajadores() {
		return this.trabajadores;
	}

	public void setTrabajadores(Set<Trabajador> t) {
		this.trabajadores = t;
	}

	public TTransporte toTransfer() {
		TTransporte transporte = new TTransporte();
		transporte.setNombre(nombre);
		transporte.setCapacidad(capacidad);
		transporte.setMatricula(matricula);
		transporte.setId(id_transporte);
		transporte.setActivo(activo);
		return transporte;
	}

}
