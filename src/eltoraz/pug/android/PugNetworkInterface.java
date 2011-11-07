package eltoraz.pug.android;

import eltoraz.pug.*;

//import java.lang.reflect.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.lang.String;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 * The class <code>PugNetworkInterface</code> provides functions to get data from the database
 * @author Brian Orecchio
 * @version 0.1
 */
public class PugNetworkInterface {

	protected JsonObjectInterface jsonInterface;  //this will be phased out
	protected HttpClient httpclient;
	
	/**
	 * Default Constructor
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public PugNetworkInterface() {
		jsonInterface = new JsonObjectInterface();
		httpclient= new DefaultHttpClient();
	}
	
	//NOTE: These getGames functions have a bunch of duplicated code, in the future I will add a way to do this filtering in a single get games 
	//function by just passing in a map from the filter key to the filter parameter
	
	/**
	 * The function <code>getGames</code> gets all the games in the database and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public ArrayList<Game> getGames() {
		
		ArrayList<Game> Games  = new ArrayList<Game>();
		
		 try {
	            
	       
			 String page = new String();
				
			 page = "http://pug.myrpi.org/";
			 page = page + "getfilter.php";
			
			 Games = getGamesFromServer(page);
	            
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		return Games;
		
	}
	
	/**
	 * The function <code>getGames(Integer lat, Integer lon)</code> gets all the games around a location in a 5 mile radius and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public ArrayList<Game> getGames(Integer lat, Integer lon) {
		
		ArrayList<Game> Games  = new ArrayList<Game>();
		
		try{
			    
			String page = new String();
			
			page = "http://pug.myrpi.org/";
			page = page + "getfilter.php" + "?lat=" + lat.toString() + "&lon=" + lon.toString() + "&dist=5";
			
			Games = getGamesFromServer(page);
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Games;
	}
	
	/**
	 * The function <code>getGames(Integer lat, Integer lon, int dist)</code> gets all the games around a location in a specified radius and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public ArrayList<Game> getGames(Integer lat, Integer lon, Integer dist) {
		
		ArrayList<Game> Games  = new ArrayList<Game>();
		
		try{
			    
			String page = new String();
			
			page = "http://pug.myrpi.org/";
			page = page + "getarea.php" + "?lat=" + lat.toString() + "&lon=" + lon.toString() + "&dist=" + dist.toString();
			
			Games = getGamesFromServer(page);
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Games;
	}
	
	/**
	 * The function <code>getGames(String sport)</code> gets all the games around a location of a certain sport and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public ArrayList<Game> getGames(Integer lat, Integer lon, String sport) {
		
		ArrayList<Game> Games  = new ArrayList<Game>();
		
		try{
			    
			String page = new String();
			
			page = "http://pug.myrpi.org/";
			page = page + "getarea.php" + "?lat=" + lat.toString() + "&lon=" + lon.toString() + "&sport=" + sport;
			
			Games = getGamesFromServer(page);
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Games;
	}
	
	
	
	/**
	 * The function <code>retrieveGamesFromServer</code> carries out the http request to get all the games in the database from the filter php page with the specified arguments in 'page' and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	private ArrayList<Game> getGamesFromServer(String page) {
		
		ArrayList<Game> Games  = new ArrayList<Game>();

		try{			
			
            HttpGet httpget= new HttpGet (page);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String temp = new String();
            temp = EntityUtils.toString(entity);
            JSONArray jsonArray=new JSONArray(temp);
            
            Games = parseGameJSONArray(jsonArray);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Games;
	}
	
	/**
	 * The function <code>parseGameJSONArray</code> parses the JSON array and returns the Games as an <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	private ArrayList<Game> parseGameJSONArray( JSONArray jsonArray )
	{
		Game game;
		ArrayList<Game> Games  = new ArrayList<Game>();
		try{
		
			for(int i=0; i<=jsonArray.length(); i++)
			{
	         	//Integer j = i;
	         	//String idstring = new String(j.toString());
	         	//get the json referred to by id i
	         	JSONObject gameJson = jsonArray.getJSONObject(i);
	         	
	         	//unpack the game json into a Game
	         	//game = jsonInterface.unpackGame(gameJson);
	         	
	         	game = Game.buildGameFromJSON(gameJson);
	         	
	         	//Add the game to the ArrayList
	         	Games.add(game);
         	
			}
		 
			return Games;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * The function <code>sendGame</code> takes a <code>Game</code> object as a parameter and sends it to the database
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public int sendGame(Game game) {
		
		try{
			//pack the Game into a JSONObject
			//JSONObject jsonGame = jsonInterface.packGame(game);  //this will change to what is below
			JSONObject jsonGame = game.JSON();
			
			//send the JSONObject to the server for processing using some HTTPClient call or something
            HttpResponse response;
            HttpPost httppost= new HttpPost ("http://pug.myrpi.org/creategame.php");
            StringEntity se=new StringEntity (jsonGame.toString());
            httppost.setEntity(se);
            System.out.print(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            response=httpclient.execute(httppost);
            
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
