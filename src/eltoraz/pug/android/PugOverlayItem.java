package eltoraz.pug.android;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import eltoraz.pug.*;

/**
 * This class allows the passing of a <code>Game</code> and <code>Person</code> (user) into the overlay
 * @author oreccb
 *
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
	 * get the game
	 * @return <code>Game</Game>
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * get the user
	 * @return <code>Person</code> that is the user
	 */
	public Person getUser() {
		return user;
	}
	
}
