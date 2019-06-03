import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.SqlConnector;
import dao.WeatherConditionsEntry;

public class JSONExtractor {
	private static String API_KEY = "ca48cf91812f0d29c506b6e2f730a3ed";
	private static String city = "Darmstadt";
	private static String countryCode = "de";

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static void doJsonStuff() throws IOException, JSONException {
		String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode + "&appid="
				+ API_KEY;
		JSONObject json = readJsonFromUrl(url);

		JSONObject coord = json.getJSONObject("coord");
		double lon = (double) coord.get("lon");
		double lat = (double) coord.get("lat");

		JSONArray weather = json.getJSONArray("weather");
		JSONObject zero = (JSONObject) weather.get(0);
		int weatherId = (int) zero.get("id");
		String weatherMain = zero.get("main").toString();
		String weatherDescription = zero.get("description").toString();
		String weatherIcon = zero.get("icon").toString();

		String base = json.get("base").toString();

		JSONObject main = json.getJSONObject("main");
		double mainTemp = (double) main.get("temp");
		int mainPressure = (int) main.get("pressure");
		int mainHumidity = (int) main.get("humidity");
		double mainTempMin = (double) main.get("temp_min");
		double mainTempMax = (double) main.get("temp_max");

		int visibility = (int) json.get("visibility");

		JSONObject wind = json.getJSONObject("wind");
		double windSpeed = (double) wind.get("speed");
		int windDegree = (int) wind.get("deg");

		JSONObject clouds = json.getJSONObject("clouds");
		int cloudsAll = (int) clouds.get("all");

		int dt = (int) json.get("dt");

		JSONObject sys = json.getJSONObject("sys");
		int sysType = (int) sys.get("type");
		int sysId = (int) sys.get("id");
		double sysMessage = (double) sys.get("message");
		String sysCountry = sys.get("country").toString();
		int sysSunrise = (int) sys.get("sunrise");
		int sysSunset = (int) sys.get("sunset");

		int timezone = (int) json.get("timezone");

		int id = (int) json.get("id");

		String name = json.get("name").toString();

		int cod = (int) json.get("cod");

		System.out.println("Coordinates:");
		System.out.println("Lon: " + lon);
		System.out.println("Lat: " + lat + "\n");

		System.out.println("Weather:");
		System.out.println("WeatherID: " + weatherId);
		System.out.println("WeatherMain: " + weatherMain);
		System.out.println("WeatherDescription: " + weatherDescription);
		System.out.println("WeatherIcon: " + weatherIcon + "\n");

		System.out.println("Base: " + base + "\n");

		System.out.println("Main:");
		System.out.println("MainTemp: " + mainTemp);
		System.out.println("MainPressure: " + mainPressure);
		System.out.println("MainHumidity: " + mainHumidity);
		System.out.println("MainTempMin: " + mainTempMin);
		System.out.println("MainTempMax: " + mainTempMax + "\n");

		System.out.println("Visibility: " + visibility + "\n");

		System.out.println("Wind:");
		System.out.println("WindSpeed: " + windSpeed);
		System.out.println("WindDegree: " + windDegree + "\n");

		System.out.println("Clouds:");
		System.out.println("CloudsAll: " + cloudsAll + "\n");

		System.out.println("Dt: " + dt + "\n");

		System.out.println("Sys:");
		System.out.println("SysType: " + sysType);
		System.out.println("SysId: " + sysId);
		System.out.println("SysMessage: " + sysMessage);
		System.out.println("SysCountry: " + sysCountry);
		System.out.println("SysSunrise: " + sysSunrise);
		System.out.println("SysSunset: " + sysSunset + "\n");

		System.out.println("Timezone: " + timezone + "\n");

		System.out.println("Id: " + id + "\n");

		System.out.println("Name: " + name + "\n");

		System.out.println("cod: " + cod + "\n");
	}

	public static void main(String[] args) {
		SqlConnector connector = new SqlConnector();
//		connector.createDatabase("weather");
//		connector.createTable("bla");
//		connector.dropTable("bla");
//		LocationEntry entry = new LocationEntry(2, 1.2, 3.4, "Darmstadt");
		WeatherConditionsEntry entry = new WeatherConditionsEntry(161, 2.23, 5, 4, 22.5, 32.5);
		connector.createWeatherConditionsEntry(entry);
//		connector.createLocationEntry(entry);
	}
}
