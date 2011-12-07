package eltoraz.pug.android;

import java.util.ArrayList;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

// For debugging.
//import android.util.Log;

/**
 * Abstract class to serve as the base for any <code>Activity</code> that displays
 *  a list of Games, allowing such Activities to define their own interaction
 *  behavior.
 * Uses the Template design pattern outlined in the Gang of Four book.
 * @author Kevin Frame
 * @author Bill Jameson
 * @version 1.0
 */
public abstract class PugGamesListActivity extends ListActivity {
	protected Person user;
	protected ArrayList<Game> games;
	protected ArrayList<String> gameText;
	
	protected ListView lv;
	
	static final int EDIT_REQUEST = 2; 

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
		if (extras != null) {
			user = (Person) extras.get("user");
			games = (ArrayList<Game>) extras.get("games");
		}
		else {			// This should theoretically never be called
			user = new Person();
			games = new ArrayList<Game>();
		}
		
		setAdapter();
		
		lv = getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, edit the game if the user owns it, or perform another action based
				//  on the context (e.g., leave the game if the user's already participating)
				String g = ((TextView) view).getText().toString();
				Game arg = games.get(gameText.indexOf(g));
				if (user.getId() == arg.getOwner().getId())
					ownedGameClickAction(arg);
				else
					unownedGameClickAction(arg);
			}
		});
	}
	
	/**
	 * Callback used to retrieve objects returned from Activities.
	 * Here, update the listing of an edited game.
	 * @param requestCode <code>int</code> Unique ID for the Activity returning the result.
	 * @param resultCode <code>int</code> Status indicator for the termination of the Activity.
	 * @param data <code>Intent</code> Container with the returned data.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == EDIT_REQUEST) {
			if (data.hasExtra("game")) {
				Bundle extras = data.getExtras();
				Game oldGame = (Game) extras.get("old_game");
				Game game = (Game) extras.get("game");
				int pos = gameText.indexOf(listItem(oldGame));
				games.remove(pos);
				games.add(pos, game);
				setAdapter();
			}
		}
	}
	
	/**
	 * Set the list adapter for the <code>ListView</code> to match the games
	 *  array.
	 */
	private void setAdapter() {
		gameText = new ArrayList<String>();
		for (Game g : games)
			gameText.add(listItem(g));
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, gameText));
	}
	
	/**
	 * Retrieve a human-readable representation of a Game to be displayed in a list.
	 * @param g <code>Game</code> to be displayed
	 * @return <code>String</code> representing the Game
	 */
	private String listItem(Game g) {
		String game = "";
		if (g.getOwner().getId() == user.getId()) {
			game += "You are this game's owner!";
			game += "\n";
		}
		game += "Time: " + g.getDate().getTime().toLocaleString();
		game += "\n";
		game += "Created by: " + g.getCreator().getName();
		game += "\n";
		game += "Location: " + g.getLocation().getAddress();
		game += "\n";
		game += "Sport: " + g.getGameType().toString();
		game += "\n";
		game += "Description: " + g.getDescription();
		
		return game;
	}
	
	/**
	 * Subclass-defined actions to be performed when the user clicks a Game
	 *  he/she owns.
	 * @param g <code>Game</code> the user selected
	 */
	protected abstract void ownedGameClickAction(Game g);
	/**
	 * Subclass-defined actions to be performed when the user clicks a Game 
	 *  he/she does not own.
	 * @param g <code>Game</code> the user selected
	 */
	protected abstract void unownedGameClickAction(Game g);
}
