/**
 * 
 */
package negocio.FacturaJPA;

public class TLineaFactura {

	private Integer idFactura;

	private Integer idPaquete;

	private Integer devuelto;

	private double precioBruto;

	private double precioNeto;

	public void set_idFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public void set_devuelto(Integer devuelto) {
		this.devuelto = devuelto;
	}

	public Integer get_devuelto() {
		return devuelto;
	}

	public Integer get_idFactura() {
		return this.idFactura;
	}

	public void set_idPaquete(Integer idP) {
		this.idPaquete = idP;
	}

	public Integer get_idPaquete() {
		return this.idPaquete;
	}

	public void set_precioBruto(double precioBruto) {
		this.precioBruto = precioBruto;
	}

	public double get_precioBruto() {
		return this.precioBruto;
	}

	public void set_precioNeto(double precioNeto) {
		this.precioNeto = precioNeto;
	}

	public double get_precioNeto() {
		return this.precioNeto;
	}
}