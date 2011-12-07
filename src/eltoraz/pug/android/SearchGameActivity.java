package eltoraz.pug.android;

import java.util.ArrayList;

import eltoraz.pug.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * This <code>Activity</code> allows the user to search for Games matching certain parameters.
 * @author Kevin Frame
 * @version 1.0
 */
public class SearchGameActivity extends Activity {
	/* ***** UI ELEMENTS ***** */
	private EditText locationEditText;
	private EditText radiusEditText;
	private ToggleButton allTypesToggleButton;
	private Spinner sportSearchSpinner;
	private Button searchSubmitButton;

	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchgame);
		
		/* ***** CAPTURE UI ELEMENTS ***** */
		locationEditText = (EditText) findViewById(R.id.locationEditText2);
		searchSubmitButton = (Button) findViewById(R.id.searchSubmitButton);
		
		radiusEditText = (EditText) findViewById(R.id.radiusEditText);
		radiusEditText.setText("5");
		
		sportSearchSpinner = (Spinner) findViewById(R.id.sportSearchSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sports_array,
				 android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sportSearchSpinner.setAdapter(adapter);
		sportSearchSpinner.setFocusable(false);
		sportSearchSpinner.setFocusableInTouchMode(false);
		sportSearchSpinner.setClickable(false);
		
		// When the user toggles this button, enable/disable the spinner based on what they selected
		allTypesToggleButton = (ToggleButton) findViewById(R.id.allGamesToggleButton);
		allTypesToggleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (allTypesToggleButton.isChecked()) {
					sportSearchSpinner.setFocusable(false);
					sportSearchSpinner.setFocusableInTouchMode(false);
					sportSearchSpinner.setClickable(false);
				}
				else {
					sportSearchSpinner.setFocusable(true);
					sportSearchSpinner.setFocusableInTouchMode(true);
					sportSearchSpinner.setClickable(true);
				}
			}
		});
		
		// When the user clicks the button, query the server using the parameters he/she entered
		searchSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<Game> games = null;
				
				// TODO: Input validation (this crashes if the user doesn't enter anything!)
				String addr = locationEditText.getText().toString();
				int radius = Integer.parseInt(radiusEditText.getText().toString());
				String sport = sportSearchSpinner.getSelectedItem().toString();
				
				Location loc = PugNetworkInterface.geocode(getApplicationContext(), addr);
				if (loc == null)
					return;
				int lat = loc.getLat(), lon = loc.getLon();
				
				if (allTypesToggleButton.isChecked())
					games = PugNetworkInterface.getGames(lat, lon, radius);
				else
					games = PugNetworkInterface.getGames(lat, lon, radius, sport);
				
				Intent data = new Intent();
				data.putExtra("games", games);
				setResult(RESULT_OK, data);
				
				finish();
			}
		});
	}
}
