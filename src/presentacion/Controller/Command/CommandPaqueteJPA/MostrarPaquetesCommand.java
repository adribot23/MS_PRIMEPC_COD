package presentacion.Controller.Command.CommandPaqueteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.PaqueteJPA.TPaquete;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class MostrarPaquetesCommand implements Command {

    @Override
    public Context execute(Object data) {
        java.util.Set<TPaquete> res = SAAbstractFactory.getInstancia().generarSAPaquete().mostrarPaquetes();
        if (res != null && !res.isEmpty()) {
            return new Context(Evento.RES_MOSTRAR_TODOS_PAQUETES_OK, res);
        } else {
            return new Context(Evento.RES_MOSTRAR_TODOS_PAQUETES_KO, null);
        }
    }
}
