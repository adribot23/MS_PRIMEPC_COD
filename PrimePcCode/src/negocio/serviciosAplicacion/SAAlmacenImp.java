package negocio.serviciosAplicacion;

import java.util.Collection;

import integracion.daos.DAOAlmacen;
import integracion.daos.DAOProducto;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TAlmacen;
import negocio.transfers.TProducto;

public class SAAlmacenImp implements SAAlmacen {

	private DAOAlmacen daoAlmacen;
	private DAOProducto daoProducto;

	public SAAlmacenImp() {
		this.daoAlmacen = FactoriaIntegracion.obtenerInstancia().generaDAOAlmacen();
		this.daoProducto = FactoriaIntegracion.obtenerInstancia().generaDAOProducto();
	}

	@Override
	public int altaAlmacen(TAlmacen almacen) {

		int id = -1;
		if (almacen != null) {

			TAlmacen leido = daoAlmacen.leerPorNombre(almacen.getNombre());
			if (almacen.getCapacidadMaxima() > 0) {
				if (leido == null) {
					id = daoAlmacen.crear(almacen);
				} else if (leido.getActivo() == 0) {
					almacen.setId(leido.getId());
					daoAlmacen.actualizar(almacen);
					id = almacen.getId();
				}
			}
		}
		return id;
	}

	@Override
	public TAlmacen leerAlmacen(int id) {
		TAlmacen leido = daoAlmacen.leer(id);
		if (leido != null && leido.getActivo() == 1) {
			return leido;
		}
		return null;
	}

	@Override
	public Collection<TAlmacen> leerTodosAlmacenes() {
		return daoAlmacen.leerTodo();
	}

	@Override
	public int modificarAlmacen(TAlmacen almacen) {
		int res = -1;
		TAlmacen existente = daoAlmacen.leer(almacen.getId());
		almacen.setOcupacion(existente.getOcupacion());
		if (existente != null && existente.getActivo() == 1 && almacen.getCapacidadMaxima() >= existente.getOcupacion()
				&& almacen.getCapacidadMaxima() > 0 && (almacen.getNombre().equals(existente.getNombre())
						|| daoAlmacen.leerPorNombre(almacen.getNombre()) == null)) {
			res = daoAlmacen.actualizar(almacen);
		}

		return res;
	}

	@Override
	public int bajaAlmacen(int id) {
		int res = -1;
		TAlmacen almacen = daoAlmacen.leer(id);

		if (almacen != null && almacen.getActivo() == 1 && daoProducto.leerPorAlmacen(almacen.getId()).isEmpty()) {
			res = daoAlmacen.eliminar(id);
		}
		return res;
	}

	@Override
	public int vincularProductoAlmacen(int idProducto, int idAlmacen) {
		int res = -1;
		TProducto productoLeido = daoProducto.leer(idProducto);
		TAlmacen almacenLeido = daoAlmacen.leer(idAlmacen);

		if (productoLeido != null && almacenLeido != null && productoLeido.getActivo() == 1
				&& almacenLeido.getActivo() == 1 && productoLeido.getIdAlmacen() == -1) {
			int unidades = productoLeido.getUnidades();
			int capacidadActual = almacenLeido.getOcupacion() + unidades;

			if (capacidadActual <= almacenLeido.getCapacidadMaxima()) {
				almacenLeido.setOcupacion(capacidadActual);
				daoAlmacen.actualizar(almacenLeido);
				productoLeido.setIdAlmacen(almacenLeido.getId());
				res = daoProducto.actualizar(productoLeido);
			}

		}

		return res;
	}

	@Override
	public int desvincularProductoAlmacen(int idProducto, int idAlmacen) {
		int res = -1;
		TProducto productoLeido = daoProducto.leer(idProducto);
		TAlmacen almacenLeido = daoAlmacen.leer(idAlmacen);

		if (productoLeido != null && almacenLeido != null && productoLeido.getActivo() == 1
				&& almacenLeido.getActivo() == 1 && productoLeido.getIdAlmacen() != -1
				&& idAlmacen == productoLeido.getIdAlmacen()) {

			int unidades = productoLeido.getUnidades();
			int capacidadActual = almacenLeido.getOcupacion() - unidades;

			if (capacidadActual >= 0) {
				almacenLeido.setOcupacion(capacidadActual);
				daoAlmacen.actualizar(almacenLeido);
				productoLeido.setIdAlmacen(-1);
				res = daoProducto.actualizar(productoLeido);
			}

		}

		return res;
	}

}