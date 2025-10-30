
package integracion.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionMySQL implements Transaction {
	private Connection connection;

	@Override
	public void start() {
		try {
			
			//CUANDO TENGAMOS LA CONFIGURACION DE LA BD DESCOMENTAR
			//this.connection = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commit() {
		try {
			this.connection.commit();
			this.connection.close();
			TManager tm = TManager.getInstance();
			tm.deleteTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void rollback() {

		try {
			this.connection.rollback();
			this.connection.close();
			TManager tm = TManager.getInstance();
			tm.deleteTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object getResource() {
		return this.connection;
	}
}