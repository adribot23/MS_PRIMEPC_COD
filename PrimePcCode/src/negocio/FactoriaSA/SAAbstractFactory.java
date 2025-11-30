
package negocio.FactoriaSA;

import negocio.Almacen.SAAlmacen;
import negocio.Cliente.SACliente;
import negocio.Empleado.SAEmpleado;
import negocio.PaqueteJPA.SAPaquete;
import negocio.Producto.SAProducto;
import negocio.Proveedor.SAProveedor;
import negocio.RemitenteJPA.SARemitente;
import negocio.TrabajadorJPA.SATrabajador;
import negocio.TransporteJPA.SATransporte;
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

	// JPA

	public abstract SARemitente generarSARemitente();

	public abstract SAPaquete generarSAPaquete();

	public abstract SATransporte generarSATransporte();

	public abstract SATrabajador generarSATrabajador();

}