package eltoraz.pug.android;

import eltoraz.pug.*;

//import java.lang.reflect.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.lang.String;
//import java.lang.String;

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

public class DisplayGameService {

	public Vector<Game> displayDefaultGames()
	{
		Vector<Game> Games  = new Vector<Game>();
		
		String temp = new String();
		String returnstring = new String();
		Game game = new Game();
		
		 try {
	            HttpClient httpclient= new DefaultHttpClient();
	       
	            HttpGet httpget= new HttpGet ("http://pug.myrpi.org/getall.php");
	            
	            HttpResponse response = httpclient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            temp = EntityUtils.toString(response.getEntity());
	            JSONObject obj=new JSONObject(temp);
	            
	           
	            for(int i=1; i<=obj.length(); i++)
	            {
	            	Integer j = i;
	            	String idstring = new String(j.toString());
	            	//get the json j=obj refered to by id i.  This will want to be changed in future
	            	JSONObject tempobj = obj.getJSONObject(idstring);
	            	//String gametype = new String( tempobj.get("sport").toString() );      //get the game type if needed
	            	//String description = new String( tempobj.get("descr").toString() );   //get the game description if needed
	            	
	            	//Get the creator info from the JSON
	            	JSONObject creatorOBJ = tempobj.getJSONObject("creator");
	            	Integer id = creatorOBJ.getInt("id");
	            	String person_name = creatorOBJ.getString("first");
	            	person_name = person_name + " " +  creatorOBJ.getString("last");
	            	Person p = new Person( person_name, id);
	            	
	            	//Get the location info from the JSON
	            	JSONObject locationOBJ = tempobj.getJSONObject("location");
	            	Integer locId = locationOBJ.getInt("id");
	            	Integer lat = locationOBJ.getInt("lat");
	            	Integer lon = locationOBJ.getInt("lon");
	            	//String location_name = locationOBJ.getString("name");
	            	Location loc = new Location(lat,lon);
	            	
	            	GregorianCalendar cal = new GregorianCalendar();
	            	
	            	game = new Game(loc,cal,p);
	            	
	            	Games.add(game);
	            	returnstring = temp;
	            }
    
	            
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
		
		//return game.getCreator().getName();
		//return returnstring;
		return Games;
		
	}
	
	
	
}
