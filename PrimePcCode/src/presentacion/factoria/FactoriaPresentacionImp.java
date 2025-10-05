package presentacion.factoria;

import presentacion.vista.Evento;
import presentacion.vista.IGUI;
import presentacion.vista.almacen.GUIAlmacen;
import presentacion.vista.cliente.GUICliente;
import presentacion.vista.empleado.GUIEmpleado;
import presentacion.vista.producto.GUIProducto;
import presentacion.vista.proveedor.GUIProveedor;
import presentacion.vista.venta.GUIVenta;

public class FactoriaPresentacionImp extends FactoriaPresentacion {
	/*
	 * private GUIEmpleado guiEmpleado = new GUIEmpleado(); private GUICliente
	 * guiCliente = new GUICliente(); private GUIAlmacen guiAlmacen = new
	 * GUIAlmacen(); private GUIProveedor guiProveedor = new GUIProveedor(); private
	 * GUIProducto guiProducto = new GUIProducto(); private GUIVenta guiVenta = new
	 * GUIVenta();
	 */

	private Evento e;

	public FactoriaPresentacionImp(Evento e) {
		this.e = e;
	}

	@Override
	public IGUI GeneraVista() {
		switch (e) {

		// ===== EMPLEADO =====
		case ALTA_EMPLEADO:
		case BAJA_EMPLEADO:
		case MODIFICAR_EMPLEADO:
		case BUSCAR_EMPLEADO:
		case MOSTRAR_TODOS_EMPLEADOS:
			return new GUIEmpleado();

		// ===== CLIENTE =====
		case ALTA_CLIENTE:
		case BAJA_CLIENTE:
		case MODIFICAR_CLIENTE:
		case BUSCAR_CLIENTE:
		case MOSTRAR_TODOS_CLIENTES:
			return new GUICliente();

		// ===== ALMACÉN =====
		case ALTA_ALMACEN:
		case BAJA_ALMACEN:
		case MODIFICAR_ALMACEN:
		case BUSCAR_ALMACEN:
		case MOSTRAR_TODOS_ALMACENES:
		case VINCULAR_PRODUCTO_ALMACEN:
		case DESVINCULAR_PRODUCTO_ALMACEN:
			return new GUIAlmacen();

		// ===== PROVEEDOR =====
		case ALTA_PROVEEDOR:
		case BAJA_PROVEEDOR:
		case MODIFICAR_PROVEEDOR:
		case BUSCAR_PROVEEDOR:
		case MOSTRAR_TODOS_PROVEEDORES:
		case MOSTRAR_PROVEEDORES_POR_PRODUCTO:
		case VINCULAR_PRODUCTO_PROVEEDOR:
		case DESVINCULAR_PRODUCTO_PROVEEDOR:
			return new GUIProveedor();

		// ===== PRODUCTO =====
		case ALTA_PRODUCTO:
		case BAJA_PRODUCTO:
		case MODIFICAR_PRODUCTO:
		case BUSCAR_PRODUCTO:
		case MOSTRAR_TODOS_PRODUCTOS:
		case MOSTRAR_PRODUCTOS_POR_PROVEEDOR:
		case MOSTRAR_PRODUCTOS_POR_ALMACEN:
			return new GUIProducto();

		// ===== VENTA =====
		case ALTA_VENTA:
		case BAJA_VENTA:
		case MODIFICAR_VENTA:
		case BUSCAR_VENTA:
		case MOSTRAR_TODAS_VENTAS:
		case MOSTRAR_VENTAS_POR_EMPLEADO:
		case MOSTRAR_VENTAS_POR_CLIENTE:
			return new GUIVenta();

		default:
			return null;
		}
	}

}
