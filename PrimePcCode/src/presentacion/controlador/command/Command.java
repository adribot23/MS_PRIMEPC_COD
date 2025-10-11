package presentacion.controlador.command;

import presentacion.controlador.Context;

public interface Command {

	public abstract Context execute(Object transfer);
}
