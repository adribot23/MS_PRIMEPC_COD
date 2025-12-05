package negocio.RemitenteJPA;

import jakarta.persistence.Entity;
import java.io.Serializable;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.RemitenteJPA.Empresa.findBynumRegistroFiscal", query = "select e from Empresa e where :numRegistroFiscal = e.numRegistroFiscal") })
public class Empresa extends Remitente implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numRegistroFiscal;

	public Empresa(TEmpresa empresa) {
		super(empresa);
		this.numRegistroFiscal = empresa.getNumRegistroFiscal();
	}

	public int getNumRegistroFiscal() {
		return numRegistroFiscal;
	}

	public void setNumRegistroFiscal(int numRegistroFiscal) {
		this.numRegistroFiscal = numRegistroFiscal;
	}

	@Override
	public TRemitente entityToTransfer() {
		TEmpresa empresa = new TEmpresa();
		empresa.setNumRegistroFiscal(this.numRegistroFiscal);

		TRemitente base = super.entityToTransfer();
		empresa.setId(base.getId());
		empresa.setActivo(base.getActivo());
		empresa.setNombre(base.getNombre());
		empresa.setDireccion(base.getDireccion());
		empresa.setTelefono(base.getTelefono());

		return empresa;
	}
}
