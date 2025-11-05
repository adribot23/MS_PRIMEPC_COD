package presentacion.Controller.Command.CommandEmpleado;

import java.util.AbstractMap;

import negocio.Empleado.TEmpleado;
import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CalcularImporteMasVendidoCommand implements Command {

	@Override
	public Context execute(Object data) {
		AbstractMap.SimpleEntry<Integer, Integer> res = SAAbstractFactory.getInstancia().generarSAEmpleado().calcularImporteMasVendido((int) data);
		if (res != null)
			return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_OK, res);
		else
			return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_KO, null);
	}

}
