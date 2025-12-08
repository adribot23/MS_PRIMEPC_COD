/**
 * 
 */
package negocio.FacturaJPA;

import java.util.Set;

public class TCarritoFactura {

	private TFactura tFactura;

	private Integer idPaquete;

	private Set<TLineaFactura> tLineasFactura;
	
	private double precioTotal;

	public void set_tFactura(TFactura tFactura) {
		this.tFactura = tFactura;
	}

	public TFactura get_tFactura() {
		return this.tFactura;
	}

	public void set_tLineasFactura(Set<TLineaFactura> tLineasFactura) {
		this.tLineasFactura = tLineasFactura;
	}

	public Set<TLineaFactura> get_tLineasFactura() {
		return this.tLineasFactura;
	}

	public void set_precioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public double get_precioTotal() {
		return this.precioTotal;
	}

	public void set_idPaquete(Integer idPaquete) {
		this.idPaquete = idPaquete;
	}

	public Integer get_idPaquete() {
		return this.idPaquete;
	}
}