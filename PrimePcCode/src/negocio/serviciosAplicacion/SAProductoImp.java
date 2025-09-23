package negocio.serviciosAplicacion;

import java.util.Collection;
import integracion.daos.DAOAlmacen;
import integracion.daos.DAOProducto;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TAlmacen;
import negocio.transfers.TProducto;

public class SAProductoImp implements SAProducto {

	private DAOProducto daoProducto;
	private DAOAlmacen daoAlmacen;

	public SAProductoImp() {
		this.daoProducto = FactoriaIntegracion.obtenerInstancia().generaDAOProducto();
		this.daoAlmacen = FactoriaIntegracion.obtenerInstancia().generaDAOAlmacen();
	}

	@Override
	public int altaProducto(TProducto producto) {
		int id = -1;

		if (producto != null) {
			TProducto leido = daoProducto.leerPorMarcaYModelo(producto.getModelo(), producto.getMarca());
			if (leido == null) {
				id = daoProducto.crear(producto);
			} else if (leido.getActivo() == 0) {
				
				producto.setId(leido.getId());
				daoProducto.actualizar(producto);
				id = producto.getId();
			}
		}

		return id;
	}

	@Override
	public TProducto leerProducto(int id) {
		TProducto leido = daoProducto.leer(id);
		if (leido != null && leido.getActivo() == 1) {
			return leido;
		}
		return null;
	}

	@Override
	public Collection<TProducto> leerTodosProductos() {
		return daoProducto.leerTodo();
	}

	@Override
	public int modificarProducto(TProducto producto) {
		if (producto == null) return -1;

	    TProducto productoExistente = daoProducto.leer(producto.getId());
	    if (productoExistente == null || productoExistente.getActivo() != 1) return -1;

	    if (productoExistente.getIdAlmacen() != -1) {
	        TAlmacen almacen = daoAlmacen.leer(productoExistente.getIdAlmacen());
	        if (almacen == null || almacen.getActivo() != 1) return -1;

	        int nuevaOcupacion = almacen.getOcupacion() - productoExistente.getUnidades() + producto.getUnidades();
	        if (nuevaOcupacion > almacen.getCapacidadMaxima()) return -1;

	        almacen.setOcupacion(nuevaOcupacion);
	        daoAlmacen.actualizar(almacen);
	    }

	    producto.setIdAlmacen(productoExistente.getIdAlmacen());
	    producto.setIdProveedor(productoExistente.getIdProveedor());

	    return daoProducto.actualizar(producto);
	}
	
	@Override
	public int bajaProducto(int id) {
		int res = -1;
		TProducto existente = daoProducto.leer(id);
		if (existente != null && existente.getActivo() == 1 && existente.getIdProveedor() == -1
				&& existente.getIdAlmacen() == -1) {
			res = daoProducto.eliminar(id);
		}
		return res;
	}

	@Override
	public Collection<TProducto> leerProductosPorProveedor(int idProveedor) {
		return daoProducto.leerPorProveedor(idProveedor);
	}

	@Override
	public Collection<TProducto> leerProductosPorAlmacen(int idAlmacen) {
		return daoProducto.leerPorAlmacen(idAlmacen);
	}
}
