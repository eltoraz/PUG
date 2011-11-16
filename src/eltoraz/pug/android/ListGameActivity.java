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

	private Person user;
	private ArrayList<Game> currentGames=PugNetworkInterface.getGames();
	private TextView textListGames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
		
		textListGames=(TextView) findViewById(R.id.textListGames);
		textListGames.setText(currentGames.get(0).getDescription().toString());
	}
	
}
