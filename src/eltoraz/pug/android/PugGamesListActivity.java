package eltoraz.pug.android;

import java.util.ArrayList;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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
	
	private ArrayList<Game> games;
	private ArrayList<String> gameText;
	private ArrayList<ArrayList<Person>> gamePlayers;
	private ArrayList<Integer> listedGameIds;
	
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
		
		gamePlayers = new ArrayList<ArrayList<Person>>();
		listedGameIds = new ArrayList<Integer>();
		
		setAdapter();
		
		lv = getListView();
		lv.setTextFilterEnabled(true);
		
		// only allow clicking on list items if they correspond to Games and not the "no games" message
		if (games.size() > 0) {
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
		for (Game g : games) {
			listedGameIds.add(g.getId());
			gamePlayers.add(PugNetworkInterface.getPlayers(g.getId()));
			gameText.add(listItem(g));
		}
		
		if (games.size() == 0)
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.array.no_games));
		else
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
		game += "Sport: " + g.getGameType().toString();
		game += "\n";
		game += "Time: " + g.getDate().getTime().toLocaleString();
		game += "\n";
		game += "Location: " + g.getLocation().getAddress();
		game += "\n";
		game += "Created by: " + g.getCreator().getName();
		game += "\n";
		game += "Number of players: " + gamePlayers.get(listedGameIds.indexOf(g.getId())).size();
		game += "\n";
		game += "Description: " + g.getDescription();
		
		return game;
	}
	
	/**
	 * Retrieve a list of the registered players for the specified Game.
	 * @param g <code>Game</code> whose players are to be displayed
	 * @return <code>String</code> containing newline-separated list of players
	 */
	private String listPlayers(Game g) {
		boolean playerRegistered = false;
		String players = "";
		int pos = listedGameIds.indexOf(g.getId());
		for (Person p : gamePlayers.get(pos)) {
			if (p.getId() == user.getId()) {
				playerRegistered = true;
				continue;
			}
			players += "\n" + p.getName();
		}
		if (playerRegistered)
			players += "\n" + "You are registered for this Game!";
		
		return players;
	}
	
	/**
	 * Subclass-defined actions to be performed when the user clicks a Game
	 *  he/she owns.
	 * @param g <code>Game</code> the user selected
	 */
	private void ownedGameClickAction(Game g) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// Build and display the game details dialog
		buildDialog(builder, g);
		setOwnedGameDialogPositiveAction(builder, g);
		showDialog(builder);
	}
	
	/**
	 * Display a dialog to the user with the details of the selected Game
	 * @param g <code>Game</code> the user selected
	 */
	private void unownedGameClickAction(Game g) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// Build and display the game details dialog
		buildDialog(builder, g);
		setUnownedGameDialogPositiveAction(builder, g);
		showDialog(builder);
	}
	
	/**
	 * Build a dialog to display game details.
	 * An intermediate step should be called after this to set the positive
	 *  action, which may vary from Activity-to-Activity.
	 * @param builder <code>AlertDialog.Builder</code> Dialog builder
	 * @param g <code>Game</code> whose details will be displayed
	 */
	private void buildDialog(AlertDialog.Builder builder, Game g) {
		builder.setCancelable(true);
		
		String gameType = g.getGameType().toString();

		if (gameType.equalsIgnoreCase("basketball"))
			builder.setIcon(R.drawable.basketball);
		else if (gameType.equalsIgnoreCase("baseball"))
			builder.setIcon(R.drawable.baseball);
		else if (gameType.equalsIgnoreCase("football"))
			builder.setIcon(R.drawable.football);
		else if (gameType.equalsIgnoreCase("soccer"))
			builder.setIcon(R.drawable.soccer);

		builder.setTitle(R.string.details_label);
		builder.setInverseBackgroundForced(true);
		
		// TODO: change contents of dialog to include player list
		String gString = listItem(g);
		gString += "\n" + "Players Registered:" + listPlayers(g);

		builder.setMessage(gString);

		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * Display the dialog.
	 * @param builder <code>AlertDialog.Builder</code> configured with the dialog's settings
	 */
	private void showDialog(AlertDialog.Builder builder) {
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/**
	 * Set the positive action for a Game the user owns in the details dialog.
	 * This can include actions like editing the game.
	 * @param builder <code>AlertDialog.Builder</code> Dialog builder being used to construct the dialog
	 * @param g <code>Game</code> the dialog is displaying
	 */
	protected abstract void setOwnedGameDialogPositiveAction(AlertDialog.Builder builder, final Game g);
	/**
	 * Set the positive action for a Game the user does not own in the details dialog.
	 * This can include actions like joining or leaving the game.
	 * @param builder <code>AlertDialog.Builder</code> Dialog builder being used to construct the dialog
	 * @param g <code>Game</code> the dialog is displaying
	 */
	protected abstract void setUnownedGameDialogPositiveAction(AlertDialog.Builder builder, final Game g);
}
