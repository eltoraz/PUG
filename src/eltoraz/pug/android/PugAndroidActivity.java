package eltoraz.pug.android;

import java.util.ArrayList;
import android.telephony.*;
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

// Logger imports
//import android.util.Log;

/**
 * The main <code>Activity</code> for the PickUpGames app. This class displays
 *  a map as the app's home screen, providing buttons to access other screens.
 * @author Kevin Frame
 * @author Bill Jameson
 * @author Brian Orecchio
 * @version 1.0
 */
public class PugAndroidActivity extends MapActivity {
	/* ***** UI ELEMENTS ***** */
	private MapView mapView;
	private Person user;
	private QuickContactBadge profileBadge;
	private Button createGameButton;
	private Button searchButton;
	private Button listGamesButton;
	
	private List<Overlay> mapOverlays;
	
	private ArrayList<Game> games = new ArrayList<Game>();
	
	/* ***** ACTIVITY RESULT CODES ***** */
	static final int SEARCH_REQUEST = 0;
	static final int PROFILE_REQUEST = 1;
	static final int EDIT_REQUEST = 2;
	
	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		user = authenticate();

		// Capture the UI elements and define their functionality.
		
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
		
		listGamesButton = (Button) findViewById(R.id.listGamesButton);
		listGamesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ListGameActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("games", games);
				startActivity(intent);
			}
		});
		
		profileBadge = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
		profileBadge.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileTabWidget.class);
				intent.putExtra("user", user);
				startActivityForResult(intent, PROFILE_REQUEST);
			}
		});
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		mapOverlays = mapView.getOverlays();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	/**
	 * Callback used to retrieve objects returned from Activities.
	 * @param requestCode <code>int</code> Unique ID for the Activity returning the result.
	 * @param resultCode <code>int</code> Status indicator for the termination of the Activity.
	 * @param data <code>Intent</code> Container with the returned data.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == SEARCH_REQUEST) {
			if (data.hasExtra("games")) {
				Bundle extras = data.getExtras();
				games = (ArrayList<Game>) extras.get("games");
				if( !games.isEmpty()) {
					plotGames(games);
				}
				else {
					Context context = getApplicationContext();
					CharSequence text = "No Games Found";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_REQUEST) {
			if (data.hasExtra("user")) {
				Bundle extras = data.getExtras();
				user = (Person) extras.get("user");
			}
		}
	}
	
	/**
	 * Plot a list of Games on the map.
	 * @param games <code>ArrayList</code> containing the Games to plot.
	 */
	private void plotGames(ArrayList<Game> games) {
		for (Game g : games) {
			GeoPoint p = g.GeoPoint();
			
			String sport = g.getGameType().toString();   //get the sport type to display
			String title = sport + " AT " + g.getLocation().getAddress();  //set the title of the over lay
			String descr = g.getDate().getTime().toLocaleString() + ":  " + g.getDescription();   //set the overlay time and description
			
			MapController mc = mapView.getController();
			mc.animateTo(p);
			mc.setZoom(16);
			PugOverlayItem overlayItem = new PugOverlayItem(p,title,descr,g,user);
			//itemizedOverlay.addOverlay(overlayItem);

			Drawable drawable = findSportIcon(g.getGameType());
			PugBalloonItemizedOverlay customItemizedOverlay = new PugBalloonItemizedOverlay(drawable,mapView);
			customItemizedOverlay.addOverlay(overlayItem);
			
			mapOverlays.add(customItemizedOverlay);
		}
		return;
	}

	/**
	 * Select the correct icon to correspond to the sport type.
	 * @param sporttype <code>enum Game.SportType</code>
	 * @return <code>Drawable</code> sport icon
	 */
	private Drawable findSportIcon(Game.SportType sporttype) {

		Drawable drawable;
		switch (sporttype.ordinal()) {
		case 0:
			drawable = this.getResources().getDrawable(R.drawable.basketball);
			break;
		case 1:
			drawable = this.getResources().getDrawable(R.drawable.baseball);
			break;
		case 2:
			drawable = this.getResources().getDrawable(R.drawable.football);
			break;
		case 3:
			drawable = this.getResources().getDrawable(R.drawable.soccer);
			break;

		default:
			drawable = this.getResources().getDrawable(R.drawable.androidmarker);
			break;
		}

		return drawable;
	}

	
	/**
	 * Authenticate the user with the server to allow them to keep a persistent profile.
	 * @return <code>true</code> if the user exists in the server, <code>false</code> otherwise.
	 */
	private Person authenticate() {
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		Person p = new Person();

	
		Context context = getApplicationContext();
		CharSequence text = "Welcome!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		p = PugNetworkInterface.getUser(deviceId);

		Context context1 = getApplicationContext();
		CharSequence text1 = p.getName().toString();
		int duration1 = Toast.LENGTH_SHORT;
		Toast toast1 = Toast.makeText(context1, text1, duration1);
		toast1.show();
			
		return p;
	}
}

