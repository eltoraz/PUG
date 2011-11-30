package eltoraz.pug.android;

import java.util.ArrayList;

import eltoraz.pug.Game;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.*;

/**
 * This <code>Activtiy</code> displays the Games the user has joined.
 * @author Brian Orecchio
 * @version 0.9
 */
public class ProfileJoinedGamesActivity extends ListActivity {
	private ArrayList<Game> games;
	private ArrayList<String> gameText;

	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Note: the main activity should pass a list of games in the Intent along with the user.
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			games = (ArrayList<Game>) extras.get("games");
		else			// This should theoretically never be called
			games = new ArrayList<Game>();
		
		gameText = new ArrayList<String>();
		for (Game g : games) {
			String game = "";
			game += g.getDate().getTime().toLocaleString();
			game += "\n";
			game += g.getCreator().getName();
			game += "\n";
			game += g.getLocation().getAddress();
			game += "\n";
			game += g.getGameType().toString();
			game += "\n";
			game += g.getDescription();
			gameText.add(game);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, gameText));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
	}
}
