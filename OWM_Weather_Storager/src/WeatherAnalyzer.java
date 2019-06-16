import org.json.simple.JSONObject;

import dao.DBHandler;
import dao.LocationEntry;
import dao.WeatherConditionsEntry;
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
 * @version 16.06.2019
 */
public class WeatherAnalyzer {
	private static String API_KEY = "";

	public static void main(String[] args) {
		HeatMapPainter demo = new HeatMapPainter();
		demo.pack();
		demo.setVisible(true);

		//		SwingGUIExample myGui = new SwingGUIExample();
		//		myGui.drawWindow();

		//		TextFieldExample example = new TextFieldExample();
		//		example.doSomething();

		//		JSONParser parser = new JSONParser();
		//		try(FileReader reader = new FileReader("json_files\\city.list.json")) {
		//			try {
		//				Object obj = parser.parse(reader);
		//				JSONArray array = (JSONArray) obj;
		//				array.forEach(entry -> parseEntry((JSONObject) entry));
		//
		//			} catch (ParseException e) {
		//				e.printStackTrace();
		//			}
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
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
