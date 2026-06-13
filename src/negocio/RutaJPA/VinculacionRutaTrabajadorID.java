package negocio.RutaJPA;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VinculacionRutaTrabajadorID implements Serializable {

	private static final long serialVersionUID = 0;

	@Column(name = "ID_RUTA")
	int ruta;
	
	@Column(name = "ID_TRABAJADOR")
	int trabajador;

	public VinculacionRutaTrabajadorID() {
	}

	public VinculacionRutaTrabajadorID(int ruta, int trabajador) {
		this.ruta = ruta;
		this.trabajador = trabajador;
	}

	int get_ruta() {
		return this.ruta;
	}

	int get_trabajador() {
		return this.trabajador;
	}

	void set_ruta(int ruta) {
		this.ruta = ruta;
	}

	void set_trabajador(int trabajador) {
		this.trabajador = trabajador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ruta, trabajador);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VinculacionRutaTrabajadorID other = (VinculacionRutaTrabajadorID) obj;
		return ruta == other.ruta && trabajador == other.trabajador;
	}

}
