package negocio.RemitenteJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Empresa.findBynumRegistroFiscal",
        query = "select e from Empresa e where :numRegistroFiscal = e.numRegistroFiscal"
    )
})
public class Empresa extends Remitente implements Serializable {

	private int numRegistroFiscal;
	
	public int getNumRegistroFiscal() {
		return numRegistroFiscal;
	}

	public void setNumRegistroFiscal(int numRegistroFiscal) {
		this.numRegistroFiscal = numRegistroFiscal;
	}
	
}
