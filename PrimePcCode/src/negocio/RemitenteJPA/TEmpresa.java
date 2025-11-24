package negocio.RemitenteJPA;

public class TEmpresa extends TRemitente {

	private int numRegistroFiscal;
	
	public TEmpresa(int id_remitente, int activo, String direccion, String telefono, int numRegistroFiscal) {
		super(id_remitente, activo, direccion, telefono);
		this.numRegistroFiscal = numRegistroFiscal;
	}

	public int getNumRegistroFiscal() {
		return numRegistroFiscal;
	}

	public void setNumRegistroFiscal(int numRegistroFiscal) {
		this.numRegistroFiscal = numRegistroFiscal;
	}
}
