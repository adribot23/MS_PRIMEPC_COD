package presentacion.Controller.Command.CommandPaqueteJPA;
import negocio.FactoriaSA.SAAbstractFactory;
import negocio.PaqueteJPA.TPaquete;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class BuscarPaqueteCommand implements Command {

    @Override
    public Context execute(Object data) {
        TPaquete res = SAAbstractFactory.getInstancia().generarSAPaquete().buscarPaquete((int) data);
        if (res != null) {
            return new Context(Evento.RES_BUSCAR_PAQUETE_OK, res);
        } else {
            return new Context(Evento.RES_BUSCAR_PAQUETE_KO, null);
        }
    }
}
