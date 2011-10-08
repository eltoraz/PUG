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
	
	public setlocation(Location loc)
	{
		location = loc;
	}
	public setdateTime(GregorianCalendar dt)
	{
		dateTime = dt;
	}
	public setowner(Person p)
	{
		owner = p;
	}
	public setowner(Person p)
	{
		creator = p;
	}
	public getlocation() { return location; }
	public getdateTime() { return dateTime; }
	public getowner() { return owner; }
	public getcreator() { return creator; }
	
	// TODO: add more constructors for location, owner
}
