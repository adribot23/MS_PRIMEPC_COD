package negocio.FacturaJPA;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import negocio.PaqueteJPA.Paquete;
import negocio.RemitenteJPA.Remitente;

@Entity
public class Factura {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id    
    private Integer id;
	
	@Version
	private Version version;
	
	private int activo;
	private double precioBruto;
	private double precioNeto;
	private Set<LineaFactura> lineasFactura;
	
	
	/*
	@ManyToOne
	private Remitente remitente;
	*/
	
	/*@OneToMany
	private Set<Paquete> paquetes;
	*/
	
	public Factura(TFactura f) {
		this.id = f.getId();
		this.activo = f.getActivo();
		this.precioBruto = f.getPrecioBruto();
		this.precioNeto = f.getPrecioNeto();
		this.lineasFactura = f.getLineasFactura();
	}
	
	public TFactura entityToTransfer() {
		TFactura tFactura = new TFactura();
		tFactura.setID(id);
		tFactura.setActivo(activo);
		tFactura.setPrecioBruto(precioBruto);
		tFactura.setPrecioNeto(precioNeto);
		tFactura.setLineasFactura(lineasFactura);
		
		return tFactura;
	}
	
	public int getId() {
		
		return id;
	}
	
	public int getActivo() {
		
		return activo;
	}
	
	public double getPrecioBruto() {
		
		return precioBruto;
	}
	
	public double getPrecioNeto() {
		
		return precioNeto;
	}
	
	public Set<Paquete> getPaquetes(){
		
		return paquetes;
	}
	
	public Remitente getRemitente() {
		
		return remitente;
	}
	
	public Version getVersion() {
		
		return version;
	}
	
	public Set<LineaFactura> getLineasFactura(){
	
		return this.lineasFactura;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public void setPrecioNeto(double precioNeto) {
		this.precioNeto = precioNeto;
	}
	
	public void setPrecioBruto(double precioBruto) {
		this.precioBruto = precioBruto;
	}
	
	public void setRemitente(Remitente remitente) {
		this.remitente = remitente;
	}
	
	public void setPaquetes(Set<Paquete> paquetes) {
		this.paquetes = paquetes;
	}
	
	public void setLineasFactura(Set<LineaFactura> lineas) {
		this.lineasFactura = lineas;
	}
}
