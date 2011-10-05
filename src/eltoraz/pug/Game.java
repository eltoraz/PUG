package eltoraz.pug;

import eltoraz.pug.*;
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
	
	// TODO: add fields for additional rules, privacy options
	
	/**
	 * Create a new <code>Game</code> with null location at the current date/time.
	 */
	public Game() {
		location = new Location();
		dateTime = new GregorianCalendar();
	}
}
