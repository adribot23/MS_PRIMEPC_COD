package negocio.TransporteJPA;

import java.util.Set;

public interface SATransporte {
	
	public int AltaTransporte(TTransporte t);
	
	public int BajaTransporte(int id);
	
	public int ModificarTransporte(TTransporte t);
	
	public TTransporte leerTransporte(int id);
	
	public Set<TTransporte> leerTodosTransportes();
	
	public int vincularTransporteTrabajador(TTransporteTrabajador t);
	
	public int desvincularTransporteTrabajador(TTransporteTrabajador t);
	
	public Set<TTransporte> leerTransportesPorTrabajador(TTrabajador t);
}
