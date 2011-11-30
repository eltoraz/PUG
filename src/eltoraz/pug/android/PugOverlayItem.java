package eltoraz.pug.android;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import eltoraz.pug.*;

/**
 * A map overlay for displaying a Game at a specific location.
 * @author Brian Orecchio
 * @version 1.0
 */
public class PugOverlayItem extends OverlayItem {
	protected Game game;
	protected Person user;
	
	public PugOverlayItem(GeoPoint point, String title, String snippet, Game a_game, Person a_user) {
		super(point, title, snippet);
		game = a_game;
		user = a_user;
	}
	
	/**
	 * Get the game this overlay represents. 
	 * @return <code>Game</Game>
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Get the current user.
	 * @return <code>Person</code>
	 */
	public Person getUser() {
		return user;
	}	
}
