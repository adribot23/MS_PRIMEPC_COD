package negocio.serviciosAplicacion;

import java.util.Collection;
import java.util.Map;

import integracion.daos.DAOAlmacen;
import integracion.daos.DAOCliente;
import integracion.daos.DAOEmpleado;
import integracion.daos.DAOProducto;
import integracion.daos.DAOVenta;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TAlmacen;
import negocio.transfers.TCarrito;
import negocio.transfers.TCliente;
import negocio.transfers.TClienteSocio;
import negocio.transfers.TEmpleado;
import negocio.transfers.TLineaVenta;
import negocio.transfers.TProducto;
import negocio.transfers.TVenta;
import negocio.transfers.TVentaCompletaTOA;

public class SAVentaImp implements SAVenta {

	private DAOVenta daoVenta;
	private DAOProducto daoProducto;
	private DAOEmpleado daoEmpleado;
	private DAOCliente daoCliente;
	private DAOAlmacen daoAlmacen;

	public SAVentaImp() {
		this.daoVenta = FactoriaIntegracion.obtenerInstancia().generaDAOVenta();
		this.daoProducto = FactoriaIntegracion.obtenerInstancia().generaDAOProducto();
		this.daoEmpleado = FactoriaIntegracion.obtenerInstancia().generaDAOEmpleado();
		this.daoCliente = FactoriaIntegracion.obtenerInstancia().generaDAOCliente();
		this.daoAlmacen = FactoriaIntegracion.obtenerInstancia().generaDAOAlmacen();
	}

	@Override
	public int altaVenta(TVenta tVenta) {
		if (tVenta == null)
			return -1;

		TCliente cliente = daoCliente.leer(tVenta.getIdCliente());
		TEmpleado empleado = daoEmpleado.leer(tVenta.getIdEmpleado());
		if (cliente == null || empleado == null || cliente.getActivo() == 0 || empleado.getActivo() == 0)
			return -1;

		Map<Integer, Integer> productos = tVenta.getProductos();
		if (productos == null || productos.isEmpty())
			return -1;

		double precioTotal = 0.0;

		// Primero validar TODO
		for (Map.Entry<Integer, Integer> entry : productos.entrySet()) {
			int idProducto = entry.getKey();
			int cantidad = entry.getValue();

			TProducto producto = daoProducto.leer(idProducto);
			if (producto == null || producto.getActivo() != 1 || producto.getIdAlmacen() == -1
					|| producto.getIdProveedor() == -1 || cantidad > producto.getUnidades()) {
				return -1;
			}
		}

		// Si todo validado, crear venta
		int idVenta = daoVenta.crear(tVenta);
		if (idVenta == -1)
			return -1;

		// Ahora procesar productos
		for (Map.Entry<Integer, Integer> entry : productos.entrySet()) {
			int idProducto = entry.getKey();
			int cantidad = entry.getValue();

			TProducto producto = daoProducto.leer(idProducto); // leer otra vez

			double importe = cantidad * producto.getPrecio();
			precioTotal += importe;

			daoVenta.vincularProducto(idVenta, idProducto, cantidad, importe);

			producto.setUnidades(producto.getUnidades() - cantidad);
			daoProducto.actualizar(producto);

			if (producto.getIdAlmacen() != -1) {
				TAlmacen almacen = daoAlmacen.leer(producto.getIdAlmacen());
				if (almacen != null) {
					almacen.setOcupacion(almacen.getOcupacion() - cantidad);
					daoAlmacen.actualizar(almacen);
				}
			}
		}

		// Finalizar venta
		tVenta.setId(idVenta);
		tVenta.setDescuento(estregiaDescuentoSocios(cliente, precioTotal));
		tVenta.setPrecio(precioTotal);
		daoVenta.actualizar(tVenta);

		return idVenta;
	}

	@Override
	public TVenta leerVenta(int id) {
		TVenta leida = daoVenta.leer(id);
		return leida;
	}

	@Override
	public Collection<TVenta> leerTodasVentas() {
		return daoVenta.leerTodo();
	}

	@Override
	public int modificarVenta(TVenta tVenta) {
		int res = -1;
		TVenta existente = daoVenta.leer(tVenta.getId());

		if (existente != null && existente.getActivo() == 1) {
			TCliente cliente = daoCliente.leer(tVenta.getIdCliente());
			TEmpleado empleado = daoEmpleado.leer(tVenta.getIdEmpleado());
			tVenta.setDescuento(existente.getDescuento());
			tVenta.setPrecio(existente.getPrecio());
			if (cliente != null && cliente.getActivo() == 1 && empleado != null && empleado.getActivo() == 1)
				res = daoVenta.actualizar(tVenta);
		}

		return res;
	}

	@Override
	public int bajaVenta(int id) {
		int res = -1;
		TVenta venta = daoVenta.leer(id);

		if (venta == null || venta.getActivo() == 0)
			return -1;

		Map<Integer, Integer> productosVenta = venta.getProductos();
		if (productosVenta == null || productosVenta.isEmpty())
			return -1;

		// Revertir efectos de productos y almacenes
		for (Map.Entry<Integer, Integer> entry : productosVenta.entrySet()) {
			int idProducto = entry.getKey();
			int cantidad = entry.getValue();

			TProducto producto = daoProducto.leer(idProducto);
			if (producto != null) {
				producto.setUnidades(producto.getUnidades() + cantidad);
				daoProducto.actualizar(producto);

				if (producto.getIdAlmacen() != -1) {
					TAlmacen almacen = daoAlmacen.leer(producto.getIdAlmacen());
					if (almacen != null) {
						int nuevaOcupacion = almacen.getOcupacion() + cantidad;
						if (nuevaOcupacion > almacen.getCapacidadMaxima()) {
							return -1;
						}
						almacen.setOcupacion(nuevaOcupacion);
						daoAlmacen.actualizar(almacen);
					}
				}
			}
		}

		// Revertir puntos de cliente socio si aplica
		TCliente cliente = daoCliente.leer(venta.getIdCliente());
		if (cliente instanceof TClienteSocio) {
			TClienteSocio socio = (TClienteSocio) cliente;
			if (venta.getDescuento() > 0) {
				// Se usaron puntos (100)
				socio.setPuntos(socio.getPuntos() + 100);
			} else {
				// Se ganaron puntos (entero del precio)
				socio.setPuntos(socio.getPuntos() - (int) venta.getPrecio());
			}
			daoCliente.actualizar(socio);
		}

		// Desactivar la venta
		res = daoVenta.eliminar(id);
		return res;
	}

	@Override
	public Collection<TVenta> leerVentasPorEmpleado(int idEmpleado) {
		return daoVenta.leerPorEmpleado(idEmpleado);
	}

	@Override
	public Collection<TVenta> leerVentasPorCliente(int idCliente) {
		return daoVenta.leerPorCliente(idCliente);
	}

	private double estregiaDescuentoSocios(TCliente cliente, double precio) {
		if (cliente instanceof TClienteSocio) {
			TClienteSocio socio = (TClienteSocio) cliente;

			if (socio.getPuntos() < 100) {
				socio.setPuntos(socio.getPuntos() + (int) precio);
				daoCliente.actualizar(socio);
				return 0;
			}
			socio.setPuntos(socio.getPuntos() - 100);
			daoCliente.actualizar(socio);
			return precio * 0.1;
		}

		return 0;
	}

	@Override
	public TCarrito abrirVenta(int idEmpleado) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public int cerrarVenta(TCarrito carrito) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public int insertarProductoCarrito(TCarrito carrito) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public int eliminarProductoCarrito(TCarrito carrito) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public TVentaCompletaTOA leerVenta(int venta) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	@Override
	public int devolverVenta(TLineaVenta tLineaVenta) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}
}
