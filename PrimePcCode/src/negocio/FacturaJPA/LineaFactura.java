/**
 * 
 */
package negocio.FacturaJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.NamedQuery;

import jakarta.persistence.OneToOne;
import negocio.PaqueteJPA.Paquete;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.ManyToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByid", query = "select obj from LineaFactura obj where :id = obj.id "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findBydevuelto", query = "select obj from LineaFactura obj where :devuelto = obj.devuelto "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByprecioTotal", query = "select obj from LineaFactura obj where :precio_total = obj.precio_total "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByversion", query = "select obj from LineaFactura obj where :version = obj.version "),
		@NamedQuery(name = "negocio.FacturaJPA.LineaFactura.findByfactura", query = "select obj from LineaFactura obj where :factura = obj.factura ") })
public class LineaFactura implements Serializable {

	private static final long serialVersionUID = 0;

	@EmbeddedId
	private LineaFacturaID id;

	private Integer devuelto;

	private double precio_total;

	private Integer version;

	@ManyToOne
	@JoinColumn(name="id_factura")
	private Factura factura;

	@OneToOne
	private Paquete paquete;

	public LineaFactura() {
	}

	public LineaFactura(TLineaFactura tLineaFactura) {
		this.devuelto = tLineaFactura.get_devuelto();
		this.precio_total = tLineaFactura.get_precioTotal();
		this.id = new LineaFacturaID(tLineaFactura.get_idFactura(), tLineaFactura.get_idPaquete());
	}

	public LineaFactura(Factura factura, Paquete paquete) {
		this.id = new LineaFacturaID(factura.get_idFactura(), paquete.getId());
		this.paquete = paquete;
		this.factura = factura;
	}

	public TLineaFactura toTransfer() {
        TLineaFactura t = new TLineaFactura();
        t.set_idFactura(id.get_idFactura());
        t.set_devuelto(devuelto);
        t.set_idPaquete(id.get_idPaquete());
        t.set_precioTotal(precio_total);

        return t;
    }
	
	public Integer get_devuelto() {
		return this.devuelto;
	}

	public void set_devuelto(Integer devuelto) {
		this.devuelto = devuelto;
	}

	public double get_precioTotal() {
		return this.precio_total;
	}

	public void set_precioTotal(double precioTotal) {
		this.precio_total = precioTotal;
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