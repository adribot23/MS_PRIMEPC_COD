
package negocio.Venta;

import java.util.Set;

public interface SAVenta {

	public TCarrito abrirVenta(int idEmpleado);

	public int procesarCarrito(TCarrito carrito);

	public int pasar_carrito(TCarrito carrito);

	public int insertarProductoCarrito(TCarrito carrito);

	public int eliminarProductoCarrito(TCarrito carrito);

	public int cerrarVenta(TCarrito carrito);

	public int devolverVenta(TLineaVenta lineaVenta);

	public Set<TVenta> leerVentasPorEmpleado(int idEmpleado);

	public int modificarVenta(TVenta venta);

	public TVentaTOA leerVenta(int id);

	public Set<TVenta> leerTodasVentas();

	public Set<TVenta> leerVentasPorCliente(int idCliente);

	public int eliminar_venta(int idVenta);
}