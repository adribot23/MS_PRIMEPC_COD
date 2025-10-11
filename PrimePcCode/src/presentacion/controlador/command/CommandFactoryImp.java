package presentacion.controlador.command;


import presentacion.controlador.command.commandAlmacen.AltaAlmacenCommand;
import presentacion.controlador.command.commandAlmacen.BajaAlmacenCommand;
import presentacion.controlador.command.commandAlmacen.BuscarAlmacenCommand;
import presentacion.controlador.command.commandAlmacen.ModificarAlmacenCommand;
import presentacion.controlador.command.commandAlmacen.MostrarAlmacenesCommand;
import presentacion.controlador.command.commandCliente.AltaClienteCommand;
import presentacion.controlador.command.commandCliente.BajaClienteCommand;
import presentacion.controlador.command.commandCliente.BuscarClienteCommand;
import presentacion.controlador.command.commandCliente.ModificarClienteCommand;
import presentacion.controlador.command.commandCliente.MostrarClientesCommand;
import presentacion.controlador.command.commandEmpleado.AltaEmpleadoCommand;
import presentacion.controlador.command.commandEmpleado.BajaEmpleadoCommand;
import presentacion.controlador.command.commandEmpleado.BuscarEmpleadoCommand;
import presentacion.controlador.command.commandEmpleado.ModificarEmpleadoCommand;
import presentacion.controlador.command.commandEmpleado.MostrarEmpleadosCommand;
import presentacion.controlador.command.commandProducto.AltaProductoCommand;
import presentacion.controlador.command.commandProducto.BajaProductoCommand;
import presentacion.controlador.command.commandProducto.BuscarProductoCommand;
import presentacion.controlador.command.commandProducto.ModificarProductoCommand;
import presentacion.controlador.command.commandProducto.MostrarProductosCommand;
import presentacion.controlador.command.commandProducto.VerProdPorAlmacenCommand;
import presentacion.controlador.command.commandProducto.VerProdPorProveedorCommand;
import presentacion.controlador.command.commandProveedor.AltaProveedorCommand;
import presentacion.controlador.command.commandProveedor.BajaProveedorCommand;
import presentacion.controlador.command.commandProveedor.BuscarProveedorCommand;
import presentacion.controlador.command.commandProveedor.DesvincularProveedorProductoCommand;
import presentacion.controlador.command.commandProveedor.ModificarProveedorCommand;
import presentacion.controlador.command.commandProveedor.MostrarProveedoresCommand;
import presentacion.controlador.command.commandProveedor.VerProvPorProductoCommand;
import presentacion.controlador.command.commandProveedor.VincularProveedorProductoCommand;
import presentacion.controlador.command.commandVenta.AbrirVentaCommand;
import presentacion.controlador.command.commandVenta.BuscarVentaCommand;
import presentacion.controlador.command.commandVenta.CerrarVentaCommand;
import presentacion.controlador.command.commandVenta.DevolverVentaCommand;
import presentacion.controlador.command.commandVenta.InsertarProductoVentaCommand;
import presentacion.controlador.command.commandVenta.ModificarVentaCommand;
import presentacion.controlador.command.commandVenta.MostrarVentasCommand;
import presentacion.controlador.command.commandVenta.QuitarProductoVentaCommand;
import presentacion.controlador.command.commandVenta.VerVentasPorClienteCommand;
import presentacion.controlador.command.commandVenta.VerVentasPorEmpleadoCommand;
import presentacion.factoria.Evento;

public class CommandFactoryImp extends CommandFactory {

	 @Override
	    public Command getCommand(Evento event) {
	        Command command = null;

	        switch (event) {
	            // ---- ALMACÉN ----
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
	            default:
	                break;
	        }

	        return command;
	    }
	}