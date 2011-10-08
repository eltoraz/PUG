package eltoraz.pug;

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
	
	/**
	 * Create a new <code>Location</code> with default values (0, 0).
	 */
	public Location() {
		latitude = 0;
		longitude = 0;
	}
	
	/**
	 * Create a new <code>Location</code> with the specified latitude and longitude
	 * @param lat <code>int</code>, -80 <= lat <= 80 to correspond to GeoPoint Google Maps API class
	 * @param lon <code>int</code>, -180 <= lon <= 180 to correspond to GeoPoint Google Maps API class
	 */
	public Location(int lat, int lon) {
		// TODO: error checking to ensure values are in range (maybe better to do this at input time)
		latitude = lat;
		longitude = lon;
	}
	
	/**
	 * Set the latitude portion of this <code>Location</code>.
	 * @param lat - the latitude component, in microdegrees (degrees * 1E6)
	 */
	public void setLat(int lat)
	{
		latitude = lat;
	}
	
	/**
	 * Set the longitude portion of this <code>Location</code>.
	 * @return the longitude component, in microdegrees (degrees * 1E6)
	 */
	public void setLon(int lon)
	{
		longitude = lon;
	}
	
	/**
	 * Get the latitude portion of this <code>Location</code>.
	 * @return the latitude component, in microdegrees (degrees * 1E6)
	 */
	public int getLat() { return latitude; }
	
	/**
	 * Get the longitude portion of this <code>Location</code>.
	 * @return the longitude component, in microdegrees (degrees * 1E6)
	 */
	public int getLon() { return longitude; }
}
