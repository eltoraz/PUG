package eltoraz.pug;

import java.util.GregorianCalendar;

/**
 * The <code>BasketballGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 0.1
 */
public class BasketballGame extends Game {
	/**
	 * Create a new <code>BasketballGame</code> with null location and owner at the current date/time.
	 */
	public BasketballGame() {
		super();
	}
	
	/**
	 * Create a new <code>BasketballGame</code> with the specified <code>Person</code> as the creator/owner
	 * @param p <code>Person</code> who created the <code>BasketballGame</code>
	 */
	public BasketballGame(Person p) {
		super(p);
	}
	
	/**
	 * Create a new <code>BasketballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>BasketballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>BasketballGame</code>
	 * @param p <code>Person</code> who created the <code>BasketballGame</code>; when initializing, this person is also the owner
	 */
	public BasketballGame(Location loc, GregorianCalendar cal, Person p) {
		super(loc, cal, p);
	}
	
	/**
	 * Create a new <code>BasketballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>BasketballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>BasketballGame</code>
	 * @param p <code>Person</code> who created the <code>BasketballGame</code>; when initializing, this person is also the owner
	 * @param privacy <code>boolean</code> representing the visibility status of the <code>BasketballGame</code>
	 */
	public BasketballGame(Location loc, GregorianCalendar cal, Person p, boolean privacy) {
		super(loc, cal, p, privacy);
	}
	
	/**
	 * Create a new <code>Game</code> from an existing <code>BasketballGame</code>, copying the old game's fields
	 * @param g <code>Game</code> of which the new <code>BasketballGame</code> will be a copy
	 */
	public BasketballGame(Game g) {
		super(g);
	}
}
