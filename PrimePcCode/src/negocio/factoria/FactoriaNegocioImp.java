package negocio.factoria;

import negocio.serviciosAplicacion.SAAlmacen;
import negocio.serviciosAplicacion.SAAlmacenImp;
import negocio.serviciosAplicacion.SACliente;
import negocio.serviciosAplicacion.SAClienteImp;
import negocio.serviciosAplicacion.SAEmpleado;
import negocio.serviciosAplicacion.SAEmpleadoImp;
import negocio.serviciosAplicacion.SAProducto;
import negocio.serviciosAplicacion.SAProductoImp;
import negocio.serviciosAplicacion.SAProveedor;
import negocio.serviciosAplicacion.SAProveedorImp;
import negocio.serviciosAplicacion.SAVenta;
import negocio.serviciosAplicacion.SAVentaImp;

public class FactoriaNegocioImp extends FactoriaNegocio {

	@Override
	public SAAlmacen generaSAAlmacen() {
		return new SAAlmacenImp();
	}

	@Override
	public SACliente generaSACliente() {
		return new SAClienteImp();
	}

	@Override
	public SAEmpleado generaSAEmpleado() {
		return new SAEmpleadoImp();
	}

	@Override
	public SAProducto generaSAProducto() {
		return new SAProductoImp();
	}

	@Override
	public SAProveedor generaSAProveedor() {
		return new SAProveedorImp();
	}

	@Override
	public SAVenta generaSAVenta() {
		return new SAVentaImp();
	}

}
