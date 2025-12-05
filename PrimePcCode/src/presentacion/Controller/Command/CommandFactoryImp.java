
package presentacion.Controller.Command;

import presentacion.Controller.Command.CommandAlmacen.AltaAlmacenCommand;
import presentacion.Controller.Command.CommandAlmacen.BajaAlmacenCommand;
import presentacion.Controller.Command.CommandAlmacen.BuscarAlmacenCommand;
import presentacion.Controller.Command.CommandAlmacen.ModificarAlmacenCommand;
import presentacion.Controller.Command.CommandAlmacen.MostrarAlmacenesCommand;
import presentacion.Controller.Command.CommandCliente.AltaClienteCommand;
import presentacion.Controller.Command.CommandCliente.BajaClienteCommand;
import presentacion.Controller.Command.CommandCliente.BuscarClienteCommand;
import presentacion.Controller.Command.CommandCliente.ModificarClienteCommand;
import presentacion.Controller.Command.CommandCliente.MostrarClientesCommand;
import presentacion.Controller.Command.CommandEmpleado.AltaEmpleadoCommand;
import presentacion.Controller.Command.CommandEmpleado.BajaEmpleadoCommand;
import presentacion.Controller.Command.CommandEmpleado.BuscarEmpleadoCommand;
import presentacion.Controller.Command.CommandEmpleado.CalcularMasVendidoCommand;
import presentacion.Controller.Command.CommandEmpleado.ModificarEmpleadoCommand;
import presentacion.Controller.Command.CommandEmpleado.MostrarEmpleadosCommand;
import presentacion.Controller.Command.CommandProducto.AltaProductoCommand;
import presentacion.Controller.Command.CommandProducto.BajaProductoCommand;
import presentacion.Controller.Command.CommandProducto.BuscarProductoCommand;
import presentacion.Controller.Command.CommandProducto.ModificarProductoCommand;
import presentacion.Controller.Command.CommandProducto.MostrarProductosCommand;
import presentacion.Controller.Command.CommandProducto.VerProdPorAlmacenCommand;
import presentacion.Controller.Command.CommandProducto.VerProdPorProveedorCommand;
import presentacion.Controller.Command.CommandProveedor.AltaProveedorCommand;
import presentacion.Controller.Command.CommandProveedor.BajaProveedorCommand;
import presentacion.Controller.Command.CommandProveedor.BuscarProveedorCommand;
import presentacion.Controller.Command.CommandProveedor.DesvincularProveedorProductoCommand;
import presentacion.Controller.Command.CommandProveedor.ModificarProveedorCommand;
import presentacion.Controller.Command.CommandProveedor.MostrarProveedoresCommand;
import presentacion.Controller.Command.CommandProveedor.ProveedorConMasUnidadesDeProductoVendidasCommand;
import presentacion.Controller.Command.CommandProveedor.VerProvPorProductoCommand;
import presentacion.Controller.Command.CommandProveedor.VincularProveedorProductoCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.AltaRemitenteCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.BajaRemitenteCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.BuscarRemitenteCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.CalcularPrecioPaquetesRemitenteCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.ModificarRemitenteCommand;
import presentacion.Controller.Command.CommandRemitenteJPA.MostrarRemitentesCommand;
import presentacion.Controller.Command.CommandTransporteJPA.AltaTransporteCommand;
import presentacion.Controller.Command.CommandTransporteJPA.BajaTransporteCommand;
import presentacion.Controller.Command.CommandTransporteJPA.BuscarTransporteCommand;
import presentacion.Controller.Command.CommandTransporteJPA.DesvincularTransporteTrabajdorCommand;
import presentacion.Controller.Command.CommandTransporteJPA.ModificarTransporteCommand;
import presentacion.Controller.Command.CommandTransporteJPA.MostrarTransportesCommand;
import presentacion.Controller.Command.CommandTransporteJPA.VerTransportesPorTrabajador;
import presentacion.Controller.Command.CommandTransporteJPA.VincularTransporteTrabajadorCommand;
import presentacion.Controller.Command.CommandVenta.AbrirVentaCommand;
import presentacion.Controller.Command.CommandVenta.BajaVentaCommand;
import presentacion.Controller.Command.CommandVenta.BuscarVentaCommand;
import presentacion.Controller.Command.CommandVenta.CerrarVentaCommand;
import presentacion.Controller.Command.CommandVenta.DevolverVentaCommand;
import presentacion.Controller.Command.CommandVenta.InsertarProductoVentaCommand;
import presentacion.Controller.Command.CommandVenta.ModificarVentaCommand;
import presentacion.Controller.Command.CommandVenta.MostrarVentasCommand;
import presentacion.Controller.Command.CommandVenta.PasarCarritoACerrarCommand;
import presentacion.Controller.Command.CommandVenta.PasarCarritoAEliminarCommand;
import presentacion.Controller.Command.CommandVenta.PasarCarritoAInsertarCommand;
import presentacion.Controller.Command.CommandVenta.QuitarProductoVentaCommand;
import presentacion.Controller.Command.CommandVenta.VerVentasPorClienteCommand;
import presentacion.Controller.Command.CommandVenta.VerVentasPorEmpleadoCommand;
import presentacion.GUI.Evento;

public class CommandFactoryImp extends CommandFactory {

	@Override
	public Command getCommand(Evento evento) {
		Command command = null;

		switch (evento) {
		// ---- ALMACEN ----
		case ALTA_ALMACEN:
			command = new AltaAlmacenCommand();
			break;
		case BAJA_ALMACEN:
			command = new BajaAlmacenCommand();
			break;
		case MODIFICAR_ALMACEN:
			command = new ModificarAlmacenCommand();
			break;
		case BUSCAR_ALMACEN:
			command = new BuscarAlmacenCommand();
			break;
		case MOSTRAR_TODOS_ALMACENES:
			command = new MostrarAlmacenesCommand();
			break;

		// ---- CLIENTE ----
		case ALTA_CLIENTE:
			command = new AltaClienteCommand();
			break;
		case BAJA_CLIENTE:
			command = new BajaClienteCommand();
			break;
		case MODIFICAR_CLIENTE:
			command = new ModificarClienteCommand();
			break;
		case BUSCAR_CLIENTE:
			command = new BuscarClienteCommand();
			break;
		case MOSTRAR_TODOS_CLIENTES:
			command = new MostrarClientesCommand();
			break;

		// ---- EMPLEADO ----
		case ALTA_EMPLEADO:
			command = new AltaEmpleadoCommand();
			break;
		case BAJA_EMPLEADO:
			command = new BajaEmpleadoCommand();
			break;
		case MODIFICAR_EMPLEADO:
			command = new ModificarEmpleadoCommand();
			break;
		case BUSCAR_EMPLEADO:
			command = new BuscarEmpleadoCommand();
			break;
		case MOSTRAR_TODOS_EMPLEADOS:
			command = new MostrarEmpleadosCommand();
			break;
		case CALCULAR_MAS_VENDIDO:
			command = new CalcularMasVendidoCommand();
			break;

		// ---- PRODUCTO ----
		case ALTA_PRODUCTO:
			command = new AltaProductoCommand();
			break;
		case BAJA_PRODUCTO:
			command = new BajaProductoCommand();
			break;
		case MODIFICAR_PRODUCTO:
			command = new ModificarProductoCommand();
			break;
		case BUSCAR_PRODUCTO:
			command = new BuscarProductoCommand();
			break;
		case MOSTRAR_TODOS_PRODUCTOS:
			command = new MostrarProductosCommand();
			break;
		case MOSTRAR_PRODUCTOS_POR_PROVEEDOR:
			command = new VerProdPorProveedorCommand();
			break;
		case MOSTRAR_PRODUCTOS_POR_ALMACEN:
			command = new VerProdPorAlmacenCommand();
			break;

		// ---- PROVEEDOR ----
		case ALTA_PROVEEDOR:
			command = new AltaProveedorCommand();
			break;
		case BAJA_PROVEEDOR:
			command = new BajaProveedorCommand();
			break;
		case MODIFICAR_PROVEEDOR:
			command = new ModificarProveedorCommand();
			break;
		case BUSCAR_PROVEEDOR:
			command = new BuscarProveedorCommand();
			break;
		case MOSTRAR_TODOS_PROVEEDORES:
			command = new MostrarProveedoresCommand();
			break;
		case MOSTRAR_PROVEEDORES_POR_PRODUCTO:
			command = new VerProvPorProductoCommand();
			break;
		case VINCULAR_PRODUCTO_PROVEEDOR:
			command = new VincularProveedorProductoCommand();
			break;
		case DESVINCULAR_PRODUCTO_PROVEEDOR:
			command = new DesvincularProveedorProductoCommand();
			break;
		case PROVEEDOR_CON_MAS_UDS:
			command = new ProveedorConMasUnidadesDeProductoVendidasCommand();
			break;

		// ---- VENTA ----
		case ABRIR_VENTA:
			command = new AbrirVentaCommand();
			break;
		case CERRAR_VENTA:
			command = new CerrarVentaCommand();
			break;
		case INSERTAR_PRODUCTO_VENTA:
			command = new InsertarProductoVentaCommand();
			break;
		case QUITAR_PRODUCTO_VENTA:
			command = new QuitarProductoVentaCommand();
			break;
		case PASAR_CARRITO_A_INSERTAR:
			command = new PasarCarritoAInsertarCommand();
			break;
		case PASAR_CARRITO_A_ELIMINAR:
			command = new PasarCarritoAEliminarCommand();
			break;
		case PASAR_CARRITO_A_CERRAR:
			command = new PasarCarritoACerrarCommand();
			break;
		case BAJA_VENTA:
			command = new BajaVentaCommand();
			break;
		case MODIFICAR_VENTA:
			command = new ModificarVentaCommand();
			break;
		case BUSCAR_VENTA:
			command = new BuscarVentaCommand();
			break;
		case MOSTRAR_TODAS_VENTAS:
			command = new MostrarVentasCommand();
			break;
		case MOSTRAR_VENTAS_POR_CLIENTE:
			command = new VerVentasPorClienteCommand();
			break;
		case MOSTRAR_VENTAS_POR_EMPLEADO:
			command = new VerVentasPorEmpleadoCommand();
			break;
		case DEVOLVER_VENTA:
			command = new DevolverVentaCommand();
			break;

		// --- TRANSPORTE ---
		case ALTA_TRANSPORTE:
			command = new AltaTransporteCommand();
			break;
		case BAJA_TRANSPORTE:
			command = new BajaTransporteCommand();
			break;
		case MODIFICAR_TRANSPORTE:
			command = new ModificarTransporteCommand();
			break;
		case BUSCAR_TRANSPORTE:
			command = new BuscarTransporteCommand();
			break;
		case MOSTRAR_TODOS_TRANSPORTES:
			command = new MostrarTransportesCommand();
			break;
		case VER_TRANSPORTE_POR_TRABAJADOR:
			command = new VerTransportesPorTrabajador();
			break;
		case VINCULAR_TRANSPORTE_TRABAJADOR:
			command = new VincularTransporteTrabajadorCommand();
			break;
		case DESVINCULAR_TRANSPORTE_TRABAJADOR:
			command = new DesvincularTransporteTrabajdorCommand();
			break;

		// --- REMITENTE ---
		case ALTA_REMITENTE:
			command = new AltaRemitenteCommand();
			break;
		case BAJA_REMITENTE:
			command = new BajaRemitenteCommand();
			break;
		case MODIFICAR_REMITENTE:
			command = new ModificarRemitenteCommand();
			break;
		case BUSCAR_REMITENTE:
			command = new BuscarRemitenteCommand();
			break;
		case MOSTRAR_TODOS_REMITENTES:
			command = new MostrarRemitentesCommand();
			break;
		case CALCULAR_PRECIO_PAQUETES:
			command = new CalcularPrecioPaquetesRemitenteCommand();
			break;

			
		
			
		default:
			break;
		}
		return command;
	}
}
