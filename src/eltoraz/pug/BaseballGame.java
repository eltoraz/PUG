package eltoraz.pug;

import java.util.GregorianCalendar;

/**
 * The <code>BaseballGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 0.1
 */
public class BaseballGame extends Game {
	/**
	 * Create a new <code>BaseballGame</code> with null location and owner at the current date/time.
	 */
	public BaseballGame() {
		super();
		gameType = SportType.BASEBALL;
	}
	
	/**
	 * Create a new <code>BaseballGame</code> with the specified <code>Person</code> as the creator/owner
	 * @param p <code>Person</code> who created the <code>BaseballGame</code>
	 */
	public BaseballGame(Person p) {
		super(p);
		gameType = SportType.BASEBALL;
	}
	
	/**
	 * Create a new <code>BaseballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>BaseballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>BaseballGame</code>
	 * @param p <code>Person</code> who created the <code>BaseballGame</code>; when initializing, this person is also the owner
	 */
	public BaseballGame(Location loc, GregorianCalendar cal, Person p) {
		super(loc, cal, p);
		gameType = SportType.BASEBALL;
	}
	
	/**
	 * Create a new <code>BaseballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>BaseballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>BaseballGame</code>
	 * @param p <code>Person</code> who created the <code>BaseballGame</code>; when initializing, this person is also the owner
	 * @param privacy <code>boolean</code> representing the visibility status of the <code>BaseballGame</code>
	 */
	public BaseballGame(Location loc, GregorianCalendar cal, Person p, boolean privacy) {
		super(loc, cal, p, privacy);
		gameType = SportType.BASEBALL;
	}
	
	/**
	 * Create a new <code>Game</code> from an existing <code>BaseballGame</code>, copying the old game's fields
	 * @param g <code>Game</code> of which the new <code>BaseballGame</code> will be a copy
	 */
	public BaseballGame(Game g) {
		super(g);
		gameType = SportType.BASEBALL;
	}
}
