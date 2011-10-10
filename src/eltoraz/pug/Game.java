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
	
	/**
	 * Create a new <code>Game</code> with null location at the current date/time.
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
	
	public void setlocation(Location loc) { location = loc; }
	public void setdateTime(GregorianCalendar dt) { dateTime = dt; }
	public void setOwner(Person p) { owner = p; }
	public void setCreator(Person p) { creator = p; }
	
	public Location getlocation() { return location; }
	public GregorianCalendar getDateTime() { return dateTime; }
	public Person getowner() { return owner; }
	public Person getcreator() { return creator; }
}
