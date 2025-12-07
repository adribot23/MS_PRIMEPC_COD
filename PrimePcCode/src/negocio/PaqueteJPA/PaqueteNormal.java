package negocio.PaqueteJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "NORMAL")
@PrimaryKeyJoinColumn(name="ID")
public class PaqueteNormal extends Paquete implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7796398680435888103L;
	private double descuento;

    public PaqueteNormal() { }

    public PaqueteNormal(TPaqueteNormal t) {
        this.setId(t.getId());
        this.setNumSerie(t.getNumSerie());
        this.setEstado(t.getEstado());
        this.setPeso(t.getPeso());
        this.setPrecio(t.getPrecio());
        this.setActivo(t.getActivo());

        //this.setRuta(null);
        //this.setFactura(null);
        this.descuento = t.getDescuento();
    }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    @Override
    public TPaqueteNormal toTransfer() {
        TPaqueteNormal t = new TPaqueteNormal();
        t.setDescuento(this.descuento);
        return t;
    }
}


