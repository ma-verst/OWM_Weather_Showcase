import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dao.DBHandler;
import dao.LocationEntry;
import dao.WeatherConditionsEntry;

/**
 * The WeatherAnalyzer serves as the entry point of the application. Here you have to set your API_KEY first.
 * Also set the city name and the country code of where you want to retrieve your weather data from.
 * 
 * The OpenWeatherMap (OWM) API is then used to extract weather data of the specified location and store the data
 * into a MySQL database. The database consists of two tables: 'location' and 'weatherconditions'.
 * 
 * 'location': Contains coordinates and name of the location
 * 'weatherconditions': Contains the weather information like temperature, pressure, humidity ...
 * 
 * @author Marcel Verst
 * @version 09.06.2019
 */
public class WeatherAnalyzer {
	private static String API_KEY = "ca48cf91812f0d29c506b6e2f730a3ed";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try(FileReader reader = new FileReader("json_files\\city.list.json")) {
			try {
				Object obj = parser.parse(reader);
				JSONArray array = (JSONArray) obj;
				array.forEach(entry -> parseEntry((JSONObject) entry));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the current entry for cities within DE and stores the current cities weather information into the database.
	 * 
	 * @param JSONObject The entry to check
	 */
	public static void parseEntry(JSONObject entry) {
		String city = entry.get("name").toString();
		String country = entry.get("country").toString();

		if(country.equals("DE")) {
			WeatherExtractor ex = new WeatherExtractor();
			DBHandler dbHandler = new DBHandler();

			/*Retrieve Weather locally----------------------------------------*/
			ex.setCredentials(API_KEY, city, country);
			if(ex.setCurrentWeather()) {

				/*Create Location Entry-------------------------------------------*/
				LocationEntry lEntry = new LocationEntry(
						ex.getCityId(), 
						ex.getLon(), 
						ex.getLat(), 
						ex.getCityName());
				dbHandler.insertL(lEntry);

				/*Create WeatherConditions Entry----------------------------------*/
				WeatherConditionsEntry wcEntry = new WeatherConditionsEntry(
						ex.getRecordedTime(), 
						ex.getTemperature(), 
						ex.getPressure(), 
						ex.getHumidity(), 
						ex.getMin_temperature(), 
						ex.getMax_temperature(),
						ex.getCityId());
				dbHandler.insertWC(wcEntry);
			}
		}
	}
}
