package presentacion.factoria;

import presentacion.vista.Evento;
import presentacion.vista.IGUI;
import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public abstract class FactoriaPresentacion {

	private static FactoriaPresentacion instancia;
	public static FactoriaPresentacion obtenerInstancia(Evento e)
	{ if (instancia== null)
	instancia = new FactoriaPresentacionImp(e);
	return instancia;
	}
	
	public abstract IGUI GeneraVista();
	public abstract GUIAlmacen generaGUIAlmacen();
	public abstract GUICliente generaGUICliente();
	public abstract GUIEmpleado generaGUIEmpleado();
	public abstract GUIProducto generaGUIProducto();
	public abstract GUIProveedor generaGUIProveedor();
	public abstract GUIVenta generaGUIVenta();
	
	
}
