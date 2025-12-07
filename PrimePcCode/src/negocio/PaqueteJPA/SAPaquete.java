package negocio.PaqueteJPA;

import java.util.Set;

public interface SAPaquete {
	public int altaPaquete(TPaquete paquete);

	public int bajaPaquete(int id_paquete);

	public int modificarPaquete(TPaquete paquete);

	public TPaquete buscarPaquete(int id_paquete);

	public Set<TPaquete> mostrarPaquetes();
	
	public Set<TPaquete> mostrarPaquetesPorFactura(int id_factura);
	
	public Set<TPaquete> mostrarPaquetesPorRuta(int id_ruta);
}
