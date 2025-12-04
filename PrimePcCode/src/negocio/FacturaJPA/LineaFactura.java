/**
 * 
 */
package negocio.FacturaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQuery;

import negocio.RemitenteJPA.Remitente;

import javax.persistence.NamedQueries;
import javax.persistence.ManyToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByid", query = "select obj from LineaFactura obj where :id = obj.id "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findBydevuelto", query = "select obj from LineaFactura obj where :devuelto = obj.devuelto "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByprecioNeto", query = "select obj from LineaFactura obj where :precioNeto = obj.precioNeto "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByprecioBruto", query = "select obj from LineaFactura obj where :precioBruto = obj.precioBruto "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByversion", query = "select obj from LineaFactura obj where :version = obj.version "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByfactura", query = "select obj from LineaFactura obj where :factura = obj.factura ") })
public class LineaFactura implements Serializable {

	private static final long serialVersionUID = 0;

	@EmbeddedId
	private LineaFacturaID id;
	
	private Integer devuelto;
	
	private double precioNeto;
	
	private double precioBruto;

	private Integer version;
	
	@ManyToOne
	private Factura factura;

	public LineaFactura() {
	}

	public LineaFactura(TLineaFactura tLineaFactura) {
		this.devuelto=tLineaFactura.get_devuelto();
		this.precioNeto=tLineaFactura.get
	}

	public LineaFactura(Factura factura, Remitente remitente) {
		
	}
	
	public Integer get_devuelto() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	public void set_devuelto(Integer devuelto) {
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

	public void set_precioNeto(double precioNeto) {
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

	public void set_precioBruto(double precioBruto) {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	public TFactura get_tFactura() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}
}