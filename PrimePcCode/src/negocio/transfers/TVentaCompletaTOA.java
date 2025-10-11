package negocio.transfers;

public class TVentaCompletaTOA {

	private TVenta venta;
	private TCarrito carrito;

	public TVentaCompletaTOA() {
	}

	public TVentaCompletaTOA(TVenta venta, TCarrito carrito) {
		this.venta = venta;
		this.carrito = carrito;
	}

	public TVenta getVenta() {
		return venta;
	}

	public void setVenta(TVenta venta) {
		this.venta = venta;
	}

	public TCarrito getCarrito() {
		return carrito;
	}

	public void setCarrito(TCarrito carrito) {
		this.carrito = carrito;
	}

	@Override
	public String toString() {
		return venta.toString() + "\nCarrito: " + carrito.toString();
	}
}
