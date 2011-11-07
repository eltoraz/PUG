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

public class SearchGameActivity extends Activity {
	private EditText locationEditText;
	private EditText radiusEditText;
	private Button searchSubmitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchgame);
		
		locationEditText = (EditText) findViewById(R.id.locationEditText2);
		radiusEditText = (EditText) findViewById(R.id.radiusEditText);
		searchSubmitButton = (Button) findViewById(R.id.searchSubmitButton);
		
		searchSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String addr = locationEditText.getText().toString();
				int radius = Integer.parseInt(radiusEditText.getText().toString());
				Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
				List<Address> locations = null;
				int lat=0, lon=0;
				
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
				
				ArrayList<Game> games = PugNetworkInterface.getGames(lat, lon, radius);
				
				Intent data = new Intent();
				data.putExtra("games", games);
				setResult(RESULT_OK, data);
				
				finish();
			}
		});
	}
}
