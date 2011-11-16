package eltoraz.pug;

import java.io.Serializable;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Person</code> class represents a person, not necessarily a user of the PUG service.
 * @author Bill Jameson
 * @version 0.1
 */
public class Person implements Serializable {
	public enum Gender {
		MALE, FEMALE;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int id;
	protected int age;
	protected Gender gender;
	protected Game.SportType favoriteSport;
	
	// TODO: persistent value to keep a tally of the number of Persons created for use in assigning IDs
	
	/* ***** CONSTRUCTORS ***** */
	
	/**
	 * Create a new <code>Person</code> with default values.
	 */
	public Person() {
		name = "";
		id = -1;
		age = -1;
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
	 * Create a new <code>Person</code> object as a copy of the specified one
	 * @param p <code>Person</code> object whose fields to copy in this one's creation
	 */
	public Person(Person p) {
		name = new String(p.name);
		id = p.id;
		age = p.age;
		gender = p.gender;
		favoriteSport = p.favoriteSport;
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
	
	/* ***** SET METHODS ***** */
	
	/**
	 * Set the <code>Person</code>'s name
	 * @param a_name <code>String</code> containing the <code>Person</code>'s name
	 */
	public void setName(String a_name) {
		name = a_name;
	}
	
	/**
	 * Set the <code>Person</code>'s ID number
	 * @param <code>int</code> containing the <code>Person</code>'s ID number
	 */
	public void setId(int i) {
		id = i;
	}
	
	/**
	 * Set the <code>Person</code>'s age
	 * @param i <code>int</code> containing the <code>Person</code>'s age
	 */
	public void setAge(int i) {
		age = i;
	}
	
	/**
	 * Set the <code>Person</code>'s gender
	 * @param g <code>enum Gender</code> corresponding to the <code>Person</code>'s gender
	 */
	public void setGender(Gender g) {
		gender = g;
	}
	
	/**
	 * Set the <code>Person</code>'s gender
	 * @param s <code>String</code> containing the <code>Person</code>'s gender
	 */
	public void setGender(String s) {
		gender = Gender.valueOf(s.toUpperCase());
	}
	
	/**
	 * Set the <code>Person</code>'s favorite sport
	 * @param s <code>enum Game.SportType</code> corresponding to the <code>Person</code>'s favorite sport
	 */
	public void setFavSport(Game.SportType s) {
		favoriteSport = s;
	}
	
	/**
	 * Set the <code>Person</code>'s favorite sport
	 * @param s <code>String</code> containing to the <code>Person</code>'s favorite sport
	 */
	public void setFavSport(String s) {
		favoriteSport = Game.SportType.valueOf(s.toUpperCase());
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
