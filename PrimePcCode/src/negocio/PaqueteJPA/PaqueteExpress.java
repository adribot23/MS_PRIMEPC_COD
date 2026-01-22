package negocio.PaqueteJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "EXPRESS")
@PrimaryKeyJoinColumn(name="ID")
public class PaqueteExpress extends Paquete implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8311998917298328752L;
	private int prioridad;

    public PaqueteExpress() { }

    public PaqueteExpress(TPaqueteExpress t) {
    	super(t);
        this.prioridad = t.getPrioridad();
    }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }
   
    @Override
	public double calculaPrecioFinal() {
		return this.getPrecio() + prioridad * 5; //se le añade 5 euros por cada grado de prioridad
	}

    @Override
    public TPaqueteExpress toTransfer() {
    	
        TPaqueteExpress t = new TPaqueteExpress();
        t.setPrioridad(this.prioridad);
        t.setId(this.getId());
        t.setNumSerie(this.getNumSerie());
        t.setEstado(this.getEstado());
        t.setPeso(this.getPeso());
        t.setPrecio(this.getPrecio());
        t.setActivo(this.getActivo());

        return t;
    }

	
}



