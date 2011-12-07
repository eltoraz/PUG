package eltoraz.pug.android;

import eltoraz.pug.Game;
import android.app.AlertDialog;
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
	protected void ownedGameClickAction(Game g) {
	}
	
	@Override
	protected void unownedGameClickAction(Game g) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		if (g.getGameType().toString().compareTo("Basketball")==0){
			builder.setIcon(R.drawable.basketball);
		}
		
		if (g.getGameType().toString().compareTo("Baseball")==0){
			builder.setIcon(R.drawable.baseball);
		}
		
		if (g.getGameType().toString().compareTo("Football")==0){
			builder.setIcon(R.drawable.football);
		}
		
		if (g.getGameType().toString().compareTo("Soccer")==0){
			builder.setIcon(R.drawable.soccer);
		}
	
		builder.setTitle("Join Game");
		builder.setInverseBackgroundForced(true);
		
					
			final String game = listItem(g);
			builder.setMessage(game);
		
		builder.setPositiveButton("Join Game", new DialogInterface.OnClickListener() {
	
		@Override
		  public void onClick(DialogInterface dialog, int which) {
			Game arg=games.get(gameText.indexOf(game));
			PugNetworkInterface.joinGame(user.getId(), arg.getId());
			Toast.makeText(getApplicationContext(), "Joined Game!", Toast.LENGTH_LONG).show();
		  	dialog.dismiss();
		  }
		});
		builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
	
		@Override
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  }
		});
			AlertDialog alert = builder.create();
			alert.show();
		
	}
}
