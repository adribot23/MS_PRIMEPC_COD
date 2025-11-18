package negocio.TrabajadorJPA;

import java.util.Set;

import negocio.TrabajadorJPA.TTrabajador;

public interface SATrabajador {

	public int altaTrabajador(TTrabajador trabajador);
	

	public int bajaTrabajador(int id_trabajador);

	public int modificarTrabajador(TTrabajador trabajador);

	public TTrabajador buscarTrabajador(int id_trabajador);

	public Set<TTrabajador> mostrarTrabajador();
	
}
