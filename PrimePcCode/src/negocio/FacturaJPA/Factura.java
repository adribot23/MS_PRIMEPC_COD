/**
 * 
 */
package negocio.FacturaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import negocio.RemitenteJPA.Remitente;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByid", query = "select obj from Factura obj where :id = obj.id "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByactivo", query = "select obj from Factura obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByversion", query = "select obj from Factura obj where :version = obj.version "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByprecioNeto", query = "select obj from Factura obj where :precioNeto = obj.precioNeto "),
		@NamedQuery(name = "negocio.FacturaJPA.Factura.findByprecioBruto", query = "select obj from Factura obj where :precioBruto = obj.precioBruto "),
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

	private double precioNeto;

	private double precioBruto;

	@OneToMany(mappedBy = "factura")
	private Set<LineaFactura> lineaFactura;

	@ManyToOne
	private Remitente remitente;

	public Factura() {
	}

	public Factura(TFactura tFactura) {
		this.id = tFactura.get_idFactura();
		this.activo = tFactura.get_activo();
		this.precioBruto = tFactura.get_precioBruto();
		this.precioNeto = tFactura.get_precioNeto();
	}

	public TFactura toTransfer() {
		TFactura tFactura = new TFactura();
		tFactura.set_idFactura(id);
		tFactura.set_activo(activo);
		tFactura.set_precioBruto(precioBruto);
		tFactura.set_precioNeto(precioNeto);
		return tFactura;
	}

	public void set_precioNeto(double precioN) {
		this.precioNeto = precioN;
	}

	public double get_precioNeto() {
		return this.precioNeto;
	}

	public void set_precioBruto(double precioB) {
		this.precioBruto = precioB;
	}

	public double get_precioBruto() {
		return this.precioBruto;
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