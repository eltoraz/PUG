package eltoraz.pug.android;

import eltoraz.pug.*;

import java.lang.reflect.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * An <code>Activity</code> for game creation based on user specifications
 * @author Bill Jameson
 * @version 0.1
 */
public class CreateGameActivity extends Activity {
	private Person user;
	private Game game;
	private Spinner sportSelectSpinner;
	
	public class CreateGameOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			// avoid exceptions by making sure to reference only classes that exist and have a constructor
			//   taking a Person as an argument
			try {
				// use reflection to create the right type of game
				// Note: all the options in this spinner must have a corresponding class that extends Game
				// Adapted from sample code at http://www.rgagnon.com/javadetails/java-0351.html
				String gameType = "eltoraz.pug." + parent.getItemAtPosition(pos).toString() + "Game";
				Class<?> cl = Class.forName(gameType);
				java.lang.reflect.Constructor<?> constructor = cl.getConstructor(new Class[] {Person.class});
				Object newGameType = constructor.newInstance(new Object[] {user});
				game = (Game) newGameType;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void onNothingSelected(AdapterView<?> parent) {
			// do nothing
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creategame);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Test User", -1);
		
		/* ***** DEFINE	UI ELEMENT FUNCTIONALITY ***** */
		
		// GAME SELECTION SPINNER
		sportSelectSpinner = (Spinner) findViewById(R.id.sportSelectSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sports_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sportSelectSpinner.setAdapter(adapter);
		sportSelectSpinner.setOnItemSelectedListener(new CreateGameOnItemSelectedListener());
	}
}
