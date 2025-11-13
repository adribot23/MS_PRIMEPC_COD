package negocio.TransporteJPA;

import java.util.Set;

public interface SATransporte {
	
	public int AltaTransporte(TTransporte t);
	
	public int BajaTransporte(int id);
	
	public int ModificarTransporte(TTransporte t);
	
	public TTransporte leerTransporte(int id);
	
	public Set<TTransporte> leerTodosTransportes();
}
