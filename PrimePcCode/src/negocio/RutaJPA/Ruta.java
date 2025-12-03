package negocio.RutaJPA;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.PaqueteJPA.Paquete;

@Entity
public class Ruta implements Serializable {
	private static final long serialVersionUID = 0;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id_ruta;

	@Version
	private Integer version;
	
	private String origen;
	private String destino;
	private double distancia;
	private int activo;
	
	@OneToMany(mappedBy = "ruta")
	private Set<Paquete> paquetes;
	
	public Ruta() {}
	
	public Ruta(TRuta ruta) {
		
		this.id_ruta = ruta.get_id();
		this.origen = ruta.get_origen();
		this.destino = ruta.get_destino();
		this.distancia = ruta.get_distancia();
		this.activo = ruta.get_activo();
	}
	
	public TRuta entityToTransfer() {
		TRuta tRuta = new TRuta();
		tRuta.set_id(this.id_ruta);
		tRuta.set_origen(this.origen);
		tRuta.set_destino(this.destino);
		tRuta.set_distancia(this.distancia);
		tRuta.set_activo(this.activo);
		return tRuta;
	}
	
	
	public int getId() {
		return this.id_ruta;
	}
	
	public String getOrigen() {
		return this.origen;
	}
	
	public String getDestino() {
		return this.destino;
	}
	
	public double getDistancia() {
		return this.distancia;
	}
	
	
	public int getActivo() {
		return this.activo;
	}
	

	public void setId(int id) {
		this.id_ruta = id;
	}
	
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public Set<Paquete> get_lista_paquetes() {
		return this.paquetes;
	}
	
	public void set_lista_paquetes(Set<Paquete> paquetes) {
		this.paquetes = paquetes;
	}
}
