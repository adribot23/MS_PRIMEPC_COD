package negocio.transfers;

public class TLineaVenta {

	private int id_producto;
	private int id_venta;
	private int unidades;
	private double importe;
	private int activo;

	public TLineaVenta() {
	}

	public TLineaVenta(int id_producto, int id_venta, int unidades, double importe, int activo) {
		this.id_producto = id_producto;
		this.id_venta = id_venta;
		this.unidades = unidades;
		this.importe = importe;
		this.activo = activo;
	}

	public int getIdProducto() {
		return id_producto;
	}

	public void setIdProducto(int id_producto) {
		this.id_producto = id_producto;
	}

	public int getIdVenta() {
		return id_venta;
	}

	public void setIdVenta(int id_venta) {
		this.id_venta = id_venta;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "LineaVenta [ID Producto: " + id_producto + ", ID Venta: " + id_venta + ", Unidades: " + unidades
				+ ", Importe: " + importe + ", Activo: " + activo + "]";
	}
}