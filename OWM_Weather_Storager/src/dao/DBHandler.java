package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Currently unused.
 * 
 * @author Marcel Verst
 * @version 17.06.2019
 */
public class DBHandler {
	SqlConnector connectionHandler = new SqlConnector();

	/**
	 * Inserts Location (L) Entry into database.
	 * 
	 * @param LocationEntry The L entry you want to insert
	 */
	public void insertL(LocationEntry entry) {
		if(!entryExists(entry.getCityID())) {
			try {
				System.out.println("Inserting.");
				connectionHandler.connect();
				Statement stmt = connectionHandler.getConnection().createStatement();
				String sql = "INSERT INTO location(cityID,lon,lat,cityName) values(" + 
						entry.getCityID() + "," + 
						entry.getLon() + "," + 
						entry.getLat() + ",'" + 
						entry.getCityName() + "');";
				stmt.executeUpdate(sql);
				System.out.println("Location Entry inserted.");
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Location Entry already exists.");
		}
		connectionHandler.disconnect();
	}

	/**
	 * Deletes Location (L) Entry from database with the given cityId.
	 * 
	 * @param Integer The cityId
	 */
	public void deleteLById(int cityId) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "DELETE FROM location WHERE cityID=" + cityId + ";";
			stmt.executeUpdate(sql);
			System.out.println("Location Entry deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();
	}

	/**
	 * Updates Location Entry based on cityId with given values.
	 * 
	 * @param Integer The cityId
	 * @param Double Longitude
	 * @param Double Latitude
	 * @param String City Name
	 */
	public void updateLById(int cityId, double lon, double lat, String cityName) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "UPDATE location SET lon=" + lon 
					+ ", lat=" + lat 
					+ ", cityName='" + cityName 
					+ "' WHERE cityID=" + cityId + ";";
			stmt.executeUpdate(sql);
			System.out.println("Location Entry updated.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();
	}

	/**
	 * Returns a ResultSet containing all entries of the 'location' table.
	 * 
	 * @return ResultSet
	 */
	public ResultSet getAllLEntries() {
		ResultSet result = null;

		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "SELECT * FROM location;";
			result = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();

		return result;
	}

	/**
	 * Inserts WeatherCondition (WC) Entry into database.
	 * 
	 * @param WeatherConditionsEntry The WC entry you want to insert.
	 */
	public void insertWC(WeatherConditionsEntry entry) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "INSERT INTO weatherConditions(recordedTime,temperature,pressure,humidity,min_temperature,max_temperature,cityID) "
					+ "values(" + 
					entry.getRecordedTime() + "," + 
					entry.getTemperature() + "," + 
					entry.getPressure() + "," + 
					entry.getHumidity() + "," + 
					entry.getMin_temperature() + "," + 
					entry.getMax_temperature() + "," + 
					entry.getCityId() + ");";
			stmt.executeUpdate(sql);
			System.out.println("WeatherConditions Entry inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();
	}

	/**
	 * Deletes WeatherCondition (WC) Entry from database with the given id.
	 * 
	 * @param Integer The id
	 */
	public void deleteWCById(int id) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "DELETE FROM weatherconditions WHERE id="+id+";";
			stmt.executeUpdate(sql);
			System.out.println("WeatherConditions Entry deleted.");
		} catch (SQLException e) {
		}
		connectionHandler.disconnect();
	}

	/**
	 * Updates WeatherCondition Entry based on id with given values.
	 * 
	 * @param Integer The id which you want to update
	 * @param Integer Recorded time
	 * @param Double Temperature
	 * @param Integer Pressure
	 * @param Integer Humidity
	 * @param Double Minimum temperature
	 * @param Double Maximum temperature
	 * @param Integer City ID
	 */
	public void updateWCLById(int id, int recordedTime, double temperature, int pressure, int humidity, double min_temperature, double max_temperature) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "UPDATE weatherconditions SET recordedTime=" + recordedTime
					+ ", temperature=" + temperature 
					+ ", pressure=" + pressure 
					+ ", humidity=" + humidity 
					+ ", min_temperature=" + min_temperature 
					+ ", max_temperature=" + max_temperature 
					+ " WHERE id=" + id + ";";
			stmt.executeUpdate(sql);
			System.out.println("WeatherConditions Entry updated.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();
	}
	
	/**
	 * Returns a ResultSet containing all entries of the 'weatherconditions' table.
	 * 
	 * @return ResultSet
	 */
	public ResultSet getAllWCEntries() {
		ResultSet result = null;

		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "SELECT * FROM weatherconditions;";
			result = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionHandler.disconnect();

		return result;
	}
	
	/**
	 * Returns true if an entry based on the given id already exists in the 'location' table.
	 * 
	 * @param Integer The id you want to check
	 * @return Boolean
	 */
	public boolean entryExists(int id) {
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			String sql = "SELECT * FROM location WHERE cityID="+id+";";
			ResultSet set = stmt.executeQuery(sql);
			while(set.next()) {
				return true;
			}
		} catch (SQLException e) {
		}
		connectionHandler.disconnect();
		
		return false;
	}
	
	/**
	 * Returns the ResultSet of a query to the database
	 * 
	 * @param String The query
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String query) {
		ResultSet result = null;
		
		connectionHandler.connect();
		try {
			Statement stmt = connectionHandler.getConnection().createStatement();
			result = stmt.executeQuery(query);
		} catch(SQLException e) {
		}
		
		return result;
	}
}
