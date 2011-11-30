package eltoraz.pug;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Location</code> class represents a physical location. It is defined by its latitude and
 * longitude. This class exists to avoid having dependencies on the Android SDK in the server component of PUG.
 * @author Bill Jameson
 * @version 1.0
 */
public class Location implements Serializable {
	/**
	 * Eclipse-generated default <code>Serializable</code> ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Latitude is specified in microdegrees (degrees * 1E6) for easier transition to
	 *   the <code>GeoPoint</code> class provided by the Google Maps API.
	 */
	protected int latitude;
	/**
	 *  Longitude is specified in microdegrees (degrees * 1E6) for easier transition to
	 *   the <code>GeoPoint</code> class provided by the Google Maps API.
	 */
	protected int longitude;
	/**
	 * User-entered or reverse-Geocoder-provided, human-readable representation of the geographic location
	 *  specified by the <code>Location</code>'s latitude and longitude.
	 */
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
	 * Create a new <code>Location</code> object with the specified latitude, longitude, and address description
	 * @param lat <code>int</code>, -80 <= lat <= 80 to correspond to GeoPoint Google Maps API class
	 * @param lon <code>int</code>, -180 <= lon <= 180 to correspond to GeoPoint Google Maps API class
	 * @param s <code>String</code> providing a description of the address
	 */
	public Location(int lat, int lon, String s) {
		latitude = lat;
		longitude = lon;
		address = s;
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
	 * Return a JSON representation of this <code>Location</code>.
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
	
	/**
	 * Get the human-readable form of this <code>Location</code>.
	 * @return a <code>String</code> 
	 */
	public String getAddress() {
		return address;
	}
}
