package presentacion.Controller.Command.CommandEmpleado;

import java.util.AbstractMap;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CalcularMasVendidoCommand implements Command {

    @Override
    public Context execute(Object data) {
        Integer idProducto = (Integer) data;

        AbstractMap.SimpleEntry<Integer, Integer> idEmpleadoCantidad = SAAbstractFactory.getInstancia()
                .generarSAEmpleado().calcularImporteMasVendido(idProducto);

        if (idEmpleadoCantidad != null)
            return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_OK, idEmpleadoCantidad);
        else
            return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_KO, null);
    }
}
