
package negocio.Venta;

import java.util.Set;

public interface SAVenta {

	public TCarrito abrirVenta (int idEmpleado);
	
	public int procesarCarrito (TCarrito carrito);
	
	public int insertarProductoCarrito (TCarrito carrito);
	
	public int eliminarProductoCarrito (TCarrito carrito);
	
	public int cerrarVenta (TCarrito carrito);
	
	public int devolverVenta (TLineaVenta lineaVenta);
	
	public Set<TVenta> leerVentasPorEmpleado(Integer idEmpleado);

	public Integer bajaVenta(Integer id);

	public int modificarVenta(TVenta venta);

	public TVentaTOA leerVenta(int id);

	public Set<TVenta> leerTodasVentas();

	public Set<TVenta> leerVentasPorCliente(Integer idCliente);
}