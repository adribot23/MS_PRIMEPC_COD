package negocio.PaqueteJPA;

public class TPaqueteNormal extends TPaquete{
	double descuento;
	
	double getDescuento(){
		return descuento;
	}
	
	void setDescuento(double descuento) {
		this.descuento = descuento;
	}
}
