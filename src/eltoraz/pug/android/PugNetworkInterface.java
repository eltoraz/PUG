package eltoraz.pug.android;

import eltoraz.pug.*;

import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

// TODO: error-checking on the HTTP responses

/**
 * The <code>PugNetworkInterface</code> class provides methods to interact with the server.
 * @author Brian Orecchio
 * @version 1.0
 */
public class PugNetworkInterface {
	// NOTE: These getGames functions have a bunch of duplicated code, in the future I will add a way to do this filtering in a single get games 
	//  function by just passing in a map from the filter key to the filter parameter.
	
	/**
	 * Geocode the human-readable address, retrieving its corresponding latitude and longitude from
	 *  Google's servers.
	 * @author Bill Jameson
	 * @param context <code>Context</code> application context
	 * @param addr <code>String</code> the human-readable address to geocode
	 * @return <code>Location</code> corresponding to the address
	 */
	public static Location geocode(Context context, String addr) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> locations = null;
		Location loc = null;
		
		/* Note: There's a known issue with Android emulator > API level 8 where this will always throw an
		 *  IOException. Tested and working on an actual device. Therefore, check to see if running on
		 *  the emulator and if so, use a default lat/lon (in this case, "Troy, ny 12180") for testing purposes.
		 * @reference http://code.google.com/p/android/issues/detail?id=8816
		 */
		if ("google_sdk".equals(Build.PRODUCT)) {
			loc = new Location(42728411, -73691785, "Troy, ny 12180");
			CharSequence msg = "Emulator detected during geocode; using default location.";
			Log.d("PugNetworkInterface.geocode()", msg.toString());
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		else {
			try {
				locations = geocoder.getFromLocationName(addr, 1);
			}
			catch (IOException e) {
				Log.e("IOException", e.getMessage());
				CharSequence errmsg = "Error: Network unavailable. IOException: " + e.getMessage();
				Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
			// Use the first address returned by the geocoder.
			if (locations != null && locations.size()>0) {
				int lat = (int) (locations.get(0).getLatitude() * 1000000);
				int lon = (int) (locations.get(0).getLongitude() * 1000000);
				loc = new Location(lat, lon, addr);
			}
			else {
				// TODO: Make sure this is the only cause of a 0-length/null return from the geocoder
				CharSequence msg = "Invalid address, try another one.";
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		}
		
		return loc;
	}

	/**
	 * Get all the Games from the server.
	 * @return <code>ArrayList</code> containing all the Games the server has in its database
	 */
	public static ArrayList<Game> getGames() {
		ArrayList<Game> games = new ArrayList<Game>();

		String page = "http://pug.myrpi.org/getfilter.php";

		games = getGamesFromServer(page);
		return games;
	}

	/**
	 * Get all Games within a five-mile radius of the specified location from the server.
	 * @param lat <code>int</code> Latitude of the game (in microdegrees)
	 * @param lon <code>int</code> Longitude of the game (in microdegrees)
	 * @return <code>ArrayList</code> containing the requested Games
	 */
	public static ArrayList<Game> getGames(Integer lat, Integer lon) {
		return getGames(lat, lon, 5);
	}

	/**
	 * Get all Games in a location within a specified radius.
	 * @param lat <code>int</code> Latitude of the game (in microdegrees)
	 * @param lon <code>int</code> Longitude of the game (in microdegrees)
	 * @param dist <code>int</code> Maximum distance from the point specified to search (in miles)
	 * @return <code>ArrayList</code> containing the requested Games
	 */
	public static ArrayList<Game> getGames(Integer lat, Integer lon, Integer dist) {
		ArrayList<Game> games = new ArrayList<Game>();

		String page = "http://pug.myrpi.org/getfilter.php";
		page += "?lat=" + lat.toString();
		page += "&lon=" + lon.toString();
		page += "&dist=" + dist.toString();

		games = getGamesFromServer(page);
		return games;
	}

	/**
	 * Get all the Games of the specified sport at the specified location.
	 * @param lat <code>int</code> Latitude of the game (in microdegrees)
	 * @param lon <code>int</code> Longitude of the game (in microdegrees)
	 * @param sport <code>String</code> Sport to filter results by
	 * @return <code>ArrayList</code> containing the requested Games
	 */
	public static ArrayList<Game> getGames(Integer lat, Integer lon, String sport) {
		return getGames(lat, lon, 5, sport);
	}
	
	/**
	 * Get all the Games of the specified sport at the specified location.
	 * @param lat <code>int</code> Latitude of the game (in microdegrees)
	 * @param lon <code>int</code> Longitude of the game (in microdegrees)
	 * @param dist <code>int</code> Maximum distance from the point specified to search (in miles)
	 * @param sport <code>String</code> Sport to filter results by
	 * @return <code>ArrayList</code> containing the requested Games
	 */
	public static ArrayList<Game> getGames(Integer lat, Integer lon, Integer dist, String sport) {
		ArrayList<Game> games = new ArrayList<Game>();

		String page = "http://pug.myrpi.org/getfilter.php";
		page += "?lat=" + lat.toString();
		page += "&lon=" + lon.toString();
		page += "&dist=" + dist.toString();
		page += "&sport=" + sport;
		
		games = getGamesFromServer(page);
		return games;
	}

	/**
	 * Carry out the HTTP request to get all the Games in the server from the filter PHP page with
	 *  the specified parameters
	 * @param page <code>String</code> URL of the PHP filter page, with all arguments specified
	 * @return <code>ArrayList</code> containing the requested Games
	 */
	private static ArrayList<Game> getGamesFromServer(String page) {
		HttpClient httpClient = new DefaultHttpClient();
		ArrayList<Game> Games = new ArrayList<Game>();

		try {			
			HttpGet httpGet = new HttpGet (page);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			JSONArray jsonArray = new JSONArray(EntityUtils.toString(entity));

			Games = parseGameJSONArray(jsonArray);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Games;
	}

	/**
	 * Parse the given JSON array for the contained Games
	 * @param jsonArray <code>JSONArray</code> containing containing the Games
	 * @return <code>ArrayList</code> containing the decoded Games
	 */
	private static ArrayList<Game> parseGameJSONArray(JSONArray jsonArray) {
		Game game;
		ArrayList<Game> games = new ArrayList<Game>();
		
		try {
			for(int i = 0; i < jsonArray.length(); i++) {
				
				// Get the JSON object at index i
				JSONObject gameJson = jsonArray.getJSONObject(i);
				game = Game.buildGame(gameJson);

				// Add the game to the list
				games.add(game);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return games;
	}

	/**
	 * Send the specified <code>Game</code> object to the server to add to the database
	 * @param game <code>Game</code> to send to the server
	 * @return <code>int</code> status of the request
	 */
	public static int sendGame(Game game) {
		HttpClient httpClient = new DefaultHttpClient();

		try{
			//pack the Game into a JSONObject
			JSONObject jsonGame = game.JSON();

			//send the JSONObject to the server for processing using some HTTPClient call or something
			HttpResponse response;
			HttpPost httpPost = new HttpPost ("http://pug.myrpi.org/creategame.php");
			StringEntity se = new StringEntity (jsonGame.toString());
			httpPost.setEntity(se);
			// For debugging.
			//System.out.print(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = httpClient.execute(httpPost);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * Get the <code>Person</code> corresponding to the phone's unique phone ID string.
	 * @param phoneId <code>String</code> The Android device's unique phone ID
	 * @return <code>Person</code> corresponding to the ID, if in the database
	 */
	public static Person getUser(String phoneId) {
		HttpClient httpClient = new DefaultHttpClient();
		Person p = null;

		try {
			String page = "http://pug.myrpi.org/getuser.php";
			page += "?phone=" + phoneId;

			HttpGet httpGet= new HttpGet (page);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			JSONObject jsonObject=new JSONObject(EntityUtils.toString(entity));

			p = new Person(jsonObject);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Send a request to the server to add the user to the specified Game.
	 * @param userid <code>int</code> unique user ID to specify which user is joining 
	 * @param gameid <code>int</code> unique game ID to specify which game to join
	 */
	public static void joinGame(int userid, int gameid) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			String page = "http://pug.myrpi.org/joingame.php";
			page += "?user=" + userid;
			page += "&game=" + gameid;

			HttpGet httpGet= new HttpGet (page);
			HttpResponse response = httpClient.execute(httpGet);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return;
	}
	
	/**
	 * Send a request to the server to remove the user from the specified Game.
	 * @param userid <code>int</code> unique user ID to specify which user is joining 
	 * @param gameid <code>int</code> unique game ID to specify which game to join
	 */
	public static void leaveGame(int userid, int gameid) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			String page = "http://pug.myrpi.org/leavegame.php";
			page += "?user=" + userid;
			page += "&game=" + gameid;

			HttpGet httpGet= new HttpGet (page);
			HttpResponse response = httpClient.execute(httpGet);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return;
	}
	
	/**
	 * Send a request to update the user's profile data on the server.
	 * @param user <code>Person</code> object containing the user's updated details
	 */
	public static void editUser(Person user) {
		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			//pack the Game into a JSONObject
			JSONObject jsonPerson = user.JSON();

			//send the JSONObject to the server for processing using some HTTPClient call or something
			HttpResponse response;
			HttpPost httpPost = new HttpPost ("http://pug.myrpi.org/edituser.php");
			StringEntity se = new StringEntity (jsonPerson.toString());
			httpPost.setEntity(se);
			// For debugging.
			//System.out.print(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = httpClient.execute(httpPost);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	/**
	 * Retrieve a list of games the user has joined.
	 * @param userId <code>int</code> unique user ID
	 * @return <code>ArrayList\<Game\></code> containing the games in which the user is participating
	 */
	public static ArrayList<Game> getJoinedGames(int userId) {
		ArrayList<Game> Games  = new ArrayList<Game>();

		String page = "http://pug.myrpi.org/getgames.php";
		page += "?user=" + userId;

		Games = getGamesFromServer(page);
		return Games;
	}
	
	
	/**
	 * Parse the given JSON array for the contained Person
	 * @param jsonArray <code>JSONArray</code> containing containing the Persons
	 * @return <code>ArrayList</code> containing the decoded Persons
	 */
	private static ArrayList<Person> parsePersonJSONArray(JSONArray jsonArray) {
		Person user;
		ArrayList<Person> users = new ArrayList<Person>();
		
		try {
			for(int i = 0; i < jsonArray.length(); i++) {
				
				// Get the JSON object at index i
				JSONObject personJson = jsonArray.getJSONObject(i);
				user = new Person(personJson);

				// Add the game to the list
				users.add(user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return users;
	}
	
	/**
	 * Retrieve a list of users who have joined the specified game
	 * @param gameId <code>int</code> unique game ID
	 * @return <code>ArrayList\<Person\></code> containing the users who have joined the game
	 */
	public static ArrayList<Person> getPlayers(int gameId) {
		ArrayList<Person> users  = new ArrayList<Person>();

		String page = "http://pug.myrpi.org/getplayers.php";
		page += "?game=" + gameId;

		HttpClient httpClient = new DefaultHttpClient();
		

		try {			
			HttpGet httpGet = new HttpGet (page);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			JSONArray jsonArray = new JSONArray(EntityUtils.toString(entity));

			users = parsePersonJSONArray(jsonArray);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
}
