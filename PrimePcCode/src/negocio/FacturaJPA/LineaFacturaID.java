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

	private Object devuelto;

	private Integer paquete;

	public LineaFacturaID() {
		// begin-user-code
		// TODO Ap�ndice de constructor generado autom�ticamente
		// end-user-code
	}

	public LineaFacturaID(Integer factura, Integer paquete) {
		// begin-user-code
		// TODO Ap�ndice de constructor generado autom�ticamente
		// end-user-code
	}

	public Integer get_idFactura() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_idFactura(Integer idFactura) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public Integer get_idPaquete() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_idPaquete(Integer idPaquete) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof LineaFacturaID))
			return false;
		LineaFacturaID pk = (LineaFacturaID) obj;
		if ((idFactura == null && pk.idFactura != null) || (idFactura != null && !idFactura.equals(pk.idFactura)))
			return false;
		if ((devuelto == null && pk.devuelto != null) || (devuelto != null && !devuelto.equals(pk.devuelto)))
			return false;
		if ((paquete == null && pk.paquete != null) || (paquete != null && !paquete.equals(pk.paquete)))
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
		if (devuelto != null) {
			hash = hash * prime + devuelto.hashCode();
		}
		if (paquete != null) {
			hash = hash * prime + paquete.hashCode();
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