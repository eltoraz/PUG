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
	
	// TODO: add more constructors for location, owner
	
	
	///Set members////
	/**
	 * Set the location
	 * @param loc - instance of Location 
	 */
	public void setLocation(Location loc)
	{
		location = loc;
	}
	/**
	 * Set the date and time
	 * @param dt - instance of Gregorian Calendar 
	 */
	public void setDateTime(GregorianCalendar dt)
	{
		dateTime = dt;
	}
	/**
	 * Set the owner
	 * @param p - instance of Person
	 */
	public void setOwner(Person p)
	{
		owner = p;
	}
	/**
	 * Set the creator
	 * @param p - instance of Person
	 */
	public void setCreator(Person p)
	{
		creator = p;
	}
	
	/////GET MEMBERS/////
	
	/**
	 * Get the location of the game .
	 * @return the location
	 */
	public Location getLocation() { return location; }
	
	/**
	 * Get the date and time of the game .
	 * @return the dateTime
	 */
	public GregorianCalendar getDateTime() { return dateTime; }
	/**
	 * Get the owner of the game .
	 * @return the owner
	 */
	public Person getOwner() { return owner; }
	/**
	 * Get the creater of the game .
	 * @return the creator
	 */
	public Person getCreator() { return creator; }
	
	
}
