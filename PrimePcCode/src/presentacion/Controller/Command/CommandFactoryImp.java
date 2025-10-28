
package Presentacion.Controller.Command;

import Presentacion.GUI.Evento;

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
}