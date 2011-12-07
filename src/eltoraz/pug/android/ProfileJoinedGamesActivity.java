package eltoraz.pug.android;

import eltoraz.pug.Game;
import android.app.*;
import android.content.Intent;
import android.widget.*;
import android.content.*;
import android.os.Bundle;
import android.util.*;

/**
 * This <code>Activity</code> displays the Games the user has joined.
 * 
 * @author Brian Orecchio
 * @author Bill Jameson
 * @version 1.0
 */
public class ProfileJoinedGamesActivity extends PugGamesListActivity {

	@Override
	protected void ownedGameClickAction(Game g) {
		Intent intent = new Intent(getApplicationContext(),
				EditGameActivity.class);
		intent.putExtra("user", user);
		intent.putExtra("game", g);
		startActivityForResult(intent, EDIT_REQUEST);
	}

	@Override
	protected void unownedGameClickAction(Game g) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);

		if (g.getGameType().toString().compareTo("Basketball") == 0) {
			builder.setIcon(R.drawable.basketball);
		}

		if (g.getGameType().toString().compareTo("Baseball") == 0) {
			builder.setIcon(R.drawable.baseball);
		}

		if (g.getGameType().toString().compareTo("Football") == 0) {
			builder.setIcon(R.drawable.football);
		}

		if (g.getGameType().toString().compareTo("Soccer") == 0) {
			builder.setIcon(R.drawable.soccer);
		}

		builder.setTitle("Leave Game");
		builder.setInverseBackgroundForced(true);

		final String game = listItem(g);

		builder.setMessage(game);

		builder.setPositiveButton("Leave Game",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Game arg=games.get(gameText.indexOf(game));
						PugNetworkInterface.leaveGame(user.getId(), arg.getId());
						Toast.makeText(getApplicationContext(), "Left Game!",
								Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});

		builder.setNegativeButton("Exit",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}
}
