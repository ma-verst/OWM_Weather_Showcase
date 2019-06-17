package dao;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Container class representing a row of the 'location' table.
 * 
 * @author Marcel Verst
 * @version 17.06.2019
 */
@Entity
@Table(name = "LOCATION")
public class LocationEntry {
	
	@Id
	@Column(name = "cityID")
	private int cityID;
	
	@Column(name = "lon")
	private double lon;
	
	@Column(name = "lat")
	private double lat;
	
	@Column(name = "cityName")
	private String cityName;
	
	// The set of weatherConditionEntries for a location
	private Set<WeatherConditionsEntry> weatherConditionsEntries;
	
	/**
	 * Default Constructor
	 */
	public LocationEntry() {}
	
	/**
	 * Specific Constructor
	 * 
	 * @param Integer The city id
	 * @param Double The longitude
	 * @param Double The latitude
	 * @param String The name of the city
	 */
	public LocationEntry(int cityID, double lon, double lat, String cityName) {
		this.cityID = cityID;
		this.lon = lon;
		this.lat = lat;
		this.cityName = cityName;
	}

	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
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
	public Set<WeatherConditionsEntry> getWeatherConditionsEntries() {
		return weatherConditionsEntries;
	}
	public void setWeatherConditionsEntries(Set<WeatherConditionsEntry> weatherConditionsEntries) {
		this.weatherConditionsEntries = weatherConditionsEntries;
	}
}
