/**
 * 
 */
package negocio.FacturaJPA;

public class TLineaFactura {

	private Integer idFactura;

	private Integer idPaquete;

	private Integer devuelto;

	private double precio_total;

	public void set_idFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public void set_devuelto(Integer devuelto) {
		this.devuelto = devuelto;
	}

	public Integer get_devuelto() {
		return devuelto;
	}

	public Integer get_idFactura() {
		return this.idFactura;
	}

	public void set_idPaquete(Integer idP) {
		this.idPaquete = idP;
	}

	public Integer get_idPaquete() {
		return this.idPaquete;
	}

	public void set_precioTotal(double precioTotal) {
		this.precio_total = precioTotal;
	}

	public double get_precioTotal() {
		return this.precio_total;
	}

}