package negocio.PaqueteJPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "NORMAL")
public class PaqueteNormal extends Paquete {

    @Column
    private double descuento;

    public PaqueteNormal() {
        super();
    }

    public PaqueteNormal(TPaqueteNormal tPn) {
        super();
        this.setNumSerie(tPn.getNumSerie());
        this.setEstado(tPn.getEstado());
        this.setPeso(tPn.getPeso());
        this.setPrecio(tPn.getPrecio());
        this.descuento = tPn.getDescuento();
    }

    @Override
    public TPaqueteNormal entityToTransfer() {
        TPaqueteNormal tPn = new TPaqueteNormal();
        tPn.setId(this.getId());
        tPn.setNumSerie(this.getNumSerie());
        tPn.setEstado(this.getEstado());
        tPn.setPeso(this.getPeso());
        tPn.setPrecio(this.getPrecio());
        tPn.setDescuento(this.descuento);
        return tPn;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
