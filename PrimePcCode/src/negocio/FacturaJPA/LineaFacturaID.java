/**
 * 
 */
package negocio.FacturaJPA;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class LineaFacturaID implements Serializable {

	private static final long serialVersionUID = 0;

	private Integer factura;

	private Integer paquete;

	public LineaFacturaID() {
	}

	public LineaFacturaID(Integer factura, Integer paquete) {
		this.factura = factura;
		this.paquete = paquete;
	}

	public Integer get_idFactura() {
		return factura;
	}

	public void set_idFactura(Integer idFactura) {
		this.factura = idFactura;
	}

	public Integer get_idPaquete() {
		return paquete;
	}

	public void set_idPaquete(Integer idPaquete) {
		this.paquete = idPaquete;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof LineaFacturaID))
			return false;
		LineaFacturaID pk = (LineaFacturaID) obj;
		if ((factura == null && pk.factura != null) || (factura != null && !factura.equals(pk.factura)))
			return false;
		if ((paquete == null && pk.paquete != null) || (paquete != null && !paquete.equals(pk.paquete)))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + factura;
		hash = hash * prime + paquete;
		return hash;
	}
}