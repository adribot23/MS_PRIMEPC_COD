package negocio.RutaJPA;

import java.util.Set;

public interface SARuta {

	public int alta_ruta(TRuta ruta);
	public int baja_ruta(int id);
	public int modificar_ruta(TRuta ruta);
	public TRuta buscar_ruta(int id);
	public Set<TRuta> listar_rutas();
	
}
