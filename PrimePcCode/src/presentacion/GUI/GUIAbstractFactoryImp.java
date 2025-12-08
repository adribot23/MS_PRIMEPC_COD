
package presentacion.GUI;

import presentacion.Almacen.GUIAlmacen;
import presentacion.Almacen.VAltaAlmacen;
import presentacion.Almacen.VBajaAlmacen;
import presentacion.Almacen.VBuscarAlmacen;
import presentacion.Almacen.VModificarAlmacen;
import presentacion.Almacen.VMostrarAlmacen;
import presentacion.Cliente.GUICliente;
import presentacion.Cliente.VAltaCliente;
import presentacion.Cliente.VBajaCliente;
import presentacion.Cliente.VBuscarCliente;
import presentacion.Cliente.VModificarCliente;
import presentacion.Cliente.VMostrarCliente;
import presentacion.Empleado.GUIEmpleado;
import presentacion.Empleado.VAltaEmpleado;
import presentacion.Empleado.VBajaEmpleado;
import presentacion.Empleado.VBuscarEmpleado;
import presentacion.Empleado.VCalcularImporteMasVendido;
import presentacion.Empleado.VModificarEmpleado;
import presentacion.Empleado.VMostrarEmpleado;
import presentacion.FacturaJPA.GUIFactura;
import presentacion.FacturaJPA.VAbrirFactura;
import presentacion.FacturaJPA.VAnyadirPaquete;
import presentacion.FacturaJPA.VBuscarFactura;
import presentacion.FacturaJPA.VCerrarFactura;
import presentacion.FacturaJPA.VDevolucion;
import presentacion.FacturaJPA.VEliminarPaquete;
import presentacion.FacturaJPA.VListarFacturasPorRemitente;
import presentacion.FacturaJPA.VModificarFactura;
import presentacion.FacturaJPA.VMostrarFactura;
import presentacion.PaqueteJPA.GUIPaquete;
import presentacion.PaqueteJPA.VAltaPaquete;
import presentacion.PaqueteJPA.VBajaPaquete;
import presentacion.PaqueteJPA.VBuscarPaquete;
import presentacion.PaqueteJPA.VModificarPaquete;
import presentacion.PaqueteJPA.VMostrarPaquetes;
import presentacion.PaqueteJPA.VVerPaquetesPorFactura;
import presentacion.PaqueteJPA.VVerPaquetesPorRuta;
import presentacion.Producto.GUIProducto;
import presentacion.Producto.VAltaProducto;
import presentacion.Producto.VBajaProducto;
import presentacion.Producto.VBuscarProducto;
import presentacion.Producto.VModificarProducto;
import presentacion.Producto.VMostrarProducto;
import presentacion.Producto.VVerProdPorAlmacen;
import presentacion.Producto.VVerProdPorProveedor;
import presentacion.Proveedor.GUIProveedor;
import presentacion.Proveedor.VAltaProveedor;
import presentacion.Proveedor.VBajaProveedor;
import presentacion.Proveedor.VBuscarProveedor;
import presentacion.Proveedor.VDesvincularProveedor;
import presentacion.Proveedor.VModificarProveedor;
import presentacion.Proveedor.VMostrarProveedor;
import presentacion.Proveedor.VProveedorConMasUnidadesDeProductoVendidas;
import presentacion.Proveedor.VVerPorProducto;
import presentacion.Proveedor.VVincularProveedor;
import presentacion.RemitenteJPA.GUIRemitente;
import presentacion.RemitenteJPA.VAltaRemitente;
import presentacion.RemitenteJPA.VBajaRemitente;
import presentacion.RemitenteJPA.VBuscarRemitente;
import presentacion.RemitenteJPA.VCalcularPrecioPaquete;
import presentacion.RemitenteJPA.VModificarRemitente;
import presentacion.RemitenteJPA.VMostrarRemitente;
import presentacion.TransporteJPA.GUITransporte;
import presentacion.TransporteJPA.VAltaTransporte;
import presentacion.TransporteJPA.VBajaTransporte;
import presentacion.TransporteJPA.VBuscarTransporte;
import presentacion.TransporteJPA.VDesvincularTransporteTrabajador;
import presentacion.TransporteJPA.VModificarTransporte;
import presentacion.TransporteJPA.VMostrarTransporte;
import presentacion.TransporteJPA.VVerTransportePorTrabajador;
import presentacion.TransporteJPA.VVincularTransporteTrabajador;
import presentacion.RutaJPA.GUIRuta;
import presentacion.RutaJPA.VAltaRuta;
import presentacion.RutaJPA.VBajaRuta;
import presentacion.RutaJPA.VBuscarRuta;
import presentacion.RutaJPA.VDesvincularRutaTrabajador;
import presentacion.RutaJPA.VModificarRuta;
import presentacion.RutaJPA.VMostrarRutas;
import presentacion.RutaJPA.VVincularRutaTrabajador;
import presentacion.RutaJPA.VVerRutasPorTrabajador;
import presentacion.RutaJPA.VVerTrabajadoresPorRuta;
import presentacion.Venta.GUIVenta;
import presentacion.Venta.VAbrirVenta;
import presentacion.Venta.VAñadirProducto;
import presentacion.Venta.VBajaVenta;
import presentacion.Venta.VBuscarVenta;
import presentacion.Venta.VCerrarVenta;
import presentacion.Venta.VDevolverVenta;
import presentacion.Venta.VEliminarProducto;
import presentacion.Venta.VListarVenta;
import presentacion.Venta.VModificarVenta;
import presentacion.Venta.VMostrarPorCliente;
import presentacion.Venta.VMostrarPorEmpleado;
import primePcMain.MainWindow;

public class GUIAbstractFactoryImp extends GUIAbstractFactory {

	@Override
	public IGUI generarVistas(Evento evento) {

		switch (evento) {

		case VISTA_PRINCIPAL:
			return new MainWindow();

		/*
		 * ====================== ======== ======== CLIENTE ============
		 * ==============================
		 */
		case CLIENTE:
			return new GUICliente();

		case VALTA_CLIENTE:
		case RES_ALTA_CLIENTE_OK:
		case RES_ALTA_CLIENTE_KO:
			return new VAltaCliente();

		case VBAJA_CLIENTE:
		case RES_BAJA_CLIENTE_OK:
		case RES_BAJA_CLIENTE_KO:
			return new VBajaCliente();

		case VBUSCAR_CLIENTE:
		case RES_BUSCAR_CLIENTE_OK:
		case RES_BUSCAR_CLIENTE_KO:
			return new VBuscarCliente();

		case VMODIFICAR_CLIENTE:
		case RES_MODIFICAR_CLIENTE_OK:
		case RES_MODIFICAR_CLIENTE_KO:
			return new VModificarCliente();

		case VMOSTRAR_TODOS_CLIENTES:
		case RES_MOSTRAR_TODOS_CLIENTES_OK:
		case RES_MOSTRAR_TODOS_CLIENTES_KO:
			return new VMostrarCliente();

		/*
		 * ============================== ======== EMPLEADO ============
		 * ==============================
		 */
		case EMPLEADO:
			return new GUIEmpleado();

		case VALTA_EMPLEADO:
		case RES_ALTA_EMPLEADO_OK:
		case RES_ALTA_EMPLEADO_KO:
			return new VAltaEmpleado();

		case VBAJA_EMPLEADO:
		case RES_BAJA_EMPLEADO_OK:
		case RES_BAJA_EMPLEADO_KO:
			return new VBajaEmpleado();

		case VBUSCAR_EMPLEADO:
		case RES_BUSCAR_EMPLEADO_OK:
		case RES_BUSCAR_EMPLEADO_KO:
			return new VBuscarEmpleado();

		case VMODIFICAR_EMPLEADO:
		case RES_MODIFICAR_EMPLEADO_OK:
		case RES_MODIFICAR_EMPLEADO_KO:
			return new VModificarEmpleado();

		case VMOSTRAR_TODOS_EMPLEADOS:
		case RES_MOSTRAR_TODOS_EMPLEADOS_OK:
		case RES_MOSTRAR_TODOS_EMPLEADOS_KO:
			return new VMostrarEmpleado();

		case VCALCULAR_MAS_VENDIDO:
		case RES_CALCULAR_MAS_VENDIDO_OK:
		case RES_CALCULAR_MAS_VENDIDO_KO:
			return new VCalcularImporteMasVendido();
		/*
		 * ============================== ======== PROVEEDOR ============
		 * ==============================
		 */
		case PROVEEDOR:
			return new GUIProveedor();

		case VALTA_PROVEEDOR:
		case RES_ALTA_PROVEEDOR_OK:
		case RES_ALTA_PROVEEDOR_KO:
			return new VAltaProveedor();

		case VBAJA_PROVEEDOR:
		case RES_BAJA_PROVEEDOR_OK:
		case RES_BAJA_PROVEEDOR_KO:
			return new VBajaProveedor();

		case VBUSCAR_PROVEEDOR:
		case RES_BUSCAR_PROVEEDOR_OK:
		case RES_BUSCAR_PROVEEDOR_KO:
			return new VBuscarProveedor();

		case VMODIFICAR_PROVEEDOR:
		case RES_MODIFICAR_PROVEEDOR_OK:
		case RES_MODIFICAR_PROVEEDOR_KO:
			return new VModificarProveedor();

		case VMOSTRAR_TODOS_PROVEEDORES:
		case RES_MOSTRAR_TODOS_PROVEEDORES_OK:
		case RES_MOSTRAR_TODOS_PROVEEDORES_KO:
			return new VMostrarProveedor();

		case VMOSTRAR_PROVEEDORES_POR_PRODUCTO:
		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_OK:
		case RES_MOSTRAR_PROVEEDORES_POR_PRODUCTO_KO:
			return new VVerPorProducto();

		case VVINCULAR_PRODUCTO_PROVEEDOR:
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_OK:
		case RES_VINCULAR_PRODUCTO_PROVEEDOR_KO:

			return new VVincularProveedor();

		case VDESVINCULAR_PRODUCTO_PROVEEDOR:
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_OK:
		case RES_DESVINCULAR_PRODUCTO_PROVEEDOR_KO:
			return new VDesvincularProveedor();

		case VPROVEEDOR_CON_MAS_UDS:
		case RES_PROVEEDOR_CON_MAS_UDS_OK:
		case RES_PROVEEDOR_CON_MAS_UDS_KO:
			return new VProveedorConMasUnidadesDeProductoVendidas();

		/*
		 * ============================== ======== PRODUCTO ============
		 * ==============================
		 */
		case PRODUCTO:
			return new GUIProducto();

		case VALTA_PRODUCTO:
		case RES_ALTA_PRODUCTO_OK:
		case RES_ALTA_PRODUCTO_KO:
			return new VAltaProducto();

		case VBAJA_PRODUCTO:
		case RES_BAJA_PRODUCTO_OK:
		case RES_BAJA_PRODUCTO_KO:
			return new VBajaProducto();

		case VMODIFICAR_PRODUCTO:
		case RES_MODIFICAR_PRODUCTO_OK:
		case RES_MODIFICAR_PRODUCTO_KO:
			return new VModificarProducto();

		case VBUSCAR_PRODUCTO:
		case RES_BUSCAR_PRODUCTO_OK:
		case RES_BUSCAR_PRODUCTO_KO:
			return new VBuscarProducto();

		case VMOSTRAR_TODOS_PRODUCTOS:
		case RES_MOSTRAR_TODOS_PRODUCTOS_OK:
		case RES_MOSTRAR_TODOS_PRODUCTOS_KO:
			return new VMostrarProducto();

		case VMOSTRAR_PRODUCTOS_POR_PROVEEDOR:
		case RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_OK:
		case RES_MOSTRAR_PRODUCTOS_POR_PROVEEDOR_KO:
			return new VVerProdPorProveedor();

		case VMOSTRAR_PRODUCTOS_POR_ALMACEN:
		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_OK:
		case RES_MOSTRAR_PRODUCTOS_POR_ALMACEN_KO:
			return new VVerProdPorAlmacen();

		/*
		 * ============================== ======== ALMACÉN ============
		 * ==============================
		 */
		case ALMACEN:
			return new GUIAlmacen();

		case VALTA_ALMACEN:
		case RES_ALTA_ALMACEN_OK:
		case RES_ALTA_ALMACEN_KO:
			return new VAltaAlmacen();

		case VBAJA_ALMACEN:
		case RES_BAJA_ALMACEN_OK:
		case RES_BAJA_ALMACEN_KO:
			return new VBajaAlmacen();

		case VBUSCAR_ALMACEN:
		case RES_BUSCAR_ALMACEN_OK:
		case RES_BUSCAR_ALMACEN_KO:
			return new VBuscarAlmacen();

		case VMODIFICAR_ALMACEN:
		case RES_MODIFICAR_ALMACEN_OK:
		case RES_MODIFICAR_ALMACEN_KO:
			return new VModificarAlmacen();

		case VMOSTRAR_TODOS_ALMACENES:
		case RES_MOSTRAR_TODOS_ALMACENES_OK:
		case RES_MOSTRAR_TODOS_ALMACENES_KO:
			return new VMostrarAlmacen();

		/*
		 * ============================== ======== VENTA ============
		 * ==============================
		 */
		// ==== VENTANA PRINCIPAL DE VENTA ====
		case VENTA:
			return new GUIVenta();

		// ==== CASOS DE USO ====

		case VABRIR_VENTA:
		case RES_ABRIR_VENTA_OK:
		case RES_ABRIR_VENTA_KO:
			return new VAbrirVenta();

		case VCERRAR_VENTA:
		case RES_CERRAR_VENTA_OK:
		case RES_CERRAR_VENTA_KO:
		case RES_PASAR_CARRITO_A_CERRAR_OK:
		case RES_PASAR_CARRITO_A_CERRAR_KO:
			return new VCerrarVenta();

		case VINSERTAR_PRODUCTO_VENTA:
		case RES_INSERTAR_PRODUCTO_VENTA_OK:
		case RES_INSERTAR_PRODUCTO_VENTA_KO:
		case RES_PASAR_CARRITO_A_INSERTAR_OK:
		case RES_PASAR_CARRITO_A_INSERTAR_KO:
			return new VAñadirProducto();

		case VQUITAR_PRODUCTO_VENTA:
		case RES_QUITAR_PRODUCTO_VENTA_OK:
		case RES_QUITAR_PRODUCTO_VENTA_KO:
		case RES_PASAR_CARRITO_A_ELIMINAR_OK:
		case RES_PASAR_CARRITO_A_ELIMINAR_KO:
			return new VEliminarProducto();

		case VMODIFICAR_VENTA:
		case RES_MODIFICAR_VENTA_OK:
		case RES_MODIFICAR_VENTA_KO:
			return new VModificarVenta();

		case VBUSCAR_VENTA:
		case RES_BUSCAR_VENTA_OK:
		case RES_BUSCAR_VENTA_KO:
			return new VBuscarVenta();

		case VMOSTRAR_TODAS_VENTAS:
		case RES_MOSTRAR_TODAS_VENTAS_OK:
		case RES_MOSTRAR_TODAS_VENTAS_KO:
			return new VListarVenta();

		case VMOSTRAR_VENTAS_POR_EMPLEADO:
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_OK:
		case RES_MOSTRAR_VENTAS_POR_EMPLEADO_KO:
			return new VMostrarPorEmpleado();

		case VMOSTRAR_VENTAS_POR_CLIENTE:
		case RES_MOSTRAR_VENTAS_POR_CLIENTE_OK:
		case RES_MOSTRAR_VENTAS_POR_CLIENTE_KO:
			return new VMostrarPorCliente();

		case VDEVOLVER_VENTA:
		case RES_DEVOLVER_VENTA_OK:
		case RES_DEVOLVER_VENTA_KO:
			return new VDevolverVenta();

		case VBAJA_VENTA:
		case RES_BAJA_VENTA_OK:
		case RES_BAJA_VENTA_KO:
			return new VBajaVenta();

		/*
		 * ============================== ======== RUTA ============
		 * ==============================
		 */
		case RUTA:
			return new GUIRuta();

		case VALTA_RUTA:
		case RES_ALTA_RUTA_OK:
		case RES_ALTA_RUTA_KO:
			return new VAltaRuta();

		case VBAJA_RUTA:
		case RES_BAJA_RUTA_OK:
		case RES_BAJA_RUTA_KO:
			return new VBajaRuta();

		case VBUSCAR_RUTA:
		case RES_BUSCAR_RUTA_OK:
		case RES_BUSCAR_RUTA_KO:
			return new VBuscarRuta();

		case VMODIFICAR_RUTA:
		case RES_MODIFICAR_RUTA_OK:
		case RES_MODIFICAR_RUTA_KO:
			return new VModificarRuta();

		case VMOSTRAR_TODAS_RUTAS:
		case RES_MOSTRAR_TODAS_RUTAS_OK:
		case RES_MOSTRAR_TODAS_RUTAS_KO:
			return new VMostrarRutas();
		case VAVINCULAR_RUTA_TRABAJADOR:
		case RES_VINCULAR_RUTA_TRABAJADOR_OK:
		case RES_VINCULAR_RUTA_TRABAJADOR_KO:
			return new VVincularRutaTrabajador();
		case VDESVINCULAR_RUTA_TRABAJADOR:
		case RES_DESVINCULAR_RUTA_TRABAJADOR_OK:
		case RES_DESVINCULAR_RUTA_TRABAJADOR_KO:
			return new VDesvincularRutaTrabajador();
		case VVER_RUTA_POR_TRABAJADOR:
		case RES_VER_RUTA_POR_TRABAJADOR_OK:
		case RES_VER_RUTA_POR_TRABAJADOR_KO:
			return new VVerRutasPorTrabajador();
		case VVER_TRABAJADOR_POR_RUTA:
		case RES_VER_TRABAJADOR_POR_RUTA_OK:
		case RES_VER_TRABAJADOR_POR_RUTA_KO:
			return new VVerTrabajadoresPorRuta();

		/*
		 * ============================== ======== TRANSPORTE ============
		 * ==============================
		 */
		case TRANSPORTE:
			return new GUITransporte();
		case VALTA_TRANSPORTE:
		case RES_ALTA_TRANSPORTE_OK:
		case RES_ALTA_TRANSPORTE_KO:
			return new VAltaTransporte();
		case VBAJA_TRANSPORTE:
		case RES_BAJA_TRANSPORTE_OK:
		case RES_BAJA_TRANSPORTE_KO:
			return new VBajaTransporte();
		case VBUSCAR_TRANSPORTE:
		case RES_BUSCAR_TRANSPORTE_OK:
		case RES_BUSCAR_TRANSPORTE_KO:
			return new VBuscarTransporte();
		case VMODIFICAR_TRANSPORTE:
		case RES_MODIFICAR_TRANSPORTE_OK:
		case RES_MODIFICAR_TRANSPORTE_KO:
			return new VModificarTransporte();
		case VMOSTRAR_TODOS_TRANSPORTES:
		case RES_MOSTRAR_TODOS_TRANSPORTES_OK:
		case RES_MOSTRAR_TODOS_TRANSPORTES_KO:
			return new VMostrarTransporte();
		case VVER_TRANSPORTE_POR_TRABAJADOR:
		case RES_VER_TRANSPORTE_POR_TRABAJADOR_OK:
		case RES_VER_TRANSPORTE_POR_TRABAJADOR_KO:
			return new VVerTransportePorTrabajador();
		case VVINCULAR_TRANSPORTE_TRABAJADOR:
		case RES_VINCULAR_TRANSPORTE_TRABAJADOR_OK:
		case RES_VINCULAR_TRANSPORTE_TRABAJADOR_KO:
			return new VVincularTransporteTrabajador();
		case VDESVINCULAR_TRANSPORTE_TRABAJADOR:
		case RES_DESVINCULAR_TRANSPORTE_TRABAJADOR_OK:
		case RES_DESVINCULAR_TRANSPORTE_TRABAJADOR_KO:
			return new VDesvincularTransporteTrabajador();

		/*
		 * ============================== ======== REMITENTE ============
		 * ==============================
		 */
		case REMITENTE:
			return new GUIRemitente();
		case VALTA_REMITENTE:
		case RES_ALTA_REMITENTE_OK:
		case RES_ALTA_REMITENTE_KO:
			return new VAltaRemitente();
		case VBAJA_REMITENTE:
		case RES_BAJA_REMITENTE_OK:
		case RES_BAJA_REMITENTE_KO:
			return new VBajaRemitente();
		case VBUSCAR_REMITENTE:
		case RES_BUSCAR_REMITENTE_OK:
		case RES_BUSCAR_REMITENTE_KO:
			return new VBuscarRemitente();
		case VMODIFICAR_REMITENTE:
		case RES_MODIFICAR_REMITENTE_OK:
		case RES_MODIFICAR_REMITENTE_KO:
			return new VModificarRemitente();
		case VMOSTRAR_TODOS_REMITENTES:
		case RES_MOSTRAR_TODOS_REMITENTES_OK:
		case RES_MOSTRAR_TODOS_REMITENTES_KO:
			return new VMostrarRemitente();
		case VCALCULAR_PRECIO_PAQUETES:
		case RES_CALCULAR_PRECIO_PAQUETES_OK:
		case RES_CALCULAR_PRECIO_PAQUETES_KO:
			return new VCalcularPrecioPaquete();
		
			/*
			 * ============================== ======== PAQUETE ==============================
			 */
		case PAQUETE:
			return new GUIPaquete();
		case VALTA_PAQUETE:
		case RES_ALTA_PAQUETE_OK:
		case RES_ALTA_PAQUETE_KO:
			return new VAltaPaquete();
		case VBAJA_PAQUETE:
		case RES_BAJA_PAQUETE_OK:
		case RES_BAJA_PAQUETE_KO:
			return new VBajaPaquete();
		case VBUSCAR_PAQUETE:
		case RES_BUSCAR_PAQUETE_OK:
		case RES_BUSCAR_PAQUETE_KO:
			return new VBuscarPaquete();
		case VMODIFICAR_PAQUETE:
		case RES_MODIFICAR_PAQUETE_OK:
		case RES_MODIFICAR_PAQUETE_KO:
			return new VModificarPaquete();
		case VMOSTRAR_TODOS_PAQUETES:
		case RES_MOSTRAR_TODOS_PAQUETES_OK:
		case RES_MOSTRAR_TODOS_PAQUETES_KO:
			return new VMostrarPaquetes();
		case VMOSTRAR_PAQUETES_POR_FACTURA:
		case RES_VER_PAQUETES_POR_FACTURA_OK:
		case RES_VER_PAQUETES_POR_FACTURA_KO:
			return new VVerPaquetesPorFactura();
		case VMOSTRAR_PAQUETES_POR_RUTA:
		case RES_VER_PAQUETES_POR_RUTA_OK:
		case RES_VER_PAQUETES_POR_RUTA_KO:
			return new VVerPaquetesPorRuta();
			
			/*
			 * ============================== ======== FACTURA ==============================
			 */
		case FACTURA:
			return new GUIFactura();
		case VABRIR_FACTURA:
		case RES_ABRIR_FACTURA_OK:
		case RES_ABRIR_FACTURA_KO:
			return new VAbrirFactura();
		case VCERRAR_FACTURA:
		case RES_CERRAR_FACTURA_OK:
		case RES_CERRAR_FACTURA_KO:
		case RES_PASAR_CARRITOFACTURA_A_CERRAR_OK:
		case RES_PASAR_CARRITOFACTURA_A_CERRAR_KO:
			return new VCerrarFactura();
		case VINSERTAR_PAQUETE_FACTURA:
		case RES_INSERTAR_PAQUETE_FACTURA_OK:
		case RES_INSERTAR_PAQUETE_FACTURA_KO:
		case RES_PASAR_CARRITOFACTURA_A_INSERTAR_OK:
		case RES_PASAR_CARRITOFACTURA_A_INSERTAR_KO:
			return new VAnyadirPaquete();
		case VQUITAR_PAQUETE_FACTURA:
		case RES_QUITAR_PAQUETE_FACTURA_OK:
		case RES_QUITAR_PAQUETE_FACTURA_KO:
		case RES_PASAR_CARRITOFACTURA_A_ELIMINAR_OK:
		case RES_PASAR_CARRITOFACTURA_A_ELIMINAR_KO:
			return new VEliminarPaquete();
		case VMODIFICAR_FACTURA:
		case RES_MODIFICAR_FACTURA_OK:
		case RES_MODIFICAR_FACTURA_KO:
			return new VModificarFactura();
		case VBUSCAR_FACTURA:
		case RES_BUSCAR_FACTURA_OK:
		case RES_BUSCAR_FACTURA_KO:
			return new VBuscarFactura();
		case VMOSTRAR_TODAS_FACTURAS:
		case RES_MOSTRAR_TODAS_FACTURAS_OK:
		case RES_MOSTRAR_TODAS_FACTURAS_KO:
			return new VMostrarFactura();
		case VVER_FACTURAS_POR_REMITENTE:
		case RES_VER_FACTURAS_POR_REMITENTE_OK:
		case RES_VER_FACTURAS_POR_REMITENTE_KO:
			return new VListarFacturasPorRemitente();
		case VDEVOLUCION:
		case RES_DEVOLUCION_OK:
		case RES_DEVOLUCION_KO:
			return new VDevolucion();


		default:
			return null;
		}

	}
}
