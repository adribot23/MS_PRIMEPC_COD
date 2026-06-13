package negocio.TrabajadorJPA;

import java.util.Set;

public interface SATrabajador {

	public int altaTrabajador(TTrabajador trabajador);

	public int bajaTrabajador(int id_trabajador);

	public int modificarTrabajador(TTrabajador trabajador);

	public TTrabajador leerTrabajador(int id_trabajador);

	public Set<TTrabajador> leerTodosTrabajadores();

	public Set<TTrabajador> leerTrabajadorPorTransporte(int id_transporte);

}
