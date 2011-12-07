package eltoraz.pug.android;

import eltoraz.pug.Game;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.*;

/**
 * This <code>Activity</code> displays the Games the user has joined.
 * 
 * @author Brian Orecchio
 * @author Bill Jameson
 * @version 1.0
 */
public class ProfileJoinedGamesActivity extends PugGamesListActivity {
	@Override
	protected void setOwnedGameDialogPositiveAction(Builder builder, final Game g) {
		builder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getApplicationContext(), EditGameActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("game", g);
				startActivityForResult(intent, EDIT_REQUEST);
				dialog.dismiss();
			}
		});
	}
	
	@Override
	protected void setUnownedGameDialogPositiveAction(Builder builder, final Game g) {
		builder.setPositiveButton(R.string.leave, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				PugNetworkInterface.leaveGame(user.getId(), g.getId());
				Toast.makeText(getApplicationContext(), "Left Game!", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
	}
}
