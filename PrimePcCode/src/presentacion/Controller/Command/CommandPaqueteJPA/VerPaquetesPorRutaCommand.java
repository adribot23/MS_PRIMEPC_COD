package presentacion.Controller.Command.CommandPaqueteJPA;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.PaqueteJPA.TPaquete;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class VerPaquetesPorRutaCommand implements Command {

    @Override
    public Context execute(Object data) {
        java.util.Set<TPaquete> res = SAAbstractFactory.getInstancia().generarSAPaquete().mostrarPaquetesPorRuta((Integer) data);
        if (res != null && !res.isEmpty()) {
            return new Context(Evento.RES_VER_PAQUETES_POR_RUTA_OK, res);
        } else {
            return new Context(Evento.RES_VER_PAQUETES_POR_RUTA_KO, null);
        }
    }
}
