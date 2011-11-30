package eltoraz.pug;

/**
 * The <code>SoccerGame</code> extends <code>Game</code> to add specific functionality.
 * @author Bill Jameson
 * @version 1.0
 */
public class SoccerGame extends Game {
	/**
	 * Eclipse-generated default <code>Serializable</code> ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new <code>SoccerGame</code> with null location and owner at the current date/time.
	 */
	public SoccerGame() {
		super();
		gameType = SportType.SOCCER;
	}
}
