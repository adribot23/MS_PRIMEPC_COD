/**
 * 
 */
package negocio.FacturaJPA;

import java.io.Serializable;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class LineaFacturaID implements Serializable {

	private static final long serialVersionUID = 0;

	private Integer idFactura;

	private Integer idPaquete;

	public LineaFacturaID() {
	}

	public LineaFacturaID(Integer factura, Integer paquete) {
		this.idFactura = factura;
		this.idPaquete = paquete;
	}

	public Integer get_idFactura() {
		return idFactura;
	}

	public void set_idFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Integer get_idPaquete() {
		return idPaquete;
	}

	public void set_idPaquete(Integer idPaquete) {
		this.idPaquete = idPaquete;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof LineaFacturaID))
			return false;
		LineaFacturaID pk = (LineaFacturaID) obj;
		if ((idFactura == null && pk.idFactura != null) || (idFactura != null && !idFactura.equals(pk.idFactura)))
			return false;
		if ((idPaquete == null && pk.idPaquete != null) || (idPaquete != null && !idPaquete.equals(pk.idPaquete)))
			return false;
		return true;
	}

	private UUID uuid;

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		if (idFactura != null) {
			hash = hash * prime + idFactura.hashCode();
		}
		if (idPaquete != null) {
			hash = hash * prime + idPaquete.hashCode();
		}
		if (hash == 17) {
			if (uuid == null) {
				uuid = UUID.randomUUID();
			}
			hash = uuid.hashCode();
		}
		return hash;
	}
}