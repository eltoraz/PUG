package eltoraz.pug;

import java.util.GregorianCalendar;

/**
 * The <code>Game</code> class represents a generic game. It's meant to be extended by subclasses to
 * correspond to specific sports/games.
 * @author Bill Jameson
 * @version 0.1
 */
public class Game {
	protected Location location;
	protected GregorianCalendar dateTime;
	protected Person owner, creator;
	
	// TODO: add fields for additional rules, privacy options
	
	/* ***** CONSTRUCTORS ***** */
	
	/**
	 * Create a new <code>Game</code> with null location and owner at the current date/time.
	 */
	public Game() {
		location = new Location();
		dateTime = new GregorianCalendar();
		owner = creator = new Person();
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
	
	/* ***** GET METHODS ***** */
	
	/**
	 * Get the location of the game .
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Get the date and time of the game .
	 * @return the date/time as a <code>GregorianCalendar</code> object
	 */
	public GregorianCalendar getDateTime() {
		return dateTime;
	}
	
	/**
	 * Get the owner of the game .
	 * @return the owner
	 */
	public Person getOwner() {
		return owner;
	}
	
	/**
	 * Get the creator of the game .
	 * @return the creator
	 */
	public Person getCreator() {
		return creator;
	}
}
