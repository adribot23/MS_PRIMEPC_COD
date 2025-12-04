/**
 * 
 */
package negocio.FacturaJPA;

public class TFactura {
	
	private Integer idFactura;

	private Integer idRemitente;

	private Integer activo;

	private double precioNeto;

	private Double precioBruto;

	public TFactura() {
	}
	
	public TFactura(Factura f) {
		this.idFactura = f.get_idFactura();
		this.activo = f.get_activo();
		this.precioBruto = f.get_precioBruto();
		this.precioNeto = f.get_precioNeto();
		this.lineaFactura = f.get_lineaFactura();
	}

	public void set_idRemitente(Integer idR) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public Integer get_idRemitente() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_activo(Integer activo) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public Integer get_activo() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_precioNeto(double precioN) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public double get_precioNeto() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return 0;
		// end-user-code
	}

	public void set_precioBruto(double precioB) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public double get_precioBruto() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return 0;
		// end-user-code
	}

	public Integer get_idFactura() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_idFactura(Integer idF) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}
}