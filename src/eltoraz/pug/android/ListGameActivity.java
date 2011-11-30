package eltoraz.pug.android;

import eltoraz.pug.Game;
import eltoraz.pug.Person;

import java.util.*;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// For debugging.
//import android.util.Log;

/**
 * This <code>Activity</code> displays a list of the Games the user has searched for.
 * For best results, store a list of Games in the <code>Intent</code> used to start this <code>Activity</code>.
 * @author Kevin Frame
 * @author Bill Jameson
 * @version 0.8
 * @reference http://developer.android.com/resources/tutorials/views/hello-listview.html
 */
public class ListGameActivity extends ListActivity {
	private Person user;
	private ArrayList<Game> games;
	private ArrayList<String> gameText;
	//private TextView textListGames;

	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.list);

		//textListGames = (TextView) findViewById(R.id.textListGames);

		// Note: the main activity should pass a list of games in the Intent.
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			user = (Person) extras.get("user");
			games = (ArrayList<Game>) extras.get("games");
		}
		else {			// This should theoretically never be called
			user = new Person();
			games = new ArrayList<Game>();
		}
		
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
			game += "\n\n";
			gameText.add(game);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, gameText));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, join the Game and show a toast with the TextView text
				String g = ((TextView) view).getText().toString();
				PugNetworkInterface.joinGame(user.getId(), games.get(gameText.indexOf(g)).getId());
				Toast.makeText(getApplicationContext(), "Joined Game!", Toast.LENGTH_LONG).show();
			}
		});

		// For debugging.
		// Log.d("WOW","1");

		// if(games != null) {
		// textListGames.setText(games.get(0).getDescription());
		// Log.d("WOW",games.get(0).getDescription());
		// }

		// Add each game to the list.
		/*
		textListGames.setText("");
		for (int i = 0; i < games.size(); i++) {
			textListGames.append(games.get(i).getDate().getTime().toLocaleString());
			textListGames.append("\n");
			textListGames.append(games.get(i).getCreator().getName());
			textListGames.append("\n");
			textListGames.append(games.get(i).getLocation().getAddress());
			textListGames.append("\n");
			textListGames.append(games.get(i).getGameType().toString());
			textListGames.append("\n");
			textListGames.append(games.get(i).getDescription().toString());
			textListGames.append("\n");
			textListGames.append("\n");
		}
		*/
	}
}
