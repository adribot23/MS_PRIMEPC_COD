package presentacion.Controller.Command.CommandEmpleado;

import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class CalcularMasVendidoCommand implements Command {

    @Override
    public Context execute(Object data) {
        Integer idProducto = (Integer) data;

        int idEmpleadoMasVendedor = SAAbstractFactory.getInstancia()
                .generarSAEmpleado().calcularImporteMasVendido(idProducto);

        if (idEmpleadoMasVendedor > 0)
            return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_OK, idEmpleadoMasVendedor);
        else
            return new Context(Evento.RES_CALCULAR_MAS_VENDIDO_KO, null);
    }
}
