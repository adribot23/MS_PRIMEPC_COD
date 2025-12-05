package negocio.RutaJPA;

import java.util.Set;

public interface SARuta {

	public int alta_ruta(TRuta truta);
	public int baja_ruta(int id);
	public int modificar_ruta(TRuta truta);
	public TRuta buscar_ruta(int id);
	public Set<TRuta> listar_rutas();
	
}
