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
        this.setId(t.getId());
        this.setNumSerie(t.getNumSerie());
        this.setEstado(t.getEstado());
        this.setPeso(t.getPeso());
        this.setPrecio(t.getPrecio());
        this.setActivo(t.getActivo());

        //this.setRuta(null);
        //this.setFactura(null);

        this.prioridad = t.getPrioridad();
    }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    @Override
    public TPaqueteExpress toTransfer() {
        TPaqueteExpress t = new TPaqueteExpress();
        t.setPrioridad(this.prioridad);
        return t;
    }
}



