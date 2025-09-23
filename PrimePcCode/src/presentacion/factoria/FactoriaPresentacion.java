package presentacion.factoria;

import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public abstract class FactoriaPresentacion {

	private static FactoriaPresentacion instancia;
	public static FactoriaPresentacion obtenerInstancia()
	{ if (instancia== null)
	instancia = new FactoriaPresentacionImp();
	return instancia;
	}
	public abstract GUIAlmacen generaGUIAlmacen();
	public abstract GUICliente generaGUICliente();
	public abstract GUIEmpleado generaGUIEmpleado();
	public abstract GUIProducto generaGUIProducto();
	public abstract GUIProveedor generaGUIProveedor();
	public abstract GUIVenta generaGUIVenta();
}
