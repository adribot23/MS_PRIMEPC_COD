
package integracion.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionMySQL implements Transaction {
	private Connection connection;

	@Override
	public void start() {
		try {
			
			this.connection = DriverManager.getConnection("jdbc:sqlite:bd/IS2PrimePC.db", "root", "root");
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