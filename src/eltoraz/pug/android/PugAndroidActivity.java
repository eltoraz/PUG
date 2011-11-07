package eltoraz.pug.android;

import java.util.ArrayList;
import java.util.List;

import eltoraz.pug.*;

import com.google.android.maps.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.graphics.drawable.Drawable;

public class PugAndroidActivity extends MapActivity {
	private MapView mapView;
	private Person user;
	private QuickContactBadge quickContactBadge1;
	private Button createGameButton;
	private Button searchButton;
	
	List<Overlay> mapOverlays;
	Drawable drawable;
	PugItemizedOverlay itemizedOverlay;
	
	private ArrayList<Game> games = new ArrayList<Game>();
	
	static final int SEARCH_REQUEST = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// test user! remove when login is implemented
		user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
		
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
				startActivityForResult(intent, SEARCH_REQUEST);
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
		
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		itemizedOverlay = new PugItemizedOverlay(drawable);
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == SEARCH_REQUEST) {
			if (data.hasExtra("games")) {
				Bundle extras = data.getExtras();
				games = (ArrayList<Game>) extras.get("games");
				plotGames(games);
			}
		}
	}
	
	private void plotGames(ArrayList<Game> games) {
		for (Game g : games) {
			GeoPoint p = g.GeoPoint();
			OverlayItem overlayItem = new OverlayItem(p, "", "");
			itemizedOverlay.addOverlay(overlayItem);
			mapOverlays.add(itemizedOverlay);
		}
	}
}
