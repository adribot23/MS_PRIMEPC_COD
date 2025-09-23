package negocio.transfers;

import java.io.Serializable;

public class TAlmacen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected int id;
	protected String nombre;
	protected int capacidadMaxima;
	protected int ocupacion;
	protected int activo;
	
	public TAlmacen(){};
	public TAlmacen(String nombre, int capacidadMaxima, int ocupacion){
		this.nombre = nombre;
		this.capacidadMaxima = capacidadMaxima;
		this.ocupacion = ocupacion;
	}
	
	public TAlmacen(int id, String nombre, int capacidadMaxima, int ocupacion){
		this.id = id;
		this.nombre = nombre;
		this.capacidadMaxima = capacidadMaxima;
		this.ocupacion = ocupacion;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getCapacidadMaxima(){
		return this.capacidadMaxima;
	}
	
	public void setCapacidadMaxima(int capacidadMaxima){
		this.capacidadMaxima = capacidadMaxima;
	}
	
	public int getOcupacion(){
		return this.ocupacion;
	}
	
	public void setOcupacion(int ocupacion){
		this.ocupacion = ocupacion;
	}
	
	public int getActivo(){
		return this.activo;
	}
	
	public void setActivo(int activo){
		this.activo = activo;
	}
	
	@Override
    public String toString() {
        return "Almacen [ID: " + id + ", Nombre: " + nombre + ", Capacidad Maxima : " + capacidadMaxima + ", Ocupacion: " + ocupacion+"]";
    }
	
}
