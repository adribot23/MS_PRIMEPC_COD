
package negocio.Venta;

public class TLineaVenta {

	private int id_producto;

	private int id_venta;

	private int num_unidades;

	private double precio_unidades;

	private int activo;

	public int get_producto() {

		return this.id_producto;
	}

	public int get_venta() {

		return this.id_venta;
	}

	public int get_num_unidades() {

		return this.num_unidades;
	}

	public double get_precio_unidades() {

		return this.precio_unidades;
	}

	public int get_activo() {

		return this.activo;
	}

	public void set_producto(int id_producto) {

		this.id_producto = id_producto;
	}

	public void set_venta(int id_venta) {

		this.id_venta = id_venta;
	}

	public void set_num_unidades(int num_unidades) {

		this.num_unidades = num_unidades;
	}

	public void set_precio_unidades(double precio_unidades) {

		this.precio_unidades = precio_unidades;
	}

	public void set_activo(int activo) {

		this.activo = activo;
	}
}