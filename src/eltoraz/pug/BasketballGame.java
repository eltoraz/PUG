package eltoraz.pug;

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
		gameType = SportType.BASKETBALL;
	}
}
