package eltoraz.pug.android;

import eltoraz.pug.Game;

import android.content.Intent;
import android.widget.Toast;

/**
 * This <code>Activity</code> displays the Games the user has joined.
 * @author Brian Orecchio
 * @author Bill Jameson
 * @version 1.0
 */
public class ProfileJoinedGamesActivity extends PugGamesListActivity {
	@Override
	protected void ownedGameClickAction(Game g) {
		Intent intent = new Intent(getApplicationContext(), EditGameActivity.class);
		intent.putExtra("user", user);
		intent.putExtra("game", g);
		startActivityForResult(intent, EDIT_REQUEST);
	}
	
	@Override
	protected void unownedGameClickAction(Game g) {
		PugNetworkInterface.leaveGame(user.getId(), g.getId());
		Toast.makeText(getApplicationContext(), "Left Game!", Toast.LENGTH_LONG).show();
	}
}
