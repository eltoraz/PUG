package eltoraz.pug.android;

import eltoraz.pug.*;

import com.google.android.maps.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class PugAndroidActivity extends MapActivity {
	// private LinearLayout linearLayout;
	private MapView mapView;
	private Person user;
	
	private Button createGameButton;

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
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}




}