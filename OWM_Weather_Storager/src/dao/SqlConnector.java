package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Serves as a connector instance to provide connectivity to a MySQL database. 
 * Connects and disconnects.
 * 
 * @author Marcel Verst
 * @version 09.06.2019
 */
public class SqlConnector {
	// Database credentials. Only set here.
	static final String USERNAME = "root";
	static final String PASSWORD = "root";

	// Driver and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/weather?serverTimezone=UTC";

	private Connection conn;

	/**
	 * Initializing object and connecting database driver
	 */
	public SqlConnector() {
		conn = null;
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connects the runtime environment with the mySql database
	 */
	public void connect() {
		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnects the runtime environment from the mySql database
	 */
	public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the current connection to the database. Used by the {@link DBHandler}.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return conn;
	}
}
