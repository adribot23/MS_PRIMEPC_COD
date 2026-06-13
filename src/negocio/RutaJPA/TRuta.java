package negocio.RutaJPA;

public class TRuta {

	private int id_ruta;
	private String origen;
	private String destino;
	private double distancia;
	private int activo;
	
	
	public int get_id() {
		return this.id_ruta;
	}
	
	public String get_origen() {
		return this.origen;
	}
	
	public String get_destino() {
		return this.destino;
	}
	
	public double get_distancia() {
		return this.distancia;
	}
	
	
	public int get_activo() {
		return this.activo;
	}
	

	public void set_id(int id) {
		this.id_ruta = id;
	}
	
	public void set_origen(String origen) {
		this.origen = origen;
	}
	
	public void set_destino(String destino) {
		this.destino = destino;
	}
	
	public void set_distancia(Double distancia) {
		this.distancia = distancia;
	}
	
	public void set_activo(int activo) {
		this.activo = activo;
	}
}
