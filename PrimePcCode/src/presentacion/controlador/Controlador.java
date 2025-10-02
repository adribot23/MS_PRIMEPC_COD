package presentacion.controlador;

import java.util.Collection;
import negocio.factoria.FactoriaNegocio;
import negocio.serviciosAplicacion.*;
import negocio.transfers.*;
import presentacion.factoria.FactoriaPresentacion;
import presentacion.vista.Evento;
import presentacion.vista.IGUI;



public class Controlador {
	
	//Hay que quitar los static
	private static SAEmpleado saEmpleado = FactoriaNegocio.obtenerInstancia().generaSAEmpleado();
	private static SACliente saCliente = FactoriaNegocio.obtenerInstancia().generaSACliente();
	private static SAAlmacen saAlmacen = FactoriaNegocio.obtenerInstancia().generaSAAlmacen();
	private static SAProveedor saProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
	private static SAProducto saProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
	private static SAVenta saVenta = FactoriaNegocio.obtenerInstancia().generaSAVenta();

	private static IGUI guiEmpleado = FactoriaPresentacion.obtenerInstancia().generaGUIEmpleado();
	private static IGUI guiCliente = FactoriaPresentacion.obtenerInstancia().generaGUICliente();
	private static IGUI guiAlmacen = FactoriaPresentacion.obtenerInstancia().generaGUIAlmacen();
	private static IGUI guiProveedor = FactoriaPresentacion.obtenerInstancia().generaGUIProveedor();
	private static IGUI guiProducto = FactoriaPresentacion.obtenerInstancia().generaGUIProducto();
	private static IGUI guiVenta = FactoriaPresentacion.obtenerInstancia().generaGUIVenta();

	private static Controlador ctrl;

	public static Controlador obtenerInstancia() {
		if (ctrl == null)
			ctrl = new Controlador();
		return ctrl;
	}

	public void accion(Evento evento, Object datos) {
		switch (evento) {
		// Empleado
		case ALTA_EMPLEADO: {
			TEmpleado tEmpleado = (TEmpleado) datos;
			int res = saEmpleado.altaEmpleado(tEmpleado);
			if (res > 0)
				guiEmpleado.actualizar(Evento.RES_ALTA_EMPLEADO_OK, res); // LLamar a la factoria cada vez que actualizemos la vista
			else
				guiEmpleado.actualizar(Evento.RES_ALTA_EMPLEADO_KO, null);
			break;
		}
		case BAJA_EMPLEADO: {
			int idEmpleado = (int) datos;
			int res = saEmpleado.bajaEmpleado(idEmpleado);
			if (res > 0)
				guiEmpleado.actualizar(Evento.RES_BAJA_EMPLEADO_OK, res);
			else
				guiEmpleado.actualizar(Evento.RES_BAJA_EMPLEADO_KO, null);
			break;
		}
		case MODIFICAR_EMPLEADO: {
			TEmpleado tEmpleado = (TEmpleado) datos;
			int res = saEmpleado.modificarEmpleado(tEmpleado);
			if (res > 0)
				guiEmpleado.actualizar(Evento.RES_MODIFICAR_EMPLEADO_OK, res);
			else
				guiEmpleado.actualizar(Evento.RES_MODIFICAR_EMPLEADO_KO, null);
			break;
		}
		case BUSCAR_EMPLEADO: {
			int idEmpleado = (int) datos;
			TEmpleado tEmpleado = saEmpleado.leerEmpleado(idEmpleado);
			if (tEmpleado != null)
				guiEmpleado.actualizar(Evento.RES_BUSCAR_EMPLEADO_OK, tEmpleado);
			else
				guiEmpleado.actualizar(Evento.RES_BUSCAR_EMPLEADO_KO, tEmpleado);
			break;
		}
		case MOSTRAR_TODOS_EMPLEADOS: {
			Collection<TEmpleado> empleados = saEmpleado.leerTodosEmpleados();

			if (empleados != null && !empleados.isEmpty())
				guiEmpleado.actualizar(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_OK, empleados);
			else
				guiEmpleado.actualizar(Evento.RES_MOSTRAR_TODOS_EMPLEADOS_KO, null);
			break;

		}

		// Cliente
		case ALTA_CLIENTE: {
			TCliente tCliente = (TCliente) datos;
			int res = saCliente.altaCliente(tCliente);
			if (res > 0)
				guiCliente.actualizar(Evento.RES_ALTA_CLIENTE_OK, res);
			else
				guiCliente.actualizar(Evento.RES_ALTA_CLIENTE_KO, null);
			break;
		}
		case BAJA_CLIENTE: {
			int idCliente = (int) datos;
			int res = saCliente.bajaCliente(idCliente);
			if (res > 0)
				guiCliente.actualizar(Evento.RES_BAJA_CLIENTE_OK, res);
			else
				guiCliente.actualizar(Evento.RES_BAJA_CLIENTE_KO, null);
			break;
		}
		case MODIFICAR_CLIENTE: {
			TCliente tCliente = (TCliente) datos;
			int res = saCliente.modificarCliente(tCliente);
			if (res > 0)
				guiCliente.actualizar(Evento.RES_MODIFICAR_CLIENTE_OK, res);
			else
				guiCliente.actualizar(Evento.RES_MODIFICAR_CLIENTE_KO, null);
			break;
		}
		case BUSCAR_CLIENTE: {
			int idCliente = (int) datos;
			TCliente tCliente = saCliente.leerCliente(idCliente);
			if (tCliente != null)
				guiCliente.actualizar(Evento.RES_BUSCAR_CLIENTE_OK, tCliente);
			else
				guiCliente.actualizar(Evento.RES_BUSCAR_CLIENTE_KO, tCliente);
			break;
		}
		case MOSTRAR_TODOS_CLIENTES: {
			Collection<TCliente> clientes = saCliente.leerTodosClientes();
			if (clientes != null && !clientes.isEmpty())
				guiCliente.actualizar(Evento.RES_MOSTRAR_TODOS_CLIENTES_OK, clientes);
			else
				guiCliente.actualizar(Evento.RES_MOSTRAR_TODOS_CLIENTES_KO, clientes);

			break;
		}

		// Almac�n
		case ALTA_ALMACEN: {
			TAlmacen tAlmacen = (TAlmacen) datos;
			int res = saAlmacen.altaAlmacen(tAlmacen);
			if (res > 0)
				guiAlmacen.actualizar(Evento.RES_ALTA_ALMACEN_OK, res);
			else
				guiAlmacen.actualizar(Evento.RES_ALTA_ALMACEN_KO, null);
			break;
		}
		case BAJA_ALMACEN: {
			int idAlmacen = (int) datos;
			int res = saAlmacen.bajaAlmacen(idAlmacen);
			if (res > 0)
				guiAlmacen.actualizar(Evento.RES_BAJA_ALMACEN_OK, res);
			else
				guiAlmacen.actualizar(Evento.RES_BAJA_ALMACEN_KO, null);
			break;
		}
		case MODIFICAR_ALMACEN: {
			TAlmacen tAlmacen = (TAlmacen) datos;
			int res = saAlmacen.modificarAlmacen(tAlmacen);
			if (res > 0)
				guiAlmacen.actualizar(Evento.RES_MODIFICAR_ALMACEN_OK, res);
			else
				guiAlmacen.actualizar(Evento.RES_MODIFICAR_ALMACEN_KO, null);
			break;
		}
		case BUSCAR_ALMACEN: {
			int idAlmacen = (int) datos;
			TAlmacen tAlmacen = saAlmacen.leerAlmacen(idAlmacen);
			if (tAlmacen != null)
				guiAlmacen.actualizar(Evento.RES_BUSCAR_ALMACEN_OK, tAlmacen);
			else
				guiAlmacen.actualizar(Evento.RES_BUSCAR_ALMACEN_KO, tAlmacen);
			break;
		}
		case MOSTRAR_TODOS_ALMACENES: {
			Collection<TAlmacen> almacenes = saAlmacen.leerTodosAlmacenes();
			if (almacenes != null && !almacenes.isEmpty())
				guiAlmacen.actualizar(Evento.RES_MOSTRAR_TODOS_ALMACENES_OK, almacenes);
			else
				guiAlmacen.actualizar(Evento.RES_MOSTRAR_TODOS_ALMACENES_KO, almacenes);
			break;
		}

		case VINCULAR_PRODUCTO_ALMACEN: {

			int[] datosVincular = (int[]) datos;
			int idProducto = datosVincular[0];
			int idAlmacen = datosVincular[1];
			int res = saAlmacen.vincularProductoAlmacen(idProducto, idAlmacen);
			if (res > 0)
				guiAlmacen.actualizar(Evento.RES_VINCULAR_PRODUCTO_ALMACEN_OK, res);
			else
				guiAlmacen.actualizar(Evento.RES_VINCULAR_PRODUCTO_ALMACEN_KO, null);

			break;
		}

		case DESVINCULAR_PRODUCTO_ALMACEN: {

			int[] datosDesvincular = (int[]) datos;
			int idProducto = datosDesvincular[0];
			int idAlmacen = datosDesvincular[1];
			int res = saAlmacen.desvincularProductoAlmacen(idProducto, idAlmacen);
			if (res > 0)
				guiAlmacen.actualizar(Evento.RES_DESVINCULAR_PRODUCTO_ALMACEN_OK, res);
			else
				guiAlmacen.actualizar(Evento.RES_DESVINCULAR_PRODUCTO_ALMACEN_KO, null);

			break;
		}
		// Proveedor
		case ALTA_PROVEEDOR: {
			TProveedor tProveedor = (TProveedor) datos;
			int res = saProveedor.altaProveedor(tProveedor);
			if (res > 0)
				guiProveedor.actualizar(Evento.RES_ALTA_PROVEEDOR_OK, res);
			else
				guiProveedor.actualizar(Evento.RES_ALTA_PROVEEDOR_KO, null);
			break;
		}

		case BAJA_PROVEEDOR: {
			int idProveedor = (int) datos;
			int res = saProveedor.bajaProveedor(idProveedor);
			if (res > 0)
				guiProveedor.actualizar(Evento.RES_BAJA_PROVEEDOR_OK, res);
			else
				guiProveedor.actualizar(Evento.RES_BAJA_PROVEEDOR_KO, null);
			break;
		}

		case MODIFICAR_PROVEEDOR: {
			TProveedor tProveedor = (TProveedor) datos;
			int res = saProveedor.modificarProveedor(tProveedor);
			if (res > 0)
				guiProveedor.actualizar(Evento.RES_MODIFICAR_PROVEEDOR_OK, res);
			else
				guiProveedor.actualizar(Evento.RES_MODIFICAR_PROVEEDOR_KO, null);
			break;
		}

		case BUSCAR_PROVEEDOR: {
			int idProveedor = (int) datos;
			TProveedor tProveedor = saProveedor.leerProveedor(idProveedor);
			if (tProveedor != null)
				guiProveedor.actualizar(Evento.RES_BUSCAR_PROVEEDOR_OK, tProveedor);
			else
				guiProveedor.actualizar(Evento.RES_BUSCAR_PROVEEDOR_KO, null);
			break;
		}

		case MOSTRAR_TODOS_PROVEEDORES: {
			Collection<TProveedor> proveedores = saProveedor.leerTodosProveedores();
			if (proveedores != null && !proveedores.isEmpty())
				guiProveedor.actualizar(Evento.RES_MOSTRAR_TODOS_PROVEEDORES_OK, proveedores);
			else
				guiProveedor.actualizar(Evento.RES_MOSTRAR_TODOS_PROVEEDORES_KO, null);
			break;
		}

		case MOSTRAR_PROVEEDORES_POR_PRODUCTO: {
			int idProducto = (int) datos;
			TProveedor proveedor = saProveedor.leerProveedorPorProducto(idProducto);
			if (proveedor != null)
				guiProveedor.actualizar(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK, proveedor);
			else
				guiProveedor.actualizar(Evento.RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO, null);
			break;
		}

		case VINCULAR_PRODUCTO_PROVEEDOR: {

			int[] datosVincular = (int[]) datos;
			int idProducto = datosVincular[0];
			int idProveedor = datosVincular[1];

			int res = saProveedor.vincularProductoProveedor(idProducto, idProveedor);

			if (res > 0)
				guiProveedor.actualizar(Evento.RES_VINCULAR_PRODUCTO_PROVEEDOR_OK, res);
			else
				guiProveedor.actualizar(Evento.RES_VINCULAR_PRODUCTO_PROVEEDOR_KO, null);

			break;
		}

		case DESVINCULAR_PRODUCTO_PROVEEDOR: {

			int[] datosDesvincular = (int[]) datos;
			int idProducto = datosDesvincular[0];
			int idProveedor = datosDesvincular[1];

			int res = saProveedor.desvincularProductoProveedor(idProducto, idProveedor);

			if (res > 0)
				guiProveedor.actualizar(Evento.RES_DESVINCULAR_PRODUCTO_PROVEEDOR_OK, res);
			else
				guiProveedor.actualizar(Evento.RES_DESVINCULAR_PRODUCTO_PROVEEDOR_KO, null);

			break;
		}

		// Producto
		case ALTA_PRODUCTO: {
			TProducto tProducto = (TProducto) datos;
			int res = saProducto.altaProducto(tProducto);
			if (res > 0)
				guiProducto.actualizar(Evento.RES_ALTA_PRODUCTO_OK, res);
			else
				guiProducto.actualizar(Evento.RES_ALTA_PRODUCTO_KO, null);
			break;
		}
		case BAJA_PRODUCTO: {
			int idProducto = (int) datos;
			int res = saProducto.bajaProducto(idProducto);
			if (res > 0)
				guiProducto.actualizar(Evento.RES_BAJA_PRODUCTO_OK, res);
			else
				guiProducto.actualizar(Evento.RES_BAJA_PRODUCTO_KO, null);
			break;
		}
		case MODIFICAR_PRODUCTO: {
			TProducto tProducto = (TProducto) datos;
			int res = saProducto.modificarProducto(tProducto);
			if (res > 0)
				guiProducto.actualizar(Evento.RES_MODIFICAR_PRODUCTO_OK, res);
			else
				guiProducto.actualizar(Evento.RES_MODIFICAR_PRODUCTO_KO, null);
			break;
		}
		case BUSCAR_PRODUCTO: {
			int idProducto = (int) datos;
			TProducto tProducto = saProducto.leerProducto(idProducto);
			if (tProducto != null)
				guiProducto.actualizar(Evento.RES_BUSCAR_PRODUCTO_OK, tProducto);
			else
				guiProducto.actualizar(Evento.RES_BUSCAR_PRODUCTO_KO, null);
			break;
		}
		case MOSTRAR_TODOS_PRODUCTOS: {
			Collection<TProducto> productos = saProducto.leerTodosProductos();
			if (productos != null && !productos.isEmpty())
				guiProducto.actualizar(Evento.RES_MOSTRAR_TODOS_PRODUCTOS_OK, productos);
			else
				guiProducto.actualizar(Evento.RES_MOSTRAR_TODOS_PRODUCTOS_KO, null);
			break;
		}
		case MOSTRAR_PRODUCTOS_POR_PROVEEDOR: {
			int idProveedor = (int) datos;
			Collection<TProducto> productos = saProducto.leerProductosPorProveedor(idProveedor);
			if (productos != null && !productos.isEmpty())
				guiProducto.actualizar(Evento.RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK, productos);
			else
				guiProducto.actualizar(Evento.RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO, null);
			break;
		}
		case MOSTRAR_PRODUCTOS_POR_ALMACEN: {
			int idAlmacen = (int) datos;
			Collection<TProducto> productos = saProducto.leerProductosPorAlmacen(idAlmacen);
			if (productos != null && !productos.isEmpty())
				guiProducto.actualizar(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK, productos);
			else
				guiProducto.actualizar(Evento.RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO, null);
			break;
		}

		// Venta

		case ALTA_VENTA: {
			TVenta tVenta = (TVenta) datos;
			int res = saVenta.altaVenta(tVenta);
			if (res > 0)
				guiVenta.actualizar(Evento.RES_ALTA_VENTA_OK, res);
			else
				guiVenta.actualizar(Evento.RES_ALTA_VENTA_KO, null);
			break;
		}

		case BAJA_VENTA: {
			int idVenta = (int) datos;
			int res = saVenta.bajaVenta(idVenta);
			if (res > 0)
				guiVenta.actualizar(Evento.RES_BAJA_VENTA_OK, null);
			else
				guiVenta.actualizar(Evento.RES_BAJA_VENTA_KO, null);
			break;
		}

		case MODIFICAR_VENTA: {
			TVenta tVenta = (TVenta) datos;
			int res = saVenta.modificarVenta(tVenta);
			if (res > 0)
				guiVenta.actualizar(Evento.RES_MODIFICAR_VENTA_OK, null);
			else
				guiVenta.actualizar(Evento.RES_MODIFICAR_VENTA_KO, null);
			break;
		}
		case BUSCAR_VENTA: {
			int idVenta = (int) datos;
			TVenta tVenta = saVenta.leerVenta(idVenta);
			if (tVenta != null)
				guiVenta.actualizar(Evento.RES_BUSCAR_VENTA_OK, tVenta);
			else
				guiVenta.actualizar(Evento.RES_BUSCAR_VENTA_KO, null);
			break;
		}
		case MOSTRAR_TODAS_VENTAS: {
			Collection<TVenta> ventas = saVenta.leerTodasVentas();
			if (ventas != null && !ventas.isEmpty())
				guiVenta.actualizar(Evento.RES_MOSTRAR_TODAS_VENTAS_OK, ventas);
			else
				guiVenta.actualizar(Evento.RES_MOSTRAR_TODAS_VENTAS_KO, null);
			break;
		}
		case MOSTRAR_VENTAS_POR_EMPLEADO: {
			int idEmpleado = (int) datos;
			Collection<TVenta> ventas = saVenta.leerVentasPorEmpleado(idEmpleado);
			if (ventas != null && !ventas.isEmpty())
				guiVenta.actualizar(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK, ventas);
			else
				guiVenta.actualizar(Evento.RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO, null);
			break;
		}
		case MOSTRAR_VENTAS_POR_CLIENTE: {
			int idCliente = (int) datos;
			Collection<TVenta> ventas = saVenta.leerVentasPorCliente(idCliente);
			if (ventas != null && !ventas.isEmpty())
				guiVenta.actualizar(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_OK, ventas);
			else
				guiVenta.actualizar(Evento.RES_MOSTRAR_VENTAS_POR_CLIENTE_KO, null);
			break;
		}
		default:
			break;

		}
	}
}
