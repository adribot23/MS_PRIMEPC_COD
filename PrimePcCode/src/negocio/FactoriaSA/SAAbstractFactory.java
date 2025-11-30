
package negocio.FactoriaSA;

import negocio.Almacen.SAAlmacen;
import negocio.Cliente.SACliente;
import negocio.Empleado.SAEmpleado;
import negocio.Producto.SAProducto;
import negocio.Proveedor.SAProveedor;
import negocio.RemitenteJPA.SARemitente;
import negocio.Venta.SAVenta;

public abstract class SAAbstractFactory {

	private static SAAbstractFactory instancia;

	public static synchronized SAAbstractFactory getInstancia() {
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
	
	public abstract SARemitente generarSARemitente();
}