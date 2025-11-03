
package presentacion.GUI;

public abstract class GUIAbstractFactory {

	private static GUIAbstractFactory instancia;

	public static synchronized GUIAbstractFactory getInstancia() {

		if (instancia == null) {
			instancia = new GUIAbstractFactoryImp();
		}
		return instancia;
	}

	public abstract IGUI generarVistas(Evento evento);

}