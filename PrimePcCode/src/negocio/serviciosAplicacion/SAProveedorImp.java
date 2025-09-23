package negocio.serviciosAplicacion;

import java.util.Collection;

import integracion.daos.DAOProducto;
import integracion.daos.DAOProveedor;
import integracion.factoria.FactoriaIntegracion;
import negocio.transfers.TProducto;
import negocio.transfers.TProveedor;

public class SAProveedorImp implements SAProveedor {

	private DAOProveedor daoProveedor;
	private DAOProducto daoProducto;

	public SAProveedorImp() {
		this.daoProveedor = FactoriaIntegracion.obtenerInstancia().generaDAOProveedor();
		this.daoProducto = FactoriaIntegracion.obtenerInstancia().generaDAOProducto();
	}

	@Override
	public int altaProveedor(TProveedor tProveedor) {
		int id = -1;
		if (tProveedor != null) {
			TProveedor leido = daoProveedor.leerPorNombre(tProveedor.getNombre());
			if (leido == null) {
				id = daoProveedor.crear(tProveedor);
			} else if (leido.getActivo() == 0) {
				tProveedor.setId(leido.getId());
				daoProveedor.actualizar(tProveedor);
				id = tProveedor.getId();
			}
		}
		return id;
	}

	@Override
	public TProveedor leerProveedor(int id) {
		TProveedor proveedor = daoProveedor.leer(id);
		if (proveedor != null && proveedor.getActivo() == 1) {
			return proveedor;
		}
		return null;
	}

	@Override
	public Collection<TProveedor> leerTodosProveedores() {
		return daoProveedor.leerTodo();
	}

	@Override
	public int modificarProveedor(TProveedor tProveedor) {
		int res = -1;

		TProveedor existente = daoProveedor.leer(tProveedor.getId());
		if (existente != null && existente.getActivo() == 1 && (tProveedor.getNombre().equals(existente.getNombre())
				|| daoProveedor.leerPorNombre(tProveedor.getNombre()) == null)) {
			res = daoProveedor.actualizar(tProveedor);
		}

		return res;

	}

	@Override
	public int bajaProveedor(int id) {
		int res = -1;
		TProveedor proveedor = daoProveedor.leer(id);

		if (proveedor != null && proveedor.getActivo() == 1
				&& daoProducto.leerPorProveedor(proveedor.getId()).isEmpty()) {
			res = daoProveedor.eliminar(id);
		}
		return res;
	}

	@Override
	public TProveedor leerProveedorPorProducto(int idProducto) {
		TProducto producto = daoProducto.leer(idProducto);

		if (producto != null) {
			TProveedor actual = daoProveedor.leer(producto.getIdProveedor());
			if (actual != null)
				return actual;
		}
		return null;
	}

	@Override
	public int vincularProductoProveedor(int idProducto, int idProveedor) {
		int res = -1;
		TProducto productoLeido = daoProducto.leer(idProducto);
		TProveedor proveedorLeido = daoProveedor.leer(idProveedor);

		if (productoLeido != null && proveedorLeido != null && productoLeido.getActivo() == 1
				&& proveedorLeido.getActivo() == 1 && productoLeido.getIdProveedor() == -1) {
			productoLeido.setIdProveedor(proveedorLeido.getId());
			res = daoProducto.actualizar(productoLeido);
		}

		return res;
	}

	@Override
	public int desvincularProductoProveedor(int idProducto, int idProveedor) {
		int res = -1;
		TProducto productoLeido = daoProducto.leer(idProducto);
		TProveedor proveedorLeido = daoProveedor.leer(idProveedor);

		if (productoLeido != null && proveedorLeido != null && productoLeido.getActivo() == 1
				&& proveedorLeido.getActivo() == 1 && productoLeido.getIdProveedor() != -1
				&& idProveedor == productoLeido.getIdProveedor()) {
			productoLeido.setIdProveedor(-1);
			res = daoProducto.actualizar(productoLeido);
		}

		return res;
	}

}
