package eltoraz.pug;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * The <code>Team</code> class represents a team of users, organized to play a specific sport
 * @author Bill Jameson
 * @version 0.1
 */
public class Team implements Serializable {
	/**
	 * Default <code>Serializable</code ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected Game.SportType sport;
	protected ArrayList<Person> members;
	protected ArrayList<Game> games;
	
	/* ***** CONSTRUCTORS ***** */

	/**
	 * Default constructor, initializes the lists
	 */
	public Team() {
		members = new ArrayList<Person>();
		games = new ArrayList<Game>();
	}
	
	/**
	 * Create a new <code>Team</code> object with the name/sport/members/games specified in the JSON object
	 * @param json <code>JSONObject</code> to be parsed
	 */
	public Team(JSONObject json) {
		// TODO: implement
	}
	
	/**
	 * Return a JSON representation of this <code>Team</code>
	 * @return a <code>JSONObject</code> encapsulating the <code>Team</code>
	 */
	public JSONObject JSON() {
		JSONObject teamJson = null;
		
		try {
			teamJson = new JSONObject();
			
			teamJson.put("name", name);
			teamJson.put("sport", sport.toString());
			
			teamJson.put("size", members.size());
			for (Person p : members)
				teamJson.put("member"+members.indexOf(p), p.JSONMin());
			
			// TODO: unique identifier for games? if implemented, change to g.JSONMin()
			teamJson.put("gamecount", games.size());
			for (Game g : games)
				teamJson.put("game"+games.indexOf(g), g.JSON());
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return teamJson;
	}
}
