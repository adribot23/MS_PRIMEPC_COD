
package negocio.FactoriaSA;

import negocio.Almacen.SAAlmacen;
import negocio.Almacen.SAAlmacenImp;
import negocio.Cliente.SACliente;
import negocio.Cliente.SAClienteImp;
import negocio.Empleado.SAEmpleado;
import negocio.Empleado.SAEmpleadoImp;
import negocio.Producto.SAProducto;
import negocio.Producto.SAProductoImp;
import negocio.Proveedor.SAProveedor;
import negocio.Proveedor.SAProveedorImp;
import negocio.Venta.SAVenta;
import negocio.Venta.SAVentaImp;

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