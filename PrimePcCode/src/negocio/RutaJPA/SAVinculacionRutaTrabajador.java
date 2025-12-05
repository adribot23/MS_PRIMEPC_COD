package negocio.RutaJPA;

import java.util.Set;

public interface SAVinculacionRutaTrabajador {

	
	public int vincular_ruta_trabajador(TVinculacionRutaTrabajador vinculacion);
	public int desvincular_ruta_trabajador(TVinculacionRutaTrabajador vinculacion);
	public Set<TVinculacionRutaTrabajador> listar_vinculaciones_por_trabajador (int id_trabajador);
	public Set<TVinculacionRutaTrabajador> listar_vinculaciones_por_ruta (int id_ruta);
	
}
