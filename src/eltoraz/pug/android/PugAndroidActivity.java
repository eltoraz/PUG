package eltoraz.pug.android;

import java.util.ArrayList;
import android.telephony.*;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import eltoraz.pug.*;

import com.google.android.maps.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	private Button listGamesButton;
	
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private PugItemizedOverlay itemizedOverlay;
	
	private ArrayList<Game> games = new ArrayList<Game>();
	
	static final int SEARCH_REQUEST = 0;
	static final int PROFILE_REQUEST = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// test user! remove when login is implemented
		user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
		
		Authenticate();
	
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
				startActivity(intent);
			}
		});
		
		quickContactBadge1 = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
		quickContactBadge1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileActivity.class);
				intent.putExtra("user", user);
				startActivityForResult(intent, PROFILE_REQUEST);
			}
		});
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		itemizedOverlay = new PugItemizedOverlay(drawable);
		
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
	
		//Toast toast = Toast.makeText(context, text, duration);
		//toast.show();
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
		if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_REQUEST) {
			if (data.hasExtra("user")) {
				Bundle extras = data.getExtras();
				user = (Person) extras.get("user");
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
	public  boolean Authenticate() {
		TelephonyManager tm=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String deviceid=tm.getDeviceId();
		boolean Auth=false;
		Person p;
	
		String page = new String();
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
		 page = "http://pug.myrpi.org/";
		 page = page + "checkuser.php?phone="+deviceid ;
		 
		 HttpGet httpget= new HttpGet (page);
		 HttpResponse response = httpclient.execute(httpget);
		 
		 HttpEntity entity = response.getEntity();
         String temp = new String();
         temp = EntityUtils.toString(entity);
         JSONObject jsonOb=new JSONObject(temp);
       
         
        Auth= jsonOb.getBoolean("exists");
         
        if (Auth) {
			 	Context context=getApplicationContext();
				CharSequence text = "Welcome!";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
				p=PugNetworkInterface.getUser(deviceid);
				
				Context context1=getApplicationContext();
				CharSequence text1 = p.getName().toString();
				int duration1 = Toast.LENGTH_SHORT;
				Toast toast1 = Toast.makeText(context1, text1, duration1);
				toast1.show();
	        }
        
        else {
	        	Context context=getApplicationContext();
				CharSequence text = "Authentication Failed!";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
	        }
        
		}
		 catch(Exception e) {
				e.printStackTrace();
				Context context=getApplicationContext();
				CharSequence text = "Whoops!";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		 return Auth;
	}
}

