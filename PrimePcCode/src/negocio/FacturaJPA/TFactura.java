package negocio.FacturaJPA;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import negocio.PaqueteJPA.Paquete;
import negocio.RemitenteJPA.Remitente;

public class TFactura {  
    private Integer id;
	private int activo;
	private double precioBruto;
	private double precioNeto;
	private Set<TLineaFactura> lineasFactura;
	
	
	/*
	@ManyToOne
	private TRemitente remitente;
	*/
	
	/*@OneToMany
	private Set<TPaquete> paquetes;
	*/
	
	public TFactura(Factura f) {
		this.id = f.getId();
		this.activo = f.getActivo();
		this.precioBruto = f.getPrecioBruto();
		this.precioNeto = f.getPrecioNeto();
		this.lineasFactura = f.getLineasFactura();
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
	
	
	//hace falta?
	public Set<TPaquete> getPaquetes(){
		
		return paquetes;
	}
	
	public TRemitente getRemitente() {
		
		return remitente;
	}
	
	public Set<TLineaFactura> getLineasFactura(){
	
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
	
	
	//Hace falta tener como atributops paquete y remitente en el transfer?
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
