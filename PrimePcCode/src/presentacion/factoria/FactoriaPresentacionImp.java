package presentacion.factoria;

import negocio.transfers.TEmpleado;
import presentacion.vista.Evento;
import presentacion.vista.IGUI;
import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public class FactoriaPresentacionImp extends FactoriaPresentacion  {

	
	private Evento e;
	public FactoriaPresentacionImp(Evento e) {
		this.e= e;
		
	}
	
	@Override
	public IGUI GeneraVista() {
		switch (e) {
		
		case RES_ALTA_EMPLEADO_OK: {
			return new GUIEmpleado();
	
		}
		case RES_ALTA_EMPLEADO_KO: {
			return new GUIEmpleado();
		}
		}
		return null;
		
	}

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
