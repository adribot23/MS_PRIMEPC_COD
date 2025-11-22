package negocio.RemitenteJPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.persistence.InheritanceType;


import javax.persistence.NamedQueries;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Remitente.findByid",
        query = "select r from Remitente r where :id_remitente = r.id_remitente"
    ),
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Remitente.findBydireccion",
        query = "select r from Remitente r where :direccion = r.direccion"
    ),
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Remitente.findBytelefono",
        query = "select r from Remitente r where :telefono = r.telefono"
    ),
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Remitente.findByactivo",
        query = "select r from Remitente r where :activo = r.activo"
    ),
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Remitente.findByversion",
        query = "select r from Remitente r where :version = r.version"
    )
})
public class Remitente implements Serializable{

	private static final long serialVersionUID = 0;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private int id_remitente;
    
	private int activo;
	private String direccion;
	private String telefono;
	
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
	
}
