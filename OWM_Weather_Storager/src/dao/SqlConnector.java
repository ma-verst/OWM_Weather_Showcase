package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnector {
	// Database credentials. Only set here.
	static final String USERNAME = "root";
	static final String PASSWORD = "root";

	// Driver and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/weather?serverTimezone=UTC";

	private Connection conn;
	private Statement stmt;

	/**
	 * Initializing object and connecting database driver
	 */
	public SqlConnector() {
		System.out.println("Initializing SqlConnector");
		conn = null;
		stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new database by the given name.
	 * 
	 * @param String
	 *            The name of the database.
	 */
	public void createDatabase(String databaseName) {
		System.out.println("Creating database " + databaseName);
		connect();
		try {
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE weather";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		System.out.println("Created database " + databaseName + " successfully.");
	}

	public void createTable(String tableName) {
		System.out.println("Create table " + tableName);
		connect();
		try {
			stmt = conn.createStatement();
			String sql = "CREATE TABLE " + tableName + 
					"(id INT NOT NULL, age INT NOT NULL, first VARCHAR(255), last VARCHAR(255), PRIMARY KEY ( id ));";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
	}
	
	public void dropTable(String tableName) {
		System.out.println("Dropping table " + tableName);
		connect();
		try {
			stmt = conn.createStatement();
			String sql = "DROP TABLE " + tableName;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
	}
	
	public void createLocationEntry(LocationEntry entry) {
		System.out.println("Creating location entry.");
		connect();
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO location(cityID,lon,lat,cityName) values(" + entry.getCityID() + "," + entry.getLon() + "," + entry.getLat() + ",'" + entry.getCityName() + "');";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		System.out.println("Location entry created.");
	}
	
	public void createWeatherConditionsEntry(WeatherConditionsEntry entry) {
		System.out.println("Creating weatherConditions entry.");
		connect();
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO weatherConditions(recordedTime,temperature,pressure,humidity,min_temperature,max_temperature) values(" + entry.getRecordedTime() + "," + entry.getTemperature() + "," + entry.getPressure() + "," + entry.getHumidity() + "," + entry.getMin_temperature() + "," + entry.getMax_temperature() + ");";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		System.out.println("WeatherConditions entry created");
	}

	/**
	 * Connects the runtime environment with the mySql database
	 */
	public void connect() {
		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Connected to database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnects the runtime environment from the mySql database
	 */
	public void disconnect() {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
				System.out.println("Disconnected Statement.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
				System.out.println("Disconnected Connection to database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
