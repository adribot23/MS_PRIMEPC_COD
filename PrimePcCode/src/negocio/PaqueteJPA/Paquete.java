package negocio.PaqueteJPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import negocio.RutaJPA.Ruta;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;
//import negocio.FacturaJPA.Factura;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(
        name = "negocio.PaqueteJPA.Paquete.findAll",
        query = "SELECT p FROM Paquete p"
    ),
    /*
    @NamedQuery(
        name = "negocio.PaqueteJPA.Paquete.findByFactura",
        query = "SELECT p FROM Paquete p WHERE p.id_factura = :idFactura"
    ),
    */
    @NamedQuery(
        name = "negocio.PaqueteJPA.Paquete.findByRuta",
        query = "SELECT p FROM Paquete p WHERE p.id_ruta = :idRuta"
    ),
    
    @NamedQuery(
            name = "Paquete.findByNumSerie",
            query = "SELECT p FROM Paquete p WHERE p.numero_serie = :numSerie"
        )
})
public abstract class Paquete implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7462137024041982342L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paquete")
    @SequenceGenerator(name = "seq_paquete", sequenceName = "PAQUETE_SEQ", allocationSize = 1)
    @Id
    private Integer id;
	@Column(unique = true)
    private String numero_serie;
    private String estado;
    private double peso;
    private double precio;
    private int activo;
    
    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private Ruta ruta;
    /*
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;
	*/
    @Version
    private Integer version;

    public Paquete() {}

    // GETTERS
    public Integer getId() { return id; }
    public String getNumSerie() { return numero_serie; }
    public String getEstado() { return estado; }
    public double getPeso() { return peso; }
    public double getPrecio() { return precio; }
    public int getActivo() { return activo; }
    public Ruta getRuta() { return ruta; }
    //public Factura getFactura() { return factura; }


    public void setId(Integer id) { this.id = id; }
    public void setNumSerie(String numSerie) { this.numero_serie = numSerie; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setActivo(int activo) { this.activo = activo; }
    public void setRuta(Ruta ruta) { this.ruta = ruta; }
    //public void setFactura(Factura factura) { this.factura = factura; }


    public TPaquete entityToTransfer() {

        TPaquete t;

        if (this instanceof PaqueteNormal) {
            t = ((PaqueteNormal) this).toTransfer();
        }
        else if (this instanceof PaqueteExpress) {
            t = ((PaqueteExpress) this).toTransfer();
        }
        else {
            t = new TPaquete(); // nunca debería pasar, pero por seguridad
        }

        t.setId(this.id);
        t.setNumSerie(this.numero_serie);
        t.setEstado(this.estado);
        t.setPeso(this.peso);
        t.setPrecio(this.precio);
        t.setActivo(this.activo);

        t.setIdRuta(ruta != null ? ruta.getId() : -1);
        //t.setIdFactura(factura != null ? factura.getId() : -1);

        return t;
    }
    
    public abstract TPaquete toTransfer();

}

