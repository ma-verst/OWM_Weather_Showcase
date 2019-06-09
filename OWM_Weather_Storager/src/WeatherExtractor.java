import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Requests JSON file using the OpenWeatherMap (OWM) API and reads the fields contained in the JSON file.
 * Several fields containing weather information is stored locally for further processing like storing in a database.
 * 
 * @author Marcel Verst
 * @version 09.06.2019
 */
public class WeatherExtractor {
	// Credentials
	private String API_KEY = "ca48cf91812f0d29c506b6e2f730a3ed";
	private String yourCity = "Darmstadt";
	private String yourCountryCode = "de";

	// Fields for the 'location' table
	private int cityId;
	private double lon;
	private double lat;
	private String cityName;

	// Fields for the 'weatherConditions' table
	private int recordedTime;
	private double temperature;
	private int pressure;
	private int humidity;
	private double min_temperature;
	private double max_temperature;

	// The object containing the json file obtained by an URL
	private JSONObject json;

	/**
	 * Sets the credentials required to send a request to the OpenWeatherMap (OWM) API.
	 * @param String Your API key
	 * @param String The city you want to know the weather of
	 * @param String The country where the city is located in
	 */
	public void setCredentials(String API_KEY, String yourCity, String yourCountryCode) {
		this.API_KEY = API_KEY;
		this.yourCity = yourCity;
		this.yourCountryCode = yourCountryCode;
	}

	/**
	 * Reads the content of a reader file and returns a String containing all lines.
	 * 
	 * @param Reader The reader which reads a file
	 * @return StringBuilder The string which contains all lines of a file
	 * @throws IOException
	 */
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Returns the JSON content of a given URL (OpenWeatherMap API call)
	 * 
	 * @param String The URL for the OpenWeatherMap API call
	 * @return JSONObject The object which contains the JSON file data
	 * @throws JSONException
	 * @throws IOException 
	 */
	public JSONObject readJsonFromUrl(String url) throws JSONException, IOException {
		JSONObject json = null;
		InputStream is = null;
		try {
			is = new URL(url).openStream();
		} catch(IOException e) {
			return null;
		}

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	/**
	 * Returns true if the weather data could be retrieved and set, false else.
	 * 
	 * @return Boolean
	 */
	public boolean setCurrentWeather() {
		String url = "https://api.openweathermap.org/data/2.5/weather?q=" + yourCity + "," + yourCountryCode + "&appid="
				+ API_KEY;
		System.out.println(url);

		// Check if a JSONObject can be retrieved
		try {
			JSONObject obj = readJsonFromUrl(url);
			if(!(obj == null)) {
				setJSON(obj);
			}
			else {
				return false;
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		//Variables for filling the 'location' table
		setCityId(json.getInt("id"));
		setLon(json.getJSONObject("coord").getDouble("lon"));
		setLat(json.getJSONObject("coord").getDouble("lat"));
		setCityName(json.get("name").toString());

		//Variables for filling the 'weatherConditions' table
		setRecordedTime(json.getInt("dt"));
		setTemperature(json.getJSONObject("main").getDouble("temp"));
		setPressure(json.getJSONObject("main").getInt("pressure"));
		setHumidity(json.getJSONObject("main").getInt("humidity"));
		setMin_temperature(json.getJSONObject("main").getDouble("temp_min"));
		setMax_temperature(json.getJSONObject("main").getDouble("temp_max"));

		return true;
	}

	//--------------------------------------------------
	// GETTERS / SETTERS
	//--------------------------------------------------
	public String getAPI_KEY() {
		return API_KEY;
	}
	public void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}
	public String getYourCity() {
		return yourCity;
	}
	public void setYourCity(String yourCity) {
		this.yourCity = yourCity;
	}
	public String getYourCountryCode() {
		return yourCountryCode;
	}
	public void setYourCountryCode(String yourCountryCode) {
		this.yourCountryCode = yourCountryCode;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getRecordedTime() {
		return recordedTime;
	}
	public void setRecordedTime(int recordedTime) {
		this.recordedTime = recordedTime;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public double getMin_temperature() {
		return min_temperature;
	}
	public void setMin_temperature(double min_temperature) {
		this.min_temperature = min_temperature;
	}
	public double getMax_temperature() {
		return max_temperature;
	}
	public void setMax_temperature(double max_temperature) {
		this.max_temperature = max_temperature;
	}
	public void setJSON(JSONObject json) {
		this.json = json;
	}
}
