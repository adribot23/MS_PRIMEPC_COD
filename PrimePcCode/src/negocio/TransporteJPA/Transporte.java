package negocio.TransporteJPA;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "matricula")})
@Entity
@NamedQueries({
	@NamedQuery(name = "negocio.TransporteJPA.Transporte.findByMatricula", query = "select t from Transporte t where t.matricula = :matricula"),
	@NamedQuery(name = "negocio.TransporteJPA.Transporte.findAll", query = "select t from Transporte t")
})

public class Transporte implements Serializable {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id_transporte;
	
	@Version
	private Integer version;	
	private String nombre;	
	private int capacidad;	
	private String matricula;	
	private int activo;
	
	private Set<VinculacionTransporteTrabajador> vinculaciones;

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

	public Set<VinculacionTransporteTrabajador> getVinculaciones() {
		return this.vinculaciones;
	}
	
	public void setvinculaciones(Set<VinculacionTransporteTrabajador> vinculaciones) {
		this.vinculaciones = vinculaciones;
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
