package negocio.RutaJPA;

public class TVinculacionRutaTrabajador {
	
	private int id_ruta;
	private int id_trabajador;
	private String hora_salida;
	private String estado;
	private String fecha_asignacion;
	
	public TVinculacionRutaTrabajador() {}
	
	public int get_id_ruta() {
		return this.id_ruta;
	}
	

	public int get_id_trabajador() {
		return this.id_trabajador;
	}


	public String get_hora_salida() {
		return this.hora_salida;
	}


	public String get_estado() {
		return this.estado;
	}
	
	public String get_fecha_asignacion() {
		return this.fecha_asignacion;
	}
	
	public void set_id_ruta(int id_ruta) {
		this.id_ruta = id_ruta;
	}
	
	public void set_id_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}

	public void set_hora_salida(String hora_salida) {
		this.hora_salida = hora_salida;
	}
	
	public void set_estado(String estado) {
		this.estado = estado;
	}
	
	public void set_fecha_asignacion(String fecha_asignacion) {
		this.fecha_asignacion = fecha_asignacion;
	}


}
