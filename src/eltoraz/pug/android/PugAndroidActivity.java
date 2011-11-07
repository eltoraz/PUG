package eltoraz.pug.android;

import eltoraz.pug.*;

import com.google.android.maps.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.*;
import android.content.*;

public class PugAndroidActivity extends MapActivity {
	// private LinearLayout linearLayout;
	private MapView mapView;
	private Person user;
	private QuickContactBadge quickContactBadge1;
	private Button createGameButton;
	private Button searchButton;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		user = new Person("Test User", -1);
		
		// define functionality for the buttons
		createGameButton = (Button) findViewById(R.id.createButton);
		createGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CreateGameActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SearchGameActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		
		quickContactBadge1 = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
		quickContactBadge1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		// TODO: display games on map
		// @reference http://stackoverflow.com/questions/2349095/google-map-dialog-info-window-not-appearing-on-touch
		// @reference http://mobiforge.com/developing/story/using-google-maps-android
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onResume() {
	super.onResume();
	Context context = getApplicationContext();
	CharSequence text = "Hello toast!";
	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(context, text, duration);
	toast.show();
	
	}
	
}
