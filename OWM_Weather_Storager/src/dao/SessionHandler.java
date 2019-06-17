package dao;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * The SessionHandler serves as a helper between the user and the database to store and retrieve values to and from tables 
 * 'location' and 'weatherconditions'
 * 
 * @author Marcel Verst
 * @version 17.06.2019
 */
public class SessionHandler {
	// The SessionFactory to execute operations to the database
	private static SessionFactory factory;
	
	/**
	 * Creates a single factory which is used throughout the lifetime of the application.
	 */
	public SessionHandler() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}

	/**
	 * Adds a LocationEntry to the 'location' table. The LocationEntry object contains a WeatherConditionsEntry object which is stored as a child 
	 * of 'location' in the 'weatherconditions' table.
	 * 
	 * @param Integer The city id
	 * @param Double The longitude
	 * @param Double The latitude
	 * @param String The name of the city
	 * @param Set<WeatherConditionsEntry> A set which contains a WeatherConditionsEntry
	 */
	public void addLocation(int cityId, double lon, double lat, String cityName, Set<WeatherConditionsEntry> weatherConditionsEntries) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			LocationEntry LE = new LocationEntry(cityId, lon, lat, cityName);
			LE.setWeatherConditionsEntries(weatherConditionsEntries);
			session.save(LE); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
	}

	/**
	 * Prints out all entries of the 'location' table including the childs from the 'weatherconditions' table.
	 */
	public void listLocations() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<LocationEntry> locations = (List<LocationEntry>) session.createQuery("FROM LocationEntry").list();

			for(Iterator<LocationEntry> iterator1 = locations.iterator(); iterator1.hasNext();) {
				LocationEntry location = (LocationEntry) iterator1.next();
				System.out.println("CityID: " + location.getCityID());
				System.out.println("Longitude: " + location.getLon());
				System.out.println("Latitude: " + location.getLat());
				System.out.println("CityName: " + location.getCityName());
				Set<WeatherConditionsEntry> weatherConditionsEntries = location.getWeatherConditionsEntries();
				for(Iterator<WeatherConditionsEntry> iterator2 = weatherConditionsEntries.iterator(); iterator2.hasNext();) {
					WeatherConditionsEntry weatherCondition = (WeatherConditionsEntry) iterator2.next();
					System.out.println("Temperature: " + weatherCondition.getTemperature());
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
	}

	/**
	 * Deletes all entries of 'location' and 'weatherconditions' table
	 */
	public void deleteAll() {
		deleteAllWeatherConditions();
		deleteAllLocations();
	}

	/**
	 * Deletes all entries of 'weatherconditions' table
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAllWeatherConditions() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM WeatherConditionsEntry");
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
	}

	/**
	 * Deletes all entries of 'location' table
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAllLocations() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM LocationEntry");
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
	}

	/**
	 * Deletes a entry of the 'location' table specified by its cityID and also deletes the corresponding childs of this entry.
	 * 
	 * @param Integer The city id
	 */
	public void deleteLocation(Integer cityID){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			LocationEntry location = (LocationEntry) session.get(LocationEntry.class, cityID); 
			session.delete(location); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
	}
}
