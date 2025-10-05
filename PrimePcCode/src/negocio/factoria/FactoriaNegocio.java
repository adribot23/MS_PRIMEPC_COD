package negocio.factoria;

import negocio.serviciosAplicacion.SAAlmacen;
import negocio.serviciosAplicacion.SACliente;
import negocio.serviciosAplicacion.SAEmpleado;
import negocio.serviciosAplicacion.SAProducto;
import negocio.serviciosAplicacion.SAProveedor;
import negocio.serviciosAplicacion.SAVenta;

public abstract class FactoriaNegocio {
	private static FactoriaNegocio instancia;

	public static FactoriaNegocio obtenerInstancia() {
		if (instancia == null)
			instancia = new FactoriaNegocioImp();
		return instancia;
	}

	public abstract SAAlmacen generaSAAlmacen();

	public abstract SACliente generaSACliente();

	public abstract SAEmpleado generaSAEmpleado();

	public abstract SAProducto generaSAProducto();

	public abstract SAProveedor generaSAProveedor();

	public abstract SAVenta generaSAVenta();
}
