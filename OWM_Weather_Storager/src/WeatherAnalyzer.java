import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dao.DBHandler;
import dao.SessionHandler;
import dao.WeatherConditionsEntry;
import gui.SwingGUIExample;
import plotting.HeatMapPainter;

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
 * @version 17.06.2019
 */
public class WeatherAnalyzer {
	private static String API_KEY = "";
	private static final SessionHandler sessionHandler = new SessionHandler();

	public static void main(String[] args) {
		processWeatherData();
		//		createHeatMap();
		//		createGUIExample();
	}

	/**
	 * Uses a parses to retrieve city names from a json file and queries the OpenWeatherMap (OWM) API for location and weather information
	 * regaring this city. Finally stores the retrieved information in a database using hibernate.
	 */
	@SuppressWarnings("unchecked")
	public static void processWeatherData() {
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
	 * Queries the database for location and weather information and creates a heatmap of all available entries.
	 */
	public static void createHeatMap() {
		HeatMapPainter demo = new HeatMapPainter();
		demo.pack();
		demo.setVisible(true);
	}

	/**
	 * Creates a Swing GUI example frame.
	 */
	public static void createGUIExample() {
		SwingGUIExample myGui = new SwingGUIExample();
		myGui.drawWindow();
	}


	/**
	 * Checks if the current JSON entry contains a city which lies in Germany using the country code 'DE'
	 * Cities in 'DE' are then crawled for retrieving location and weather data.
	 * 
	 * @param JSONObject The entry to check
	 */
	public static void parseEntry(JSONObject entry) {
		String city = entry.get("name").toString();
		String country = entry.get("country").toString();

		if(country.equals("DE")) {
			storeEntry(city, country);
		}
	}

	/**
	 * Uses {@link WeatherExtractor} to retrieve location and weather information of a specific city within a specific country.
	 * Afterwards the {@link DBHandler} is used to store the retrieved information within the MySQL database.
	 * 
	 * @param String The city
	 * @param String The country
	 */
	public static void storeEntry(String city, String country) {
		WeatherExtractor ex = new WeatherExtractor();

		/*Retrieve Weather locally----------------------------------------*/
		ex.setCredentials(API_KEY, city, country);
		if(ex.setCurrentWeather()) {
			HashSet<WeatherConditionsEntry> set = new HashSet<>();
			set.add(new WeatherConditionsEntry(
					ex.getRecordedTime(), 
					ex.getTemperature(), 
					ex.getPressure(), 
					ex.getHumidity(), 
					ex.getMin_temperature(), 
					ex.getMax_temperature(),
					ex.getCityId()));

			sessionHandler.addLocation(ex.getCityId(), ex.getLon(), ex.getLat(), ex.getCityName(), set);
		}
	}
}
