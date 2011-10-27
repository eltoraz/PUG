package eltoraz.pug.android;

import java.util.GregorianCalendar;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import eltoraz.pug.*;


/**
 * The class <code>JsonObjectInterface</code> provides functions to make various objects into JSONObjects and to make JSONObjects into their respective objects
 * @author Brian Orecchio
 * @version 0.1
 */
public class JsonObjectInterface {

	
	/**
	 * The <code>unpackGame</code> takes as a parameter a JSONObject of a game, parses it into a <code>Game</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public Game unpackGame(JSONObject json) {
		try{
			//String gametype = new String( json.get("sport").toString() );      //get the game type if needed
	    	//String description = new String( json.get("descr").toString() );   //get the game description if needed
	    	
	    	//Get the creator JSON from the JSON
	    	JSONObject creatorJSON = json.getJSONObject("creator");
	    	//unpack the creator json into a Person
	    	Person p = unpackPerson(creatorJSON);
	    	
	    	//Get the location info from the JSON
	    	JSONObject locationJSON = json.getJSONObject("location");
	    	//unpack the creator json into a Location
	    	Location loc = unpackLocation(locationJSON);
	    	
	    	GregorianCalendar cal = new GregorianCalendar();
	    	Game game = new Game(loc,cal,p);
	    	
	    	return game;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * The <code>unpackPerson</code> takes as a parameter a JSONObject of a game, parses it into a <code>Person</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public Person unpackPerson(JSONObject json) {
		
		try{
			Integer id = json.getInt("id");
	    	String person_name = json.getString("first");
	    	person_name = person_name + " " +  json.getString("last");
			Person person = new Person( person_name, id);
			
			return person;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * The <code>unpackLocation</code> takes as a parameter a <code>JSONObject</code> of a game, parses it into a <code>Location</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public Location unpackLocation(JSONObject json) {
		
		try{
			//Integer locId = json.getInt("id");
        	Integer lat = json.getInt("lat");
        	Integer lon = json.getInt("lon");
        	//String location_name = locationOBJ.getString("name");
        	Location loc = new Location(lat,lon);
        	
        	loc.setAddr("Unknown Location");
			
			return loc;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * The <code>packGame</code> takes as a parameter a JSONObject of a game, parses it into a <code>Game</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public JSONObject packGame(Game game) {
		
		try{
			JSONObject jsonGame = new JSONObject();
			
			jsonGame.put("sport", "Soccer");  //How is this implementation going to work
			jsonGame.put("descr", "WTFFFFF"); 
			long time = System.currentTimeMillis();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
			jsonGame.put("datetime", timestamp.toString());  //currently the datetime stuff is screwing up, DB not getting the game
			
			jsonGame.put("creator", packPerson(game.getCreator()) );
			
			jsonGame.put("location", packLocation(game.getLocation()) );
			
			return jsonGame;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * The <code>packPerson</code> takes as a parameter a <code>Person</code> and makes it into a <code>JSONObject</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public JSONObject packPerson(Person person) {
		try{
			JSONObject jsonPerson = new JSONObject();
			//put the person id into the json
			//jsonPerson.put("id", person.getId() );
			
			//split the name into first and last components and put into json
			String name = person.getName();
			String firstlast[] = name.split(" ");  //is this doing what I think??
			jsonPerson.put("first", firstlast[0] );
			jsonPerson.put("last", firstlast[1] );
			
			return jsonPerson;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * The <code>packLocation</code> takes as a parameter a <code>Location</code> amd makes it into a <code>JSONObject</code> and returns it
	 * @author Brian Orecchio
	 * @version 0.1
	 */
	public JSONObject packLocation(Location loc) {
		try{
			JSONObject jsonLocation = new JSONObject();
			
			//jsonLocation.put("id", -1);  //currently the Location class does not have an id, should it?
			
			jsonLocation.put("name", "Location");  //currently the Location class does not have a name, should it?
			
			//out location class has an address component but our DB doesn't.  We should add this
			
			jsonLocation.put("lat", loc.getLat() );
			jsonLocation.put("lon", loc.getLon() );
			
			return jsonLocation;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
