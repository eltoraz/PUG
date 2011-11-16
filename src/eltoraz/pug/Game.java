package eltoraz.pug;

import com.google.android.maps.GeoPoint;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.GregorianCalendar;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Game</code> class represents a generic game. It's meant to be extended by subclasses to
 * correspond to specific sports/games.
 * @author Bill Jameson
 * @version 0.1
 */
public abstract class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum SportType {
		BASKETBALL, BASEBALL, FOOTBALL, SOCCER;
		
		@Override
		public String toString() {
			String s = super.toString();
			return s.substring(0, 1) + s.substring(1).toLowerCase();
		}
	}
	
	// TODO: figure out how to implement the ID's (assigned server-side, but how to propagate to app?)
	//		 - maybe have the server return the id to the push game function
	protected int id;
	protected Location location;
	protected GregorianCalendar dateTime;
	protected Person owner, creator;
	protected boolean privateGame;
	protected int maxPlayers;
	protected String description;
	protected SportType gameType;
	
	// TODO: add fields for additional rules, privacy options
	// TODO: change the e.printStackTrace()'s to do something more useful instead
	
	/* ***** CONSTRUCTOR ***** */
	
	/**
	 * Create a new <code>Game</code> with null location and owner at the current date/time.
	 */
	public Game() {
		location = new Location();
		dateTime = new GregorianCalendar();
		owner = creator = new Person();
		privateGame = false;
		maxPlayers = 2;
	}
	
	/* ***** GAME BUILDERS ***** */
	
	/**
	 * Build a new <code>Game</code> from the components specified
	 * @param type <code>enum Game.SportType</code> corresponding to the <code>Game</code> type (and thus subclass)
	 * @param descr <code>String</code> representing the <code>Game</code>'s description
	 * @param dt <code>long</code> encoding the number of milliseconds since the Epoch, uniquely identifying a date and time
	 * @param create <code>Person</code> who initially created the <code>Game</code>
	 * @param own <code>Person</code> who is the current owner of the <code>Game</code>
	 * @param max <code>int</code> equal to the maximum allowed number of players for the <code>Game</code>
	 * @param priv <code>boolean</code> describing the privacy status of the <code>Game</code>
	 */
	public static Game buildGame(SportType type, String descr, long dt, Person create,
								 Person own, Location loc, int max, boolean priv) {
		Game newGame = null;
		
		try {
			// use reflection to create the game
			String className = "eltoraz.pug." + type.toString() + "Game";
			Class<?> cl = Class.forName(className);
			java.lang.reflect.Constructor<?> constructor = cl.getConstructor();
			Object newGameType = constructor.newInstance();
			newGame = (Game) newGameType;
			
			// set the properties of the game
			newGame.gameType = type;
			newGame.description = descr;
			newGame.dateTime = new GregorianCalendar();
			newGame.dateTime.setTimeInMillis(dt);
			newGame.creator = create;
			newGame.owner = own;
			newGame.location = loc;
			newGame.maxPlayers = max;
			newGame.privateGame = priv;
		}
		catch (ClassNotFoundException e) {
			// thrown if the subclass doesn't exist
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			// in this case, thrown if the target subclass doesn't define its default constructor
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// thrown if the target constructor throws an exception
			// note: none of the Game subclass constructors throw an exception
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// thrown when the target constructor is inaccessible
			// this should theoretically never happen here
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// thrown if trying to instantiate an abstract class
			// the naming convention above is based on the GameType enum, all of which will correspond
			//    to subclasses of Game, so this exception will never be thrown
			e.printStackTrace();
		}
		
		return newGame;
	}
	
	/**
	 * Build an instance of the correct <code>Game</code> subclass from the JSON object passed in
	 * @param json <code>JSONObject</code> to be parsed
	 * @return an instance of a subclass of <code>Game</code>
	 */
	public static Game buildGame(JSONObject json) {
		Game newGame = null;
		
		try {
			// get the data about the game from the JSON
			SportType type = SportType.valueOf(json.getString("sport").toUpperCase());
			String descr = json.getString("descr");
			long dt = json.getLong("datetime");
			Person create = new Person(json.getJSONObject("creator"));
			Person own = new Person(json.getJSONObject("owner"));
			Location loc = new Location(json.getJSONObject("location"));
			int max = json.getInt("playercount");
			boolean priv = json.getBoolean("private");
			
			newGame = buildGame(type, descr, dt, create, own, loc, max, priv);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return newGame;
	}
	
	/**
	 * Return a JSON object representation of this <code>Game</code>
	 * @author Brian Orecchio
	 * @return a <code>JSONObject</code> encapsulation of this <code>Game</code> 
	 */
	public JSONObject JSON() {
		JSONObject gameJson = null;
		
		try {
			gameJson = new JSONObject();
			
			gameJson.put("sport", gameType.toString());
			gameJson.put("descr", description);
			gameJson.put("datetime", dateTime.getTimeInMillis());
			gameJson.put("creator", creator.JSON());
			gameJson.put("owner", owner.JSON());
			gameJson.put("location", location.JSON());
			gameJson.put("playercount", maxPlayers);
			gameJson.put("private", privateGame);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return gameJson;
	}
	
	/**
	 * Return a <code>GeoPoint</code> corresponding to the <code>Location</code> of this <code>Game</code>
	 * @return a <code>GeoPoint</code> matching the latitude and longitude of the location of this game
	 */
	public GeoPoint GeoPoint() {
		return location.GeoPoint();
	}
	
	/* ***** GET METHODS ***** */
	
	/**
	 * Retrieve the <code>Game</code>'s description
	 * @return a <code>String</code> containing the <code>Game</code>'s description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Retrieve the <code>Game</code>'s location
	 * @return a <code>Location</code> containing the <code>Game</code>'s location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Retrieve the <code>Game</code>'s type
	 * @return a <code>SportType</code> containing the <code>Game</code>'s type
	 */
	public SportType getGameType() {
		return gameType;
	}
	
	/**
	 * Retrieve the <code>Game</code>'s creator
	 * @return a <code>Person</code> containing the <code>Game</code>'s creator
	 */
	public Person getCreator() {
		return creator;
	}
	
	/**
	 * Retrieve the <code>Game</code>'s date and time
	 * @return a <code>GregorianCalendar</code> containing the <code>Game</code>'s date and time
	 */
	public GregorianCalendar getDate() {
		return dateTime;
	}
	
}
