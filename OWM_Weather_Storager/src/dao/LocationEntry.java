package dao;

/**
 * Container class representing a row of the 'location' table.
 * 
 * @author Marcel Verst
 * @version 10.06.2019
 */
public class LocationEntry {
	private int cityID;
	private double lon;
	private double lat;
	private String cityName;

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
}
