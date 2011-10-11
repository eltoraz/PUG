package eltoraz.pug.android;

import android.os.Bundle;
import com.google.android.maps.*;
import android.widget.*;

public class PugAndroidActivity extends MapActivity {
	private LinearLayout linearLayout;
	private MapView mapView;
	
	private Button createGameButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// define functionality for the buttons
		createGameButton = (Button) findViewById(R.id.create);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}




}