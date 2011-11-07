package eltoraz.pug;

import java.util.GregorianCalendar;

/**
 * The <code>FootballGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 0.1
 */
public class FootballGame extends Game {
	/**
	 * Create a new <code>FootballGame</code> with null location and owner at the current date/time.
	 */
	public FootballGame() {
		super();
		gameType = SportType.FOOTBALL;
	}
	
	/**
	 * Create a new <code>FootballGame</code> with the specified <code>Person</code> as the creator/owner
	 * @param p <code>Person</code> who created the <code>FootballGame</code>
	 */
	public FootballGame(Person p) {
		super(p);
		gameType = SportType.FOOTBALL;
	}
	
	/**
	 * Create a new <code>FootballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>FootballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>FootballGame</code>
	 * @param p <code>Person</code> who created the <code>FootballGame</code>; when initializing, this person is also the owner
	 */
	public FootballGame(Location loc, GregorianCalendar cal, Person p) {
		super(loc, cal, p);
		gameType = SportType.FOOTBALL;
	}
	
	/**
	 * Create a new <code>FootballGame</code> with the specified creator/owner, at the specified location, at the specified time.
	 * @param loc <code>Location</code> for the <code>FootballGame</code>
	 * @param cal <code>GregorianCalendar</code> specifying the date and time of the <code>FootballGame</code>
	 * @param p <code>Person</code> who created the <code>FootballGame</code>; when initializing, this person is also the owner
	 * @param privacy <code>boolean</code> representing the visibility status of the <code>FootballGame</code>
	 */
	public FootballGame(Location loc, GregorianCalendar cal, Person p, boolean privacy) {
		super(loc, cal, p, privacy);
		gameType = SportType.FOOTBALL;
	}
	
	/**
	 * Create a new <code>Game</code> from an existing <code>FootballGame</code>, copying the old game's fields
	 * @param g <code>Game</code> of which the new <code>FootballGame</code> will be a copy
	 */
	public FootballGame(Game g) {
		super(g);
		gameType = SportType.FOOTBALL;
	}
}
