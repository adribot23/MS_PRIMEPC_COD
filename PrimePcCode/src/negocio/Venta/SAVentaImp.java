
package negocio.Venta;

import java.util.HashSet;
import java.util.Set;

public class SAVentaImp implements SAVenta {

	@Override
	public TCarrito abrirVenta(int idEmpleado) {
		
		TCarrito carrito = new TCarrito();
		
		carrito.setLineasVenta(new HashSet<TLineaVenta>());
		
		TVenta venta = new TVenta();
		
		venta.setIdEmpleado(idEmpleado);
		
		carrito.setVenta(venta);
		
		return carrito;
		
	}

	@Override
	public int procesarCarrito(TCarrito carrito) {
		
		if (carrito == null || carrito.getLineasVenta().isEmpty()) {
			return -1;
		}
		
		else {
			return 1;
		}

		
		
	}

	@Override
	public int insertarProductoCarrito(TCarrito carrito) {
		
		
		int idProducto = carrito.getidProducto();
		
		int cantidad = carrito.getcantidadProducto();
		
		Set<TLineaVenta> lineasVenta = carrito.getLineasVenta();
		
		TLineaVenta lineaVenta = new TLineaVenta();
		
		lineaVenta = buscarLineaVentaPorIdProducto(lineasVenta, idProducto);
		
		if (lineaVenta != null) {
			
			lineaVenta.set_num_unidades(lineaVenta.get_num_unidades() + cantidad);
			
		} else {
			
			lineaVenta = new TLineaVenta();
			
			lineaVenta.set_producto(idProducto);
			
			lineaVenta.set_num_unidades(cantidad);
			
			lineasVenta.add(lineaVenta);
			
		}
		
		return 1;
		
	}

	private TLineaVenta buscarLineaVentaPorIdProducto(Set<TLineaVenta> lineasVenta, int idProducto) {
		
		
		for (TLineaVenta lineaVenta : lineasVenta) {
			if (lineaVenta.get_producto() == idProducto) {
				return lineaVenta;
			}
		}
		return null;
	}

	@Override
	public int eliminarProductoCarrito(TCarrito carrito) {
		
		int idProducto = carrito.getidProducto();
		
		int cantidad = carrito.getcantidadProducto();
		
		Set<TLineaVenta> lineasVenta = carrito.getLineasVenta();
		
		TLineaVenta lineaVenta = new TLineaVenta();
		
		lineaVenta = buscarLineaVentaPorIdProducto(lineasVenta, idProducto);
		
		if (lineaVenta != null) {
			
			int nuevaCantidad = lineaVenta.get_num_unidades() - cantidad;
			
			if (nuevaCantidad > 0) {
				lineaVenta.set_num_unidades(nuevaCantidad);
			} else {
				lineasVenta.remove(lineaVenta);
			}
			
			
		} else {
			return -1;
		}
		
		return 1;
	}

	@Override
	public int cerrarVenta(TCarrito carrito) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int devolverVenta(TLineaVenta lineaVenta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<TVenta> leerVentasPorEmpleado(Integer idEmpleado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer bajaVenta(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modificarVenta(TVenta venta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TVentaTOA leerVenta(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVenta> leerTodasVentas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TVenta> leerVentasPorCliente(Integer idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	
}