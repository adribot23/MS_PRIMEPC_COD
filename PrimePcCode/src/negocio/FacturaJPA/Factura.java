/**
 * 
 */
package negocio.FacturaJPA;

import jakarta.persistence.Entity;
import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;
import negocio.RemitenteJPA.Remitente;
import java.util.Set;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByid", query = "select obj from Factura obj where :id = obj.id "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByactivo", query = "select obj from Factura obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByversion", query = "select obj from Factura obj where :version = obj.version "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByprecio_total", query = "select obj from Factura obj where :precio_total = obj.precio_total "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByremitente", query = "select obj from Factura obj where :remitente = obj.remitente "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findBylineaFactura", query = "select obj from Factura obj where :lineaFactura MEMBER OF obj.lineaFactura "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findBypaquete", query = "select obj from Factura obj where :paquete = obj.paquete ") })
public class Factura implements Serializable {

	private static final long serialVersionUID = 0;

	@GeneratedValue
	@Id
	private Integer id;

	private Integer activo;

	private Integer version;

	private double precio_total;

	@OneToMany(mappedBy = "factura")
	private Set<LineaFactura> lineaFactura;

	@ManyToOne
	private Remitente remitente;

	public Factura() {
	}

	public Factura(TFactura tFactura) {
		this.id = tFactura.get_idFactura();
		this.activo = tFactura.get_activo();
		this.precio_total = tFactura.get_precioTotal();
	}

	public TFactura toTransfer() {
		TFactura tFactura = new TFactura();
		tFactura.set_idFactura(id);
		tFactura.set_activo(activo);
		tFactura.set_precioTotal(precio_total);
		return tFactura;
	}

	public void set_precioTotal(double precioT) {
		this.precio_total = precioT;
	}

	public double get_precioTotal() {
		return this.precio_total;
	}

	public Integer get_version() {
		return this.version;
	}

	public void set_activo(Integer activo) {
		this.activo = activo;
	}

	public Integer get_activo() {
		return this.activo;
	}

	public void set_Remitente(Remitente remitente) {
		this.remitente = remitente;
	}

	public Remitente get_Remitente() {
		return this.remitente;
	}

	public void set_lineaFactura(Set<LineaFactura> lineaFactura) {
		this.lineaFactura = lineaFactura;
	}

	public Set<LineaFactura> get_lineaFactura() {
		return this.lineaFactura;
	}

	public Integer get_idFactura() {
		return this.id;
	}

	public void set_idFactura(Integer idFactura) {
		this.id = idFactura;
	}
}