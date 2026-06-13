package presentacion.Controller.Command.CommandPaqueteJPA;
import negocio.FactoriaSA.SAAbstractFactory;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BajaPaqueteCommand implements Command {

    @Override
    public Context execute(Object data) {
        int res = SAAbstractFactory.getInstancia().generarSAPaquete().bajaPaquete((int) data);
        if (res > 0) {
            return new Context(Evento.RES_BAJA_PAQUETE_OK, res);
        } else {
            return new Context(Evento.RES_BAJA_PAQUETE_KO, res);
        }
    }
}
