package negocio.PaqueteJPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPRESS")
public class PaqueteExpress extends Paquete {

    @Column
    private int prioridad;

    public PaqueteExpress() {
        super();
    }

    public PaqueteExpress(TPaqueteExpress tPe) {
        super();
        this.setNumSerie(tPe.getNumSerie());
        this.setEstado(tPe.getEstado());
        this.setPeso(tPe.getPeso());
        this.setPrecio(tPe.getPrecio());
        this.prioridad = tPe.getPrioridad();
    }

    @Override
    public TPaqueteExpress entityToTransfer() {
        TPaqueteExpress tPe = new TPaqueteExpress();
        tPe.setId(this.getId());
        tPe.setNumSerie(this.getNumSerie());
        tPe.setEstado(this.getEstado());
        tPe.setPeso(this.getPeso());
        tPe.setPrecio(this.getPrecio());
        tPe.setPrioridad(this.prioridad);
        return tPe;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}

