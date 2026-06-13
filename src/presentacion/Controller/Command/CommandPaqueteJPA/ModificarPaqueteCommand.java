package presentacion.Controller.Command.CommandPaqueteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.PaqueteJPA.TPaquete;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ModificarPaqueteCommand implements Command {

    @Override
    public Context execute(Object data) {
        int res = SAAbstractFactory.getInstancia().generarSAPaquete().modificarPaquete((TPaquete) data);
        if (res > 0) {
            return new Context(Evento.RES_MODIFICAR_PAQUETE_OK, res);
        } else {
            return new Context(Evento.RES_MODIFICAR_PAQUETE_KO, res);
        }
    }
}
