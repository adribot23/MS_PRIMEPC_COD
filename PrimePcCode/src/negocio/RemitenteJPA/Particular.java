package negocio.RemitenteJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "Negocio.RemitenteJPA.Particular.findByfechaNacimiento",
        query = "select p from Particular p where :fechaNacimiento = p.fechaNacimiento"
    )
})
public class Particular extends Remitente implements Serializable {

	private static final long serialVersionUID = 0;
	
	private String fechaNacimiento;
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
