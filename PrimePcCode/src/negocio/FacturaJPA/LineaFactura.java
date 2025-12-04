/**
 * 
 */
package negocio.FacturaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQuery;

import jakarta.persistence.OneToOne;
import negocio.PaqueteJPA.Paquete;
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

	@OneToOne
	private Paquete paquete;

	public LineaFactura() {
	}

	public LineaFactura(TLineaFactura tLineaFactura) {
		this.devuelto = tLineaFactura.get_devuelto();
		this.precioNeto = tLineaFactura.get_precioNeto();
		this.precioBruto = tLineaFactura.get_precioBruto();
		this.id = new LineaFacturaID(tLineaFactura.get_idFactura(), tLineaFactura.get_idPaquete());
	}

	public LineaFactura(Factura factura, Paquete paquete) {
		this.id = new LineaFacturaID(factura.get_idFactura(), paquete.getId());
		this.paquete = paquete;
		this.factura = factura;
	}

	public Integer get_devuelto() {
		return this.devuelto;
	}

	public void set_devuelto(Integer devuelto) {
		this.devuelto = devuelto;
	}

	public double get_precioNeto() {
		return this.precioNeto;
	}

	public void set_precioNeto(double precioNeto) {
		this.precioNeto = precioNeto;
	}

	public double get_precioBruto() {
		return this.precioBruto;
	}

	public void set_precioBruto(double precioBruto) {
		this.precioBruto = precioBruto;
	}

	public Factura get_factura() {
		return this.factura;
	}

	public void set_factura(Factura factura) {
		this.factura = factura;
	}

	public Paquete get_Paquete() {
		return this.paquete;
	}

	public void set_Paquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public LineaFacturaID get_LineaFacturaID() {
		return this.id;
	}

	public void set_lineaFacturaID(LineaFacturaID id) {
		this.id = id;
	}

	public Integer get_version() {
		return this.version;
	}
}