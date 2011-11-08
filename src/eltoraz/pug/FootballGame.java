package eltoraz.pug;

/**
 * The <code>FootballGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 0.1
 */
public class FootballGame extends Game {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new <code>FootballGame</code> with null location and owner at the current date/time.
	 */
	public FootballGame() {
		super();
		gameType = SportType.FOOTBALL;
	}
}
