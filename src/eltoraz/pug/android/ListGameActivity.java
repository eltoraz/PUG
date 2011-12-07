package eltoraz.pug.android;

import eltoraz.pug.Game;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	protected void setOwnedGameDialogPositiveAction(Builder builder, final Game g) {
		// TODO: implement edit button
	}
	
	@Override
	protected void setUnownedGameDialogPositiveAction(Builder builder, final Game g) {
		builder.setPositiveButton(R.string.join, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				PugNetworkInterface.joinGame(user.getId(), g.getId());
				Toast.makeText(getApplicationContext(), "Joined Game!", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
	}
}
