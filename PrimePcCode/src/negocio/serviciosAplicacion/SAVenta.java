package negocio.serviciosAplicacion;

import java.util.Collection;
import java.util.Set;

import negocio.transfers.TCarrito;
import negocio.transfers.TLineaVenta;
import negocio.transfers.TVenta;
import negocio.transfers.TVentaCompletaTOA;

public interface SAVenta {

	public TCarrito abrirVenta(int idEmpleado);

	public int cerrarVenta(TCarrito carrito);

	public int insertarProductoCarrito(TCarrito carrito);

	public int eliminarProductoCarrito(TCarrito carrito);

	public TVentaCompletaTOA leerVenta(int venta);

	public int modificarVenta(TVenta tVenta);

	public Collection<TVentaCompletaTOA> leerTodasVentas();

	public Collection<TVentaCompletaTOA> leerVentasPorEmpleado(int idEmpleado);

	public Collection<TVentaCompletaTOA> leerVentasPorCliente(int idCliente);

	public int bajaVenta(int id);

	public int devolverVenta(TLineaVenta tLineaVenta);


}
