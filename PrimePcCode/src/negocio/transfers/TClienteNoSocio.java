package negocio.transfers;

public class TClienteNoSocio extends TCliente {

	private static final long serialVersionUID = 1L;
	protected int numVisitas;

	public TClienteNoSocio() {}
	
	public TClienteNoSocio(String nombre, String dni, int numVisitas) {
		super(nombre, dni);
		this.numVisitas = numVisitas;
	}
	
	public TClienteNoSocio(int id, String nombre, String dni, int numVisitas) {
		super(id, nombre, dni);
		this.numVisitas = numVisitas;
	}
	
	public TClienteNoSocio(int id, String nombre, String dni, int activo, int numVisitas) {
		super(id, nombre, dni, activo);
		this.numVisitas = numVisitas;
	}

	public int getNumVisitas() {
		return this.numVisitas;
	}

	public void setNumVisitas(int numVisitas) {
		this.numVisitas = numVisitas;
	}

	@Override
	public String toString() {
		return super.toString() + ", No socio Numero de visitas: " + numVisitas + "]";
	}

}
