/**
 * 
 */
package negocio.FacturaJPA;

public class TFactura {
	
	private Integer idFactura;

	private Integer idRemitente;

	private Integer activo;

	private Double precio_total;

	public TFactura() {
	}
	
	public TFactura(Factura f) {
		this.idFactura = f.get_idFactura();
		this.activo = f.get_activo();
		this.precio_total = f.get_precioTotal();
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

	public void set_precioTotal(double precioT) {
		this.precio_total=precioT;
	}

	public double get_precioTotal() {
		return this.precio_total;
	}

	public Integer get_idFactura() {
		return idFactura;
	}

	public void set_idFactura(Integer idF) {
		this.idFactura=idF;
	}
}