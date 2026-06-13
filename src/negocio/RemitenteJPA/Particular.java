package negocio.RemitenteJPA;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "PARTICULAR")
@NamedQueries({
		@NamedQuery(name = "Negocio.RemitenteJPA.Particular.findByfechaNacimiento", query = "select p from Particular p where p.fechaNacimiento = :fechaNacimiento") })
public class Particular extends Remitente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NACIMIENTO")
	private String fechaNacimiento;

	public Particular() {
	}

	public Particular(TParticular particular) {
		super(particular);
		this.fechaNacimiento = particular.getFechaNacimiento();
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public TRemitente entityToTransfer() {
		TParticular particular = new TParticular();
		particular.setFechaNacimiento(this.fechaNacimiento);

		TRemitente base = super.entityToTransfer();
		particular.setId(base.getId());
		particular.setActivo(base.getActivo());
		particular.setNombre(base.getNombre());
		particular.setDireccion(base.getDireccion());
		particular.setTelefono(base.getTelefono());

		return particular;
	}
}