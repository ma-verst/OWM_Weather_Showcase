package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Container class representing a row of the 'weatherconditions' table.
 * 
 * @author Marcel Verst
 * @version 17.06.2019
 */
@Entity
@Table(name = "WEATHERCONDITIONS")
public class WeatherConditionsEntry {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "recordedTime")
	private int recordedTime;
	
	@Column(name = "temperature")
	private double temperature;
	
	@Column(name = "pressure")
	private int pressure;
	
	@Column(name = "humidity")
	private int humidity;
	
	@Column(name = "min_temperature")
	private double min_temperature;
	
	@Column(name = "max_temperature")
	private double max_temperature;
	
	@Column(name = "cityId")
	private int cityId;
	
	/**
	 * Default Constructor
	 */
	public WeatherConditionsEntry() {}
	
	/**
	 * Specific Constructor
	 * 
	 * @param Integer The timestamp
	 * @param Double The current temperature
	 * @param Integer The current pressure
	 * @param Integer The current humidity
	 * @param Double The minimum temperature
	 * @param Double The maximum temperature
	 * @param Integer The city ID referring to the city ID of the location table
	 */
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cityId;
		result = prime * result + humidity;
		long temp;
		temp = Double.doubleToLongBits(max_temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min_temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + pressure;
		result = prime * result + recordedTime;
		temp = Double.doubleToLongBits(temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherConditionsEntry other = (WeatherConditionsEntry) obj;
		if (cityId != other.cityId)
			return false;
		if (humidity != other.humidity)
			return false;
		if (Double.doubleToLongBits(max_temperature) != Double.doubleToLongBits(other.max_temperature))
			return false;
		if (Double.doubleToLongBits(min_temperature) != Double.doubleToLongBits(other.min_temperature))
			return false;
		if (pressure != other.pressure)
			return false;
		if (recordedTime != other.recordedTime)
			return false;
		if (Double.doubleToLongBits(temperature) != Double.doubleToLongBits(other.temperature))
			return false;
		return true;
	}
	
}
