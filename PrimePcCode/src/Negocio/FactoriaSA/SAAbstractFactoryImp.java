
package Negocio.FactoriaSA;

import Negocio.Almacen.SAAlmacen;
import Negocio.Almacen.SAAlmacenImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;
import Negocio.Empleado.SAEmpleado;
import Negocio.Empleado.SAEmpleadoImp;
import Negocio.Venta.SAVenta;
import Negocio.Venta.SAVentaImp;
import Negocio.Producto.SAProducto;
import Negocio.Producto.SAProductoImp;
import Negocio.Proveedor.SAProveedor;
import Negocio.Proveedor.SAProveedorImp;

public class SAAbstractFactoryImp extends SAAbstractFactory {
	public SAAlmacen generarSAAlmacen() {
		return new SAAlmacenImp();
	}

	public SACliente generarSACliente() {
		return new SAClienteImp();
	}

	public SAEmpleado generarSAEmpleado() {
		return new SAEmpleadoImp();
	}

	public SAVenta generarSAVenta() {
		return new SAVentaImp();
	}

	public SAProducto generarSAProducto() {
		return new SAProductoImp();
	}

	public SAProveedor generarSAProveedor() {
		return new SAProveedorImp();
	}
}