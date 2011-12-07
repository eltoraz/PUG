package eltoraz.pug;

import java.io.Serializable;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Person</code> class represents a user of the PickUpGames application platform.
 * @author Bill Jameson
 * @version 1.0
 */
public class Person implements Serializable {
	/**
	 * Simple Enumeration for gender.
	 * @author Bill Jameson
	 * @version 1.0
	 */
	public enum Gender {
		MALE, FEMALE;
		
		@Override
		public String toString() {
			String s = super.toString();
			return s.substring(0, 1) + s.substring(1).toLowerCase();
		}
	}
	
	/**
	 * Eclipse-generated default <code>Serializable</code> ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/* ***** PERSON PROPERTIES ***** */
	protected String name;
	protected int id;
	protected int age;
	protected Gender gender;
	protected Game.SportType favoriteSport;
	
	/* ***** CONSTRUCTORS ***** */
	
	/**
	 * Create a new <code>Person</code> with default/dummy values.
	 */
	public Person() {
		name = "Robert White";
		id = 1;
		age = 22;
		gender = Gender.MALE;
		favoriteSport = Game.SportType.BASEBALL;
	}
	
	/**
	 * Create a new <code>Person</code> object with specified name and ID number
	 * @param s <code>String</code>, the name of the <code>Person</code>
	 * @param n <code>int</code>, the person's ID number
	 * @param a <code>int</code>, the person's age
	 * @param g <code>enum Gender</code>, the person's gender
	 * @param fav <code>enum Game.SportType</code>, the person's favorite sport
	 */
	public Person(String s, int n, int a, Gender g, Game.SportType fav) {
		name = s;
		id = n;
		age = a;
		gender = g;
		favoriteSport = fav;
	}
	
	/**
	 * Create a new <code>Person</code> object from the data retrieved in the JSON object
	 * @author Brian Orecchio
	 * @param json <code>JSONObject</code> to be parsed
	 */
	public Person(JSONObject json) {
		try {
			// TODO: make some of these getOpt (e.g., don't always need age)
			id = json.getInt("id");
			name = json.getString("name");
			age = json.getInt("age");
			gender = Gender.valueOf(json.getString("gender"));
			favoriteSport = Game.SportType.valueOf(json.getString("favorite").toUpperCase());
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return a JSON object representation of this <code>Person</code>
	 * @author Brian Orecchio
	 * @return a <code>JSONObject</code> encapsulating this <code>Person</code>
	 */
	public JSONObject JSON() {
		JSONObject personJson = null;
		
		try {
			personJson = new JSONObject();
			
			personJson.put("id", id);
			personJson.put("name", name);
			personJson.put("age", age);
			personJson.put("gender", gender.toString());
			personJson.put("favorite", favoriteSport.toString());
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return personJson;
	}
	
	/**
	 * Return a JSON object with a minimalistic representation of this <code>Person</code>
	 * @return a <code>JSONObject</code> containing the <code>Person</code>'s unique ID
	 */
	public JSONObject JSONMin() {
		JSONObject personJson = null;
		
		try {
			personJson = new JSONObject();
			personJson.put("id", id);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return personJson;
	}
	
	/* ***** GET METHODS ***** */
	
	/**
	 * Get the <code>Person</code>'s name
	 * @return a <code>String</code> containing the <code>Person</code>'s name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the <code>Person</code>'s ID number
	 * @return an <code>int</code> containing the <code>Person</code>'s ID number
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the <code>Person</code>'s age
	 * @return an <code>int</code> containing the <code>Person</code>'s age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Get the <code>Person</code>'s gender
	 * @return an <code>enum Gender</code> corresponding to the <code>Person</code>'s gender
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Get the <code>Person</code>'s favorite sport
	 * @return an <code>enum Game.SportType</code> corresponding to the <code>Person</code>'s favorite sport
	 */
	public Game.SportType getFavSport() {
		return favoriteSport;
	}
}
