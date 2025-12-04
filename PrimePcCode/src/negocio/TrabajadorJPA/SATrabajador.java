package negocio.TrabajadorJPA;

import java.util.Set;

public interface SATrabajador {

	public int AltaTrabajador(TTrabajador trabajador);

	public int BajaTrabajador(int id_trabajador);

	public int ModificarTrabajador(TTrabajador trabajador);

	public TTrabajador leerTrabajador(int id_trabajador);

	public Set<TTrabajador> leerTodosTrabajadores();
	
	public Set<TTrabajador> leerTrabajadorPorTransporte(int id_transporte);
	
	public Set<TTrabajador> leerTrabajadorPorRuta(int id_ruta);

}
