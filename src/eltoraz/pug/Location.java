package eltoraz.pug;

import com.google.android.maps.GeoPoint;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Location</code> class represents a physical location. It is defined by its latitude and
 * longitude. This class exists to avoid having dependencies on the Android SDK in the server component of PUG.
 * @author Bill Jameson
 * @version 0.1
 */
public class Location {
	// latitude and longitude are specified in microdegrees (degrees * 1E6) to match Google Maps API 
	protected int latitude;
	protected int longitude;
	protected String address;
	
	/* ***** CONSTRUCTORS ***** */
	
	/**
	 * Create a new <code>Location</code> object with default values (0, 0).
	 */
	public Location() {
		latitude = 0;
		longitude = 0;
		address = "";
	}
	
	/**
	 * Create a new <code>Location</code> object with the specified latitude and longitude
	 * @param lat <code>int</code>, -80 <= lat <= 80 to correspond to GeoPoint Google Maps API class
	 * @param lon <code>int</code>, -180 <= lon <= 180 to correspond to GeoPoint Google Maps API class
	 */
	public Location(int lat, int lon) {
		// TODO: error checking to ensure values are in range (maybe better to do this at input time)
		latitude = lat;
		longitude = lon;
		address = "";
	}
	
	/**
	 * Create a new <code>Location</code> object with the specified latitude, longitude, and address description
	 * @param lat <code>int</code>, -80 <= lat <= 80 to correspond to GeoPoint Google Maps API class
	 * @param lon <code>int</code>, -180 <= lon <= 180 to correspond to GeoPoint Google Maps API class
	 * @param s <code>String</code> providing a description of the address
	 */
	public Location(int lat, int lon, String s) {
		// TODO: error checking to ensure values are in range (maybe better to do this at input time)
		latitude = lat;
		longitude = lon;
		address = s;
	}
	
	/**
	 * Create a new <code>Location</code> object as a copy of the specified one
	 * @param loc <code>Location</code> object to be cloned to create this one
	 */
	public Location(Location loc) {
		latitude = loc.latitude;
		longitude = loc.longitude;
		address = loc.address;
	}
	
	/**
	 * Create a new <code>Location</code> object from the data in the JSON object
	 * @author Brian Orecchio
	 * @param json <code>JSONObject</code> to be parsed
	 */
	public Location(JSONObject json) {
		try {
			latitude = json.getInt("lat");
			longitude = json.getInt("lon");
			address = json.getString("name");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return a <code>GeoPoint</code> representation of this <code>Location</code>
	 * @return a <code>GeoPoint</code> matching the latitude and longitude of this location
	 */
	public GeoPoint GeoPoint() {
		return new GeoPoint(latitude, longitude);
	}
	
	/**
	 * Return a JSON object representation of this <code>Location</code>
	 * @author Brian Orecchio
	 * @return a <code>JSONObject</code> encapsulating this location
	 */
	public JSONObject JSON() {
		JSONObject locJson = null;
		
		try {
			locJson = new JSONObject();
			
			locJson.put("lat", latitude);
			locJson.put("lon", longitude);
			locJson.put("name", address);
		}
		catch (JSONException e) {		// will never throw this exception in practice
			e.printStackTrace();
		}
		
		return locJson;
	}
}
