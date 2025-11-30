package negocio.RemitenteJPA;

import java.util.Set;

public class SARemitenteImp implements SARemitente {

	@Override
	public int altaRemitente(TRemitente tRemitente) {
		return 0;
	}

	@Override
	public int bajaRemitente(int id_remitente) {
		return 0;
	}

	@Override
	public int modificarRemitente(TRemitente tRemitente) {
		return 0;
	}

	@Override
	public TRemitente buscarRemitente(int id_remitente) {
		return null;
	}

	@Override
	public Set<TRemitente> listarTodosRemitentes() {
		return null;
	}

	@Override
	public double calcularPrecioPaquetesRemitente(int id_remitente) {
		return 0;
	}
}
