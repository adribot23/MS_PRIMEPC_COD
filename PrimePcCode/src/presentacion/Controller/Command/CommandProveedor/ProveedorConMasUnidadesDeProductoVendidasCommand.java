package presentacion.Controller.Command.CommandProveedor;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Proveedor.TProveedor;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ProveedorConMasUnidadesDeProductoVendidasCommand implements Command {

	@Override
	public Context execute(Object data) {
		TProveedor proveedor = SAAbstractFactory.getInstancia().generarSAProveedor()
				.proveedorConMasUnidadesDeProductoVendidas((int) data);
		if (proveedor != null)
			return new Context(Evento.RES_PROVEEDOR_CON_MAS_UDS_OK, proveedor);
		else
			return new Context(Evento.RES_PROVEEDOR_CON_MAS_UDS_KO, null);
	}

}
