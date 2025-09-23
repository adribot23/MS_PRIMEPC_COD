package presentacion.factoria;

import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public class FactoriaPresentacionImp extends FactoriaPresentacion {

	@Override
	public GUIAlmacen generaGUIAlmacen() {
		return new GUIAlmacen();
	}

	@Override
	public GUICliente generaGUICliente() {
		return new  GUICliente();
	}

	@Override
	public GUIEmpleado generaGUIEmpleado() {
		return new GUIEmpleado();
	}

	@Override
	public GUIProducto generaGUIProducto() {
		return new GUIProducto();
	}

	@Override
	public GUIProveedor generaGUIProveedor() {
		return new GUIProveedor();
	}

	@Override
	public GUIVenta generaGUIVenta() {
		return new GUIVenta();
	}

}
