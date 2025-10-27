/**
 * 
 */
package negocio.Almacen;

import java.util.Set;

import integracion.Almacen.DAOAlmacen;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.Producto.DAOProducto;
import integracion.factoria.FactoriaIntegracion;
import negocio.Almacen.SAAlmacen;
import negocio.Almacen.TAlmacen;



public class SAAlmacenImp implements SAAlmacen {
	public Integer altaAlmacen(TAlmacen almacen) {
		int id = -1;
		if (almacen != null) {
			DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
			TAlmacen leido = daoAlmacen.read_by_name(almacen.getNombre());
			if (almacen.getCapacidadMaxima() > 0) {
				if (leido == null) {
					id = daoAlmacen.create(almacen);
				} else if (leido.getActivo() == 0) {
					almacen.setId(leido.getId());
					daoAlmacen.update(almacen);
					id = almacen.getId();
				}
			}
		}
		return id;
	}

	public Integer bajaAlmacen(Integer id) {
		int res = -1;
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
		TAlmacen almacen = daoAlmacen.read(id);

		if (almacen != null && almacen.getActivo() == 1 && daoProducto.read_by_almacen(almacen.getId()).isEmpty()) {
			res = daoAlmacen.delete(id);
		}
		return res;
	}

	public Integer modificarAlmacen(TAlmacen almacen) {
		int res = -1;
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		TAlmacen existente = daoAlmacen.read(almacen.getId());
		almacen.setOcupacion(existente.getOcupacion());
		if (existente != null && existente.getActivo() == 1 && almacen.getCapacidadMaxima() >= existente.getOcupacion()
				&& almacen.getCapacidadMaxima() > 0 && (almacen.getNombre().equals(existente.getNombre())
						|| daoAlmacen.read_by_name(almacen.getNombre()) == null)) {
			res = daoAlmacen.update(almacen);
		}

		return res;
	}

	public TAlmacen leerAlmacen(Integer id) {
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		TAlmacen leido = daoAlmacen.read(id);
		if (leido != null && leido.getActivo() == 1) {
			return leido;
		}
		return null;
	}

	public Set<TAlmacen> leerTodosAlmacenes() {
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		return daoAlmacen.read_all();
	}

	public Integer vincularProductoAlmacen(Integer idProducto, Integer idAlmacen) {
		int res = -1;
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
		TProducto productoLeido = daoProducto.read(idProducto);
		TAlmacen almacenLeido = daoAlmacen.read(idAlmacen);

		if (productoLeido != null && almacenLeido != null && productoLeido.getActivo() == 1
				&& almacenLeido.getActivo() == 1 && productoLeido.getIdAlmacen() == -1) {
			int unidades = productoLeido.getUnidades();
			int capacidadActual = almacenLeido.getOcupacion() + unidades;

			if (capacidadActual <= almacenLeido.getCapacidadMaxima()) {
				almacenLeido.setOcupacion(capacidadActual);
				daoAlmacen.update(almacenLeido);
				productoLeido.setIdAlmacen(almacenLeido.getId());
				res = daoProducto.update(productoLeido);
			}

		}

		return res;
	}

	public Integer desvincularProductoAlmacen(Integer idProducto, Integer idAlmacen) {
		int res = -1;
		DAOAlmacen daoAlmacen = DAOAbstractFactory.getInstancia().generaDAOAlmacen();
		DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();
		TProducto productoLeido = daoProducto.read(idProducto);
		TAlmacen almacenLeido = daoAlmacen.read(idAlmacen);

		if (productoLeido != null && almacenLeido != null && productoLeido.getActivo() == 1
				&& almacenLeido.getActivo() == 1 && productoLeido.getIdAlmacen() != -1
				&& idAlmacen == productoLeido.getIdAlmacen()) {

			int unidades = productoLeido.getUnidades();
			int capacidadActual = almacenLeido.getOcupacion() - unidades;

			if (capacidadActual >= 0) {
				almacenLeido.setOcupacion(capacidadActual);
				daoAlmacen.update(almacenLeido);
				productoLeido.setIdAlmacen(-1);
				res = daoProducto.actualizar(productoLeido);
			}

		}

		return res;
	}
}