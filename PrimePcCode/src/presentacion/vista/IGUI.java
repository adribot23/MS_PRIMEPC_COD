package presentacion.vista;

import presentacion.factoria.Evento;

public interface IGUI {
	void actualizar(Evento evento, Object datos);
}
