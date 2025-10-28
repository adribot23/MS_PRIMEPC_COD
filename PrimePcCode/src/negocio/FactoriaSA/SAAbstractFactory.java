
package Negocio.FactoriaSA;

import Negocio.Almacen.SAAlmacen;
import Negocio.Cliente.SACliente;
import Negocio.Empleado.SAEmpleado;
import Negocio.Venta.SAVenta;
import Negocio.Producto.SAProducto;
import Negocio.Proveedor.SAProveedor;


public abstract class SAAbstractFactory {

	private static SAAbstractFactory instancia;

	public static SAAbstractFactory getInstancia() {
		if (instancia == null) {
			instancia = new SAAbstractFactoryImp();
		}
		return instancia;
	}

	
	public abstract SAAlmacen generarSAAlmacen();

	
	public abstract SACliente generarSACliente();


	public abstract SAEmpleado generarSAEmpleado();

	public abstract SAVenta generarSAVenta();

	public abstract SAProducto generarSAProducto();

	public abstract SAProveedor generarSAProveedor();
}