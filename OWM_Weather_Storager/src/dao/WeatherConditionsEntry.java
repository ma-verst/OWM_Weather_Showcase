package dao;

public class WeatherConditionsEntry {
	private int recordedTime;
	private double temperature;
	private int pressure;
	private int humidity;
	private double min_temperature;
	private double max_temperature;
	private int cityId;

	public WeatherConditionsEntry(int recordedTime, double temperature, int pressure, int humidity,
			double min_temperature, double max_temperature, int cityId) {
		this.recordedTime = recordedTime;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.min_temperature = min_temperature;
		this.max_temperature = max_temperature;
		this.cityId = cityId;
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
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
