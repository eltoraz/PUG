package eltoraz.pug.android;

import com.google.android.maps.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class PugAndroidActivity extends MapActivity {
	// private LinearLayout linearLayout;
	private MapView mapView;
	
	private Button createGameButton;
	private Intent createGameIntent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// define functionality for the buttons
		createGameButton = (Button) findViewById(R.id.createButton);
		createGameIntent = new Intent(this, CreateGameActivity.class);
		createGameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(createGameIntent);
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