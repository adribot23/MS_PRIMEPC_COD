
package negocio.Venta;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Empleado.DAOEmpleado;
import integracion.FactoriaDAO.DAOAbstractFactory;
import integracion.Producto.DAOProducto;
import integracion.Transaction.TManager;
import integracion.Transaction.Transaction;
import integracion.Venta.DAOLineaVenta;
import integracion.Venta.DAOVenta;
import negocio.Empleado.TEmpleado;
import negocio.Producto.TProducto;

public class SAVentaImp implements SAVenta {

	@Override
	public TCarrito abrirVenta(int idEmpleado) {
		
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		
		TCarrito carrito = new TCarrito();

		if (tr != null) {
		
		tr.start();
		
		DAOEmpleado daoEmp = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
		
		TEmpleado empleado = daoEmp.read(idEmpleado);
		
		if (empleado != null && empleado.getActivo() == 1) {

		
		carrito.setLineasVenta(new HashSet<TLineaVenta>());

		TVenta venta = new TVenta();

		venta.setIdEmpleado(idEmpleado);

		carrito.setVenta(venta);
		tr.commit();

		
		}
		
		else {
			tr.rollback();
			carrito = null;
			
		}
		
		
		}
		
		return carrito;

	}
	
	public int pasar_carrito(TCarrito carrito) {
		return carrito == null ? -1 : 1;
	}


	@Override
	public int procesarCarrito(TCarrito carrito) {

		if (carrito == null || carrito.getLineasVenta().isEmpty()) {
			return -1;
		}

		else {
			return 1;
		}

	}

	@Override
	public int insertarProductoCarrito(TCarrito carrito) {
		
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		
		if (tr != null) {
			
		tr.start();
			
		DAOProducto daoProd = DAOAbstractFactory.getInstancia().generaDAOProducto();
		
		int idProducto = carrito.getidProducto();
		
		TProducto producto = daoProd.read(idProducto);
		
		if (producto != null && producto.getActivo() == 1) {

		int cantidad = carrito.getcantidadProducto();

		Set<TLineaVenta> lineasVenta = carrito.getLineasVenta();

		TLineaVenta lineaVenta = new TLineaVenta();

		lineaVenta = buscarLineaVentaPorIdProducto(lineasVenta, idProducto);

		if (lineaVenta != null) {

			lineaVenta.set_num_unidades(lineaVenta.get_num_unidades() + cantidad);

		} else {

			lineaVenta = new TLineaVenta();

			lineaVenta.set_producto(idProducto);

			lineaVenta.set_num_unidades(cantidad);

			lineasVenta.add(lineaVenta);

		}
		tr.commit();
		}
		
		else {
			tr.rollback();
		}
		
		}

		return 1;

	}

	private TLineaVenta buscarLineaVentaPorIdProducto(Set<TLineaVenta> lineasVenta, int idProducto) {

		for (TLineaVenta lineaVenta : lineasVenta) {
			if (lineaVenta.get_producto() == idProducto) {
				return lineaVenta;
			}
		}
		return null;
	}

	@Override
	public int eliminarProductoCarrito(TCarrito carrito) {

		int idProducto = carrito.getidProducto();

		int cantidad = carrito.getcantidadProducto();

		Set<TLineaVenta> lineasVenta = carrito.getLineasVenta();

		TLineaVenta lineaVenta = new TLineaVenta();

		lineaVenta = buscarLineaVentaPorIdProducto(lineasVenta, idProducto);

		if (lineaVenta != null) {

			int nuevaCantidad = lineaVenta.get_num_unidades() - cantidad;

			if (nuevaCantidad > 0) {
				lineaVenta.set_num_unidades(nuevaCantidad);
			} else {
				lineasVenta.remove(lineaVenta);
			}

		} else {
			return -1;
		}

		return 1;
	}

	@Override
	public int cerrarVenta(TCarrito carrito) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		boolean exito = false;
		int idVenta = -1;

		if (tr != null) {
			tr.start();

		TVenta venta = carrito.getVenta();

		DAOEmpleado daoEmp = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
		TEmpleado empleado = daoEmp.read(venta.getIdEmpleado());

		if (empleado != null && empleado.getActivo() == 1) {

				Iterator<TLineaVenta> it = carrito.recorrerLineasVenta();

				Set<TLineaVenta> lineasVenta = new LinkedHashSet<TLineaVenta>();

				DAOProducto daoProd = DAOAbstractFactory.getInstancia().generaDAOProducto();

				double totalproducto = 0;

				while (it.hasNext()) {

					TLineaVenta lineaVenta = it.next();

					TProducto producto = daoProd.read(lineaVenta.get_producto());

					if (producto != null && producto.getActivo() == 1 && producto.getUnidades() > 0) {

						TLineaVenta tLineaf = new TLineaVenta();

						if (producto.getUnidades() >= lineaVenta.get_num_unidades()) {
							totalproducto = actualizardatosLineaVenta(tLineaf, lineaVenta, producto, lineasVenta,
									totalproducto);

							if (daoProd.update(producto) == -1) {

								exito = true;
								break;
							}

							TProducto producto2 = daoProd.read(producto.getId());

							if (producto2 == null
									|| producto2.getUnidades() == 0 && daoProd.delete(producto2.getId()) == -1) {

								exito = true;
								break;
							}
						}
					}
				}

			if (lineasVenta.size() > 0 && !exito) {

				DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();

			venta.setPrecio(totalproducto);
			if (venta.getDescuento() == null) {
				venta.setDescuento(0.0);
			}
			if (venta.getMetodoPago() == null || venta.getMetodoPago().isEmpty()) {
				venta.setMetodoPago("Efectivo");
			}

			idVenta = daoVenta.create(venta);

					if (idVenta != -1) {

						carrito.setVenta(venta);
						venta.setId(idVenta);
						venta.setActivo(1);

						DAOLineaVenta daoLineaVenta = DAOAbstractFactory.getInstancia().generaDAOLineaVenta();

						for (TLineaVenta lineaVenta : lineasVenta) {

							lineaVenta.set_venta(idVenta);
							lineaVenta.set_activo(1);

							int idLinea = daoLineaVenta.create(lineaVenta);

							if (idLinea == -1) {
								exito = true;
								break;
							}
						}

						carrito.setLineasVenta(lineasVenta);

						if (exito) {
							tr.rollback();
						}

						else {
							tr.commit();
						}

					} else {
						tr.rollback();
					}

				} else {
					tr.rollback();
				}

			} else {
				tr.rollback();

			}
		}

		return idVenta;

	}

	private double actualizardatosLineaVenta(TLineaVenta lineaVenta, TLineaVenta lineaVenta2, TProducto producto,
			Set<TLineaVenta> lineasVenta, double totalproducto2) {

		lineaVenta.set_num_unidades(lineaVenta2.get_num_unidades());
		lineaVenta.set_producto(lineaVenta2.get_producto());

		double precioTotalLinea = producto.getPrecio() * lineaVenta.get_num_unidades();

		lineaVenta.set_precio_unidades(precioTotalLinea);
		lineasVenta.add(lineaVenta);

		int unidadesTotales = producto.getUnidades() - lineaVenta2.get_num_unidades();

		totalproducto2 += producto.getPrecio() * lineaVenta2.get_num_unidades();

		producto.setUnidades(unidadesTotales);

		return totalproducto2;

	}

	@Override
	public int devolverVenta(TLineaVenta lineaVenta) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			TVenta tVenta = daoVenta.read(lineaVenta.get_venta());

			if (tVenta != null) {

				DAOLineaVenta daoLineaVenta = DAOAbstractFactory.getInstancia().generaDAOLineaVenta();
				TLineaVenta tLineaVenta2 = daoLineaVenta.read(lineaVenta);

				if (tLineaVenta2 != null) {

					DAOProducto daoProd = DAOAbstractFactory.getInstancia().generaDAOProducto();
					TProducto tProd = daoProd.read(lineaVenta.get_producto());

					if (tProd != null && tLineaVenta2.get_num_unidades() >= lineaVenta.get_num_unidades()) {

						int unidades = tLineaVenta2.get_num_unidades() - lineaVenta.get_num_unidades();

						double preciototal = tLineaVenta2.get_precio_unidades() / tLineaVenta2.get_num_unidades();
						double totalVenta = tVenta.getPrecio() - lineaVenta.get_num_unidades() * preciototal;
						double precioUnidades = unidades * preciototal;

						tLineaVenta2.set_precio_unidades(precioUnidades);
						tLineaVenta2.set_num_unidades(unidades);

						tVenta.setPrecio(totalVenta);

						if (daoLineaVenta.update(tLineaVenta2) == 1) {

							if (tProd.getActivo() == 0 && tProd.getUnidades() == 0) {
								tProd.setActivo(1);
							}

							tProd.setUnidades(tProd.getUnidades() + lineaVenta.get_num_unidades());

							if (daoProd.update(tProd) != -1) {

								TLineaVenta tLineaVenta_aux = daoLineaVenta.read(lineaVenta);

								// Linea de venta no válida
								if (tLineaVenta_aux != null) {

									// Error al actualizar la venta
									if (daoVenta.update(tVenta) != -1) {

										if (tLineaVenta_aux.get_num_unidades() != 0
												|| daoLineaVenta.delete(lineaVenta) != -1) {

											Set<TLineaVenta> setLineaVenta = daoLineaVenta
													.read_all(lineaVenta.get_venta());

											if (setLineaVenta != null) {

												if (setLineaVenta.stream().filter(venta -> venta.get_activo() == 1)
														.count() != 0
														|| daoVenta.delete(lineaVenta.get_venta()) != -1) {
													tr.commit();
													res = 1;
												} else {
													tr.rollback();
												}
											} else {
												tr.rollback();
											}
										} else {
											tr.rollback();
										}
									} else {
										tr.rollback();
									}
								} else {
									tr.rollback();
								}
							} else {
								tr.rollback();
							}
						} else {
							tr.rollback();
						}
					} else {
						tr.rollback();
					}
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

	@Override
	public Set<TVenta> leerVentasPorEmpleado(int idEmpleado) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TVenta> ventas = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			ventas = daoVenta.read_by_empleado(idEmpleado);

			if (ventas != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return ventas;
	}


	@Override
	public int modificarVenta(TVenta venta) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;

		if (tr != null) {

			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			TVenta ventaExistente = daoVenta.read(venta.getId());

			if (ventaExistente != null && ventaExistente.getActivo() == 1) {

			TEmpleado empleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado().read(venta.getIdEmpleado());

			if (empleado != null && empleado.getActivo() == 1) {

				ventaExistente.setIdEmpleado(venta.getIdEmpleado());
				ventaExistente.setIdCliente(venta.getIdCliente());
				ventaExistente.setMetodoPago(venta.getMetodoPago());
				ventaExistente.setPrecio(venta.getPrecio());
				ventaExistente.setDescuento(venta.getDescuento());
				res = daoVenta.update(ventaExistente);

					if (res != -1) {
						tr.commit();
					}

					else {
						tr.rollback();
					}
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;

	}

	@Override
	public TVentaTOA leerVenta(int id) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		TVentaTOA ventaTOA = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			TVenta venta = daoVenta.read(id);

			if (venta != null) {
				DAOEmpleado daoEmpleado = DAOAbstractFactory.getInstancia().generaDAOEmpleado();
				TEmpleado empleado = daoEmpleado.read(venta.getIdEmpleado());

				DAOLineaVenta daoLineaVenta = DAOAbstractFactory.getInstancia().generaDAOLineaVenta();
				Set<TLineaVenta> lineasVenta = daoLineaVenta.read_all(id);

				Set<TProducto> listaProductos = new LinkedHashSet<>();

				if (lineasVenta != null && !lineasVenta.isEmpty()) {
					Iterator<TLineaVenta> it = lineasVenta.iterator();
					DAOProducto daoProducto = DAOAbstractFactory.getInstancia().generaDAOProducto();

					while (it.hasNext()) {
						TLineaVenta lineaVenta = it.next();
						TProducto producto = daoProducto.read(lineaVenta.get_producto());

						if (producto != null) {
							listaProductos.add(producto);
						}
					}
				}

				ventaTOA = new TVentaTOA(venta, empleado, lineasVenta, listaProductos);
				tr.commit();

			} else {
				tr.rollback();
			}
		}
		return ventaTOA;
	}

	@Override
	public Set<TVenta> leerTodasVentas() {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TVenta> ventas = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			ventas = daoVenta.read_all();

			if (ventas != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return ventas;
	}

	@Override
	public Set<TVenta> leerVentasPorCliente(int idCliente) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TVenta> ventas = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			ventas = daoVenta.read_by_cliente(idCliente);

			if (ventas != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return ventas;
	}
	
	public int eliminar_venta(int idVenta) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;
		boolean error = false;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = DAOAbstractFactory.getInstancia().generaDAOVenta();
			TVenta venta = daoVenta.read(idVenta);

			if (venta != null && venta.getActivo() != 0) {

				DAOLineaVenta daoLineaVenta = DAOAbstractFactory.getInstancia().generaDAOLineaVenta();
				Set<TLineaVenta> setLineaVenta = daoLineaVenta.read_all(venta.getId());
				if (setLineaVenta == null) {
				    error = true;
				} else {
				    for (TLineaVenta lineaVenta : setLineaVenta) {
				        if (daoLineaVenta.delete(lineaVenta) == -1) {
				            error = true;
				            break;
				        }
				    }
				}

				if (!error) {

					res = daoVenta.delete(idVenta);

					if (res != -1) {
						tr.commit();
					}

					else {
						tr.rollback();
					}

				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

}