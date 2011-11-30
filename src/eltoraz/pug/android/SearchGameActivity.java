package eltoraz.pug.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eltoraz.pug.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

/**
 * This <code>Activity</code> allows the user to search for Games matching certain parameters.
 * @author Kevin Frame
 * @version 0.5
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
				Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
				String sport = sportSearchSpinner.getSelectedItem().toString();
				List<Address> locations = null;
				int lat = 0, lon = 0;
				
				try {
					locations = geocoder.getFromLocationName(addr, 1);
				}
				catch (IOException e) {
					Log.e("IOException", e.getMessage());
					Context context = getApplicationContext();
					CharSequence errmsg = "Error: Network unavailable. IOException: " + e.getMessage();
					Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				if (locations != null && locations.size()>0) {
					lat = (int) (locations.get(0).getLatitude() * 1000000);
					lon = (int) (locations.get(0).getLongitude() * 1000000);
				}
				else {
					Context context = getApplicationContext();
					CharSequence msg = "Invalid address, try another one.";
					Toast.makeText(context, msg, Toast.LENGTH_SHORT);

					return;
				}
				
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
