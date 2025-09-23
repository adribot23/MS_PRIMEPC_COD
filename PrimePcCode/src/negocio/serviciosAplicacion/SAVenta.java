package negocio.serviciosAplicacion;

import java.util.Collection;

import negocio.transfers.TVenta;

public interface SAVenta {
	public int altaVenta(TVenta tVenta);
	public TVenta leerVenta(int id);
	public Collection<TVenta> leerTodasVentas();
	public int modificarVenta(TVenta tVenta);
	public int bajaVenta(int id);
	public Collection<TVenta> leerVentasPorEmpleado(int idEmpleado);
	public Collection<TVenta> leerVentasPorCliente(int idCliente);
}
