package negocio.RutaJPA;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import negocio.TrabajadorJPA.Trabajador;

@Entity

@NamedQueries({
	@NamedQuery(name = "negocio.RutaJPA.VinculacionRutaTrabajador.findByid_ruta", query = "select obj from VinculacionRutaTrabajador obj where :id_ruta = obj.ruta"),
	@NamedQuery(name = "negocio.TrabajadorJPA.VinculacionRutaTrabajador.findByid_trabajador", query = "select obj from VinculacionRutaTrabajador obj where :id_trabajador = obj.trabajador") })
public class VinculacionRutaTrabajador implements Serializable {

private static final long serialVersionUID = 0;

@EmbeddedId
private VinculacionRutaTrabajadorID id_vinculacion;

@Version
private Integer version;

@ManyToOne
@MapsId
private Trabajador trabajador;

@ManyToOne
@MapsId
private Ruta ruta;

private String hora_salida;
private String estado;
private String fecha_asignacion;

public VinculacionRutaTrabajador() {
}

public VinculacionRutaTrabajador(Ruta ruta, Trabajador trabajador, String hora_salida, String estado, String fecha_asignacion) {
	this.id_vinculacion = new VinculacionRutaTrabajadorID(ruta.getId(), trabajador.getId());
	this.ruta = ruta;
	this.trabajador = trabajador;
	this.hora_salida = hora_salida;
	this.estado = estado;
	this.fecha_asignacion = fecha_asignacion;
}

public Trabajador get_trabajador() {
	return this.trabajador;
}

public Ruta get_ruta() {
	return this.ruta;
}

public String get_hora_salida() {
	return this.hora_salida;
}


public String get_estado() {
	return this.estado;
}

public String get_fecha_asignacion() {
	return this.fecha_asignacion;
}

public void set_trabajador(Trabajador trabajador) {
	this.trabajador = trabajador;
}

public void set_ruta(Ruta ruta) {
	this.ruta = ruta;
}

public void set_hora_salida(String hora_salida) {
	this.hora_salida = hora_salida;
}

public void set_estado(String estado) {
	this.estado = estado;
}

public void set_fecha_asignacion(String fecha_asignacion) {
	this.fecha_asignacion = fecha_asignacion;
}


public TVinculacionRutaTrabajador toTransfer() {

	TVinculacionRutaTrabajador vinculacion = new TVinculacionRutaTrabajador();

	vinculacion.set_id_ruta(this.ruta.getId());
	vinculacion.set_id_trabajador(this.trabajador.getId());
	vinculacion.set_hora_salida(this.hora_salida);
	vinculacion.set_estado(this.estado);
	vinculacion.set_fecha_asignacion(this.fecha_asignacion);

	return vinculacion;
}

}
