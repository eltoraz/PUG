package eltoraz.pug;

import java.util.GregorianCalendar;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Game</code> class represents a generic game. It's meant to be extended by subclasses to
 * correspond to specific sports/games.
 * @author Bill Jameson
 * @version 0.1
 */
public class Game {
	public enum Sport {
		BASKETBALL, BASEBALL, FOOTBALL;
		
		@Override
		public String toString() {
			String s = super.toString();
			return s.substring(0, 1) + s.substring(1).toLowerCase();
		}
	}
	
	protected Location location;
	protected GregorianCalendar dateTime;
	protected Person owner, creator;
	protected boolean privateGame;
	protected int maxPlayers;
	protected String description;
	protected Sport gameType;
	
	// TODO: add fields for additional rules, privacy options
	// TODO: remove useless constructors
	
	/* ***** CONSTRUCTORS ***** */
	
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
	
	/**
	 * Create a new <code>Game</code> with the specified <code>Person</code> as the creator/owner
	 * @param p <code>Person</code> who created the <code>Game</code>
	 */
	public Game(Person p) {
		location = new Location();
		dateTime = new GregorianCalendar();
		owner = creator = p;
		privateGame = false;
		maxPlayers = 2;
	}
	
	/**
	 * Create a new <code>Game</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>Game</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>Game</code>
	 * @param p <code>Person</code> who created the <code>Game</code>; when initializing, this person is also the owner
	 */
	public Game(Location loc, GregorianCalendar cal, Person p) {
		location = loc;
		dateTime = cal;
		owner = creator = p;
		privateGame = false;
		maxPlayers = 2;
	}
	
	/**
	 * Create a new <code>Game</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>Game</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>Game</code>
	 * @param p <code>Person</code> who created the <code>Game</code>; when initializing, this person is also the owner
	 * @param privacy <code>boolean</code> representing the visibility status of the <code>Game</code>
	 */
	public Game(Location loc, GregorianCalendar cal, Person p, boolean privacy) {
		location = loc;
		dateTime = cal;
		owner = creator = p;
		privateGame = privacy;
		maxPlayers = 2;
	}
	
	/**
	 * Create a new <code>Game</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>Game</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>Game</code>
	 * @param p <code>Person</code> who created the <code>Game</code>; when initializing, this person is also the owner
	 * @param privacy <code>boolean</code> representing the visibility status of the <code>Game</code>
	 * @param n <code>int</code> equal to the maximum number of players who can participate in the <code>Game</code>
	 */
	public Game(Location loc, GregorianCalendar cal, Person p, boolean privacy, int n) {
		location = loc;
		dateTime = cal;
		owner = creator = p;
		privateGame = privacy;
		maxPlayers = n;
	}
	
	/**
	 * Create a new <code>Game</code> from an existing <code>Game</code>, copying the old game's fields
	 * @param g <code>Game</code> of which the new <code>Game</code> will be a copy
	 */
	public Game(Game g) {
		location = new Location(g.location);
		dateTime = (GregorianCalendar) g.dateTime.clone();
		creator = new Person(g.creator);
		owner = new Person(g.owner);
		privateGame = g.privateGame;
		maxPlayers = g.maxPlayers;
	}
	
	/**
	 * Return a JSON object representation of this <code>Game</code>
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
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return gameJson;
	}
	
	/* ***** SET METHODS ***** */
	
	/**
	 * Set the location
	 * @param loc <code>Location</code> of the game
	 */
	public void setLocation(Location loc) {
		location = loc;
	}
	
	/**
	 * Set the date and time
	 * @param dt <code>GregorianCalendar</code> containing the date & time of the game
	 */
	public void setDateTime(GregorianCalendar dt) {
		dateTime = dt;
	}
	
	/**
	 * Set the owner
	 * @param p <code>Person</code> who is the owner of the game
	 */
	public void setOwner(Person p) {
		owner = p;
	}
	
	/**
	 * Set the creator
	 * @param p <code>Person</code> who created the game
	 */
	public void setCreator(Person p) {
		creator = p;
	}
	
	/**
	 * Set the privacy option for the game
	 * @param tf <code>boolean</code> representing the game's privacy: true = private, false (default) = public
	 */
	public void setPrivate(boolean tf) {
		privateGame = tf;
	}
	
	/**
	 * Set the maximum number of participating players for the game
	 * @param n <code>int</code> equal to the maximum number of players who can participate in the <code>Game</code>
	 */
	public void setMaxPlayers(int n) {
		maxPlayers = n;
	}
	
	/* ***** GET METHODS ***** */
	
	/**
	 * Get the location of the game
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Get the date and time of the game
	 * @return the date/time as a <code>GregorianCalendar</code> object
	 */
	public GregorianCalendar getDateTime() {
		return dateTime;
	}
	
	/**
	 * Get the owner of the game
	 * @return the owner
	 */
	public Person getOwner() {
		return owner;
	}
	
	/**
	 * Get the creator of the game
	 * @return the creator
	 */
	public Person getCreator() {
		return creator;
	}
	
	/**
	 * Get the privacy status of the game
	 * @return whether the game is private
	 */
	public boolean getPrivate() {
		return privateGame;
	}
	
	/**
	 * Get the maximum number of players for the game
	 * @return the maximum number of players who can join the game
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}
}
