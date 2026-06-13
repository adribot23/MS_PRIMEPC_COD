
package presentacion.Controller.Command;

import presentacion.GUI.Evento;

public class Context {

	private Object datos;

	private Evento evento;

	public Context(Evento e, Object d) {
		evento = e;
		datos = d;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}

}