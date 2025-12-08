/**
 * 
 */
package negocio.FacturaJPA;

import java.util.Set;

import negocio.PaqueteJPA.TPaquete;
import negocio.RemitenteJPA.TRemitente;

public class TFacturaTOA {

	private TFactura tFactura;

	private TRemitente tRemitente;

	private Set<TLineaFactura> tLineasFactura;

	public TFacturaTOA() {
	}

	public void set_tFactura(TFactura tFactura) {
		this.tFactura = tFactura;
	}

	public void set_tRemitente(TRemitente tRemitente) {
		this.tRemitente = tRemitente;
	}

	public void set_tLineasFactura(Set<TLineaFactura> tLineasFactura) {
		this.tLineasFactura = tLineasFactura;
	}

	public TFactura get_tFactura() {
		return this.tFactura;
	}

	public TRemitente get_tRemitente() {
		return this.tRemitente;
	}

	public Set<TLineaFactura> get_tLineasFactura() {
		return this.tLineasFactura;
	}
}