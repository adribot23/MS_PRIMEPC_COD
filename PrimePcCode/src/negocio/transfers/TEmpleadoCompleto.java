package negocio.transfers;

public class TEmpleadoCompleto extends TEmpleado {
	private static final long serialVersionUID = 1L;
	protected int horas_extra;

	public TEmpleadoCompleto(int id, String nombre, String dni, String tlf, int horas_extra) {
		super(id, nombre, dni, tlf);
		this.horas_extra = horas_extra;
	}

	public TEmpleadoCompleto(int id, String nombre, String dni, String tlf, int activo, int horas_extra) {
		super(id, nombre, dni, tlf, activo);
		this.horas_extra = horas_extra;
	}

	public TEmpleadoCompleto() {

	}

	public int getHorasExtra() {
		return this.horas_extra;
	}

	public void setHorasExtra(int horas) {
		this.horas_extra = horas;
	}

	public String toString() {
		return super.toString() + ", Tipo: Completo, Horas Extra: " + this.horas_extra + "]";

	}
}
