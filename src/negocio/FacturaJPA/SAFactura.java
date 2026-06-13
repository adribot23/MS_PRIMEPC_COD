/**
 * 
 */
package negocio.FacturaJPA;

import java.util.Set;

public interface SAFactura {

	public TCarritoFactura abrirFactura(TFactura tFactura);

	public Integer cerrarFactura(TCarritoFactura tCarritoFactura);

	public Integer modificarFactura(TFactura factura);

	public TFacturaTOA buscarFactura(Integer idFactura);

	public Set<TFactura> mostrarFacturas();

	public Integer devolucion(TLineaFactura tLineaFactura);

	public Integer anyadirPaquete(TCarritoFactura tCarritoFactura);

	public Integer eliminarPaquete(TCarritoFactura tCarritoFactura);
	
	public Set<TFactura> listarFacturasPorRemitente(Integer idRemitente);
	
}