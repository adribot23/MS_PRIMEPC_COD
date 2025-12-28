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
        super(t);
        this.descuento = t.getDescuento();
    }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    @Override
    public TPaqueteNormal toTransfer() {
    	 TPaqueteNormal t = new TPaqueteNormal();
         t.setDescuento(this.descuento);
         t.setId(this.getId());
         t.setNumSerie(this.getNumSerie());
         t.setEstado(this.getEstado());
         t.setPeso(this.getPeso());
         
         double precioCalculado = t.getPrecio() - descuento;
         double precio = Math.max(0, precioCalculado);
         this.setPrecio(precio);
         
         t.setActivo(this.getActivo());

         return t;
    }
}


