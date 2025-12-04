package negocio.RemitenteJPA;

import jakarta.persistence.Entity;
import java.io.Serializable;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.RemitenteJPA.Particular.findByfechaNacimiento", query = "select p from Particular p where :fechaNacimiento = p.fechaNacimiento") })
public class Particular extends Remitente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fechaNacimiento;

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
