
package negocio.FactoriaSA;

import negocio.Almacen.SAAlmacen;
import negocio.Almacen.SAAlmacenImp;
import negocio.Cliente.SACliente;
import negocio.Cliente.SAClienteImp;
import negocio.Empleado.SAEmpleado;
import negocio.Empleado.SAEmpleadoImp;
import negocio.FacturaJPA.SAFactura;
import negocio.FacturaJPA.SAFacturaImp;
import negocio.PaqueteJPA.SAPaquete;
import negocio.PaqueteJPA.SAPaqueteImp;
import negocio.Producto.SAProducto;
import negocio.Producto.SAProductoImp;
import negocio.Proveedor.SAProveedor;
import negocio.Proveedor.SAProveedorImp;
import negocio.RemitenteJPA.SARemitente;
import negocio.RemitenteJPA.SARemitenteImp;
import negocio.TrabajadorJPA.SATrabajador;
import negocio.TrabajadorJPA.SATrabajadorImp;
import negocio.TransporteJPA.SATransporte;
import negocio.TransporteJPA.SATransporteImp;
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

	// JPA
	public SARemitente generarSARemitente() {
		return new SARemitenteImp();
	}

	@Override
	public SAPaquete generarSAPaquete() {
		return new SAPaqueteImp();
	}

	@Override
	public SATransporte generarSATransporte() {
		return new SATransporteImp();
	}

	@Override
	public SATrabajador generarSATrabajador() {

		return new SATrabajadorImp();
	}
	
	public SAFactura generarSAFactura() {
		return new SAFacturaImp();
	}
}