package negocio.RemitenteJPA;

import java.util.Set;

public interface SARemitente {
	
	public int altaRemitente(TRemitente tRemitente);
	
	public int bajaRemitente(int id_remitente);
	
	public int modificarRemitente(TRemitente tRemitente);
	
	public TRemitente buscarRemitente(int id_remitente);
	
	public Set<TRemitente> listarTodosRemitentes();
	
	public double calcularPrecioPaquetesRemitente(int id_remitente);
}
