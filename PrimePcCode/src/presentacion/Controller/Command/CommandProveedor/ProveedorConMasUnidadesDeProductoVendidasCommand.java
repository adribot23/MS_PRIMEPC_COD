package presentacion.Controller.Command.CommandProveedor;

import negocio.FactoriaSA.SAAbstractFactory;
import negocio.Proveedor.TProveedor;
import presentacion.Controller.Command.Command;
import presentacion.Controller.Command.Context;
import presentacion.GUI.Evento;

public class ProveedorConMasUnidadesDeProductoVendidasCommand implements Command {

	@Override
	public Context execute(Object data) {
		Integer idProducto = (Integer) data;
		
		int id_proveedor = SAAbstractFactory.getInstancia().generarSAProveedor()
				.proveedorConMasUnidadesDeProductoVendidas(idProducto);
		if (id_proveedor > 0)
			return new Context(Evento.RES_PROVEEDOR_CON_MAS_UDS_OK, id_proveedor);
		else
			return new Context(Evento.RES_PROVEEDOR_CON_MAS_UDS_KO, null);
	}

}
