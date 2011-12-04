package eltoraz.pug.android;

import eltoraz.pug.Game;
import android.widget.Toast;

// For debugging.
//import android.util.Log;

/**
 * This <code>Activity</code> displays a list of the Games the user has searched for.
 * @author Kevin Frame
 * @author Bill Jameson
 * @version 1.0
 * @reference http://developer.android.com/resources/tutorials/views/hello-listview.html
 */
public class ListGameActivity extends PugGamesListActivity {
	@Override
	protected void ownedGameClickAction(Game g) {
	}
	
	@Override
	protected void unownedGameClickAction(Game g) {
		PugNetworkInterface.joinGame(user.getId(), g.getId());
		Toast.makeText(getApplicationContext(), "Joined Game!", Toast.LENGTH_LONG).show();
	}
}
