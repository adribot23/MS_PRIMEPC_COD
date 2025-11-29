package negocio.TransporteJPA;

import java.util.Set;

import negocio.TrabajadorJPA.TTrabajador;

public interface SATransporte {
	
	public int altaTransporte(TTransporte t);
	
	public int bajaTransporte(int id);
	
	public int modificarTransporte(TTransporte t);
	
	public TTransporte leerTransporte(int id);
	
	public Set<TTransporte> leerTodosTransportes();
	
	public int vincularTransporteTrabajador(TTransporteTrabajador t);
	
	public int desvincularTransporteTrabajador(TTransporteTrabajador t);
	
	public Set<TTransporte> leerTransportesPorTrabajador(TTrabajador t);
}
