
package negocio.Venta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TCarrito {

	private int id_carrito;

	private int id_empleado;

	private int id_cliente;

	private int estado;

	private Double total;

	private Set<TLineaVenta> lineasVenta = new HashSet<>();

	private TVenta venta;

	private int idProducto;

	private int cantidadProducto;

	public int getId() {

		return this.id_carrito;
	}

	public void setId(int id) {

		this.id_carrito = id;
	}

	public Integer getIdEmpleado() {

		return this.id_empleado;
	}

	public void setIdEmpleado(int id) {

		this.id_empleado = id;
	}

	public Integer getIdCliente() {

		return this.id_cliente;
	}

	public void setIdCliente(int id) {

		this.id_cliente = id;
	}

	public Double getTotal() {

		return this.total;
	}

	public void setTotal(Double total) {

		this.total = total;
	}

	public Integer getEstado() {

		return this.estado;
	}

	public void setEstado(int estado) {

		this.estado = estado;
	}

	public Set<TLineaVenta> getLineasVenta() {

		return this.lineasVenta;
	}

	public void setLineasVenta(Set<TLineaVenta> lineasVenta) {
		if (lineasVenta != null) {
			this.lineasVenta = new HashSet<>(lineasVenta);
		} else {
			this.lineasVenta = new HashSet<>();
		}
	}

	public TVenta getVenta() {

		return this.venta;
	}

	public void setVenta(TVenta venta) {

		this.venta = venta;
	}

	public int getidProducto() {
		return idProducto;
	}

	public void setidProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getcantidadProducto() {
		return cantidadProducto;
	}

	public void setcantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Iterator<TLineaVenta> recorrerLineasVenta() {

		return new Iterator<TLineaVenta>() {

			List<TLineaVenta> listalineasVenta = new ArrayList<>(lineasVenta);
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < listalineasVenta.size();
			}

			@Override
			public TLineaVenta next() {
				return listalineasVenta.get(i++);
			}

			@Override
			public void remove() {

				listalineasVenta.remove(i);

				if (hasNext()) {
					i++;
				}
			}

		};

	}

}