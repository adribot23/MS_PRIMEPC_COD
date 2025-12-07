/**
 * 
 */
package negocio.FacturaJPA;

public class TFactura {
	
	private Integer idFactura;

	private Integer idRemitente;

	private Integer activo;

	private double precioNeto;

	private Double precioBruto;

	public TFactura() {
	}
	
	public TFactura(Factura f) {
		this.idFactura = f.get_idFactura();
		this.activo = f.get_activo();
		this.precioBruto = f.get_precioBruto();
		this.precioNeto = f.get_precioNeto();
		this.idRemitente= f.get_Remitente().getId();
	}

	public void set_idRemitente(Integer idR) {
		this.idRemitente=idR;
	}

	public Integer get_idRemitente() {
		return this.idRemitente;
	}

	public void set_activo(Integer activo) {
		this.activo=activo;
	}

	public Integer get_activo() {
		return activo;
	}

	public void set_precioNeto(double precioN) {
		this.precioNeto=precioN;
	}

	public double get_precioNeto() {
		return this.precioNeto;
	}

	public void set_precioBruto(double precioB) {
		this.precioBruto=precioB;
	}

	public double get_precioBruto() {
		return precioBruto;
	}

	public Integer get_idFactura() {
		return idFactura;
	}

	public void set_idFactura(Integer idF) {
		this.idFactura=idF;
	}
}