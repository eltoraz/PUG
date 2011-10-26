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

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 * The class <code>PugNetworkInterface</code> provides functions to get data from the database
 * @author Brian Orecchio
 * @version 0.1
 */
public class PugNetworkInterface {

	protected JsonObjectInterface jsonInterface;
	
	public PugNetworkInterface() {
		jsonInterface = new JsonObjectInterface();
	}
	
	/**
	 * The function <code>getAllGames</code> gets all the games in the database and returns as <code>ArrayList<Game></code>
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public ArrayList<Game> getAllGames() {
		
		ArrayList<Game> Games  = new ArrayList<Game>();
		
		String temp = new String();
		String returnstring = new String();
		Game game = new Game();
		
		 try {
	            HttpClient httpclient= new DefaultHttpClient();
	       
	            HttpGet httpget= new HttpGet ("http://pug.myrpi.org/getall.php");
	            
	            HttpResponse response = httpclient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            temp = EntityUtils.toString(entity);
	            JSONObject obj=new JSONObject(temp);
	            
	            returnstring = temp; //debugging
	            
	            for(int i=1; i<=obj.length(); i++)
	            {
	            	Integer j = i;
	            	String idstring = new String(j.toString());
	            	//get the json referred to by id i
	            	JSONObject gameJson = obj.getJSONObject(idstring);
	            	
	            	//unpack the game json into a Game
	            	game = jsonInterface.unpackGame(gameJson);
	            	
	            	//Add the game to the ArrayList
	            	Games.add(game);
	            	
	            }
	            
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		//return game.getCreator().getName();
		//return returnstring;
		return Games;
		
	}
	
	
	public int sendGame(Game game) {
		
		try{
			//pack the Game into a JSONObject
			JSONObject jsonGame = jsonInterface.packGame(game);
			
			//send the JSONObject to the server for processing using some HTTPClient call or something
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
