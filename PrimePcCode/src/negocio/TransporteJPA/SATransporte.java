package negocio.TransporteJPA;

import java.util.Set;

import negocio.TrabajadorJPA.TTrabajador;

public interface SATransporte {
	
	public int altaTransporte(TTransporte t);
	
	public int bajaTransporte(int id);
	
	public int modificarTransporte(TTransporte t);
	
	public TTransporte leerTransporte(int id);
	
	public Set<TTransporte> leerTodosTransportes();
	
	public int vincularTransporteTrabajador(int id_transporte, int id_trabajador);
	
	public int desvincularTransporteTrabajador(int id_transporte, int id_trabajador);
	
	public Set<TTransporte> leerTransportesPorTrabajador(int id_trabajador);
}
