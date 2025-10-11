package negocio.transfers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TCarrito {

	private int id_venta;
	private int id_cliente;
	private Set<TLineaVenta> lista_lineaVenta;

	public TCarrito() {
		this.lista_lineaVenta = new HashSet<>();
	}

	public TCarrito(int id_venta, int id_cliente, Set<TLineaVenta> lista_lineaVenta) {
		this.id_venta = id_venta;
		this.id_cliente = id_cliente;
		this.lista_lineaVenta = lista_lineaVenta != null ? lista_lineaVenta : new HashSet<>();
	}

	public int getIdVenta() {
		return id_venta;
	}

	public void setIdVenta(int id_venta) {
		this.id_venta = id_venta;
	}

	public int getIdCliente() {
		return id_cliente;
	}

	public void setIdCliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Set<TLineaVenta> getListaLineaVenta() {
		return lista_lineaVenta;
	}

	public void setListaLineaVenta(Set<TLineaVenta> lista_lineaVenta) {
		this.lista_lineaVenta = lista_lineaVenta;
	}

	public void addLineaVenta(TLineaVenta linea) {
		this.lista_lineaVenta.add(linea);
	}

	public void removeLineaVenta(TLineaVenta linea) {
		this.lista_lineaVenta.remove(linea);
	}

	public int getCantidadTotal() {
		return lista_lineaVenta.stream().mapToInt(TLineaVenta::getUnidades).sum();
	}

	@Override
	public String toString() {
		return "Carrito [ID Venta: " + id_venta + ", ID Cliente: " + id_cliente + ", Cantidad Total: "
				+ getCantidadTotal() + ", Lineas: " + lista_lineaVenta + "]";
	}
}