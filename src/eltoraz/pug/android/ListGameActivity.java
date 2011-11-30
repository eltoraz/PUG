package eltoraz.pug.android;

import eltoraz.pug.Game;
import java.util.*;

import eltoraz.pug.Location;
import eltoraz.pug.Person;
import eltoraz.pug.Game.SportType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ListGameActivity extends Activity {
	private ArrayList<Game> games;
	private TextView textListGames;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			games = (ArrayList<Game>) extras.get("games");
		}
		else
		{
			games = new ArrayList<Game>();
			// This should theoretically never be called
		}
		
		
			
		textListGames=(TextView) findViewById(R.id.textListGames);
		Log.d("WOW","1");
	
		textListGames.setText(games.get(0).getDescription());
		Log.d("WOW","2");
		//for (int i=0;i<games.size();i++)
	//	{
		//textListGames.append(games.get(i).getDate().getTime().toLocaleString());
	//	textListGames.setText(games.size());
		//textListGames.append(games.get(i).getCreator().getName());
	//	textListGames.append("\n");
		//textListGames.append(games.get(i).getLocation().getAddress());
		//textListGames.append("\n");
	//	textListGames.append(games.get(i).getGameType().toString());
		//textListGames.append("\n");
		//textListGames.append(games.get(i).getDescription().toString());
		//textListGames.append("\n");
		//textListGames.append("\n");
	//	} 
	}
	
}
