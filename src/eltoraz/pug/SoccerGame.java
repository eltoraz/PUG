package eltoraz.pug;

/**
 * The <code>SoccerGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 0.1
 */
public class SoccerGame extends Game {
	/**
	 * Create a new <code>SoccerGame</code> with null location and owner at the current date/time.
	 */
	public SoccerGame() {
		super();
		gameType = SportType.SOCCER;
	}
}
