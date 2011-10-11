package eltoraz.pug;

/**
 * The <code>Person</code> class represents a person, not necessarily a user of the PUG service.
 * @author Bill Jameson
 * @version 0.1
 */
public class Person {
	protected String name;
	protected int id;
	
	// TODO: persistent value to keep a tally of the number of Persons created for use in assigning IDs
	
	/* ***** CONSTRUCTORS ***** */
	
	/**
	 * Create a new <code>Person</code> with default values.
	 */
	public Person() {
		name = "";
		id = -1;
	}
	
	/**
	 * Create a new <code>Person</code> object with specified name and ID number
	 * @param s <code>String</code>, the name of the <code>Person</code>
	 * @param n <code>int</code>, the person's ID number
	 */
	public Person(String s, int n) {
		name = s;
		id = n;
	}
	
	/**
	 * Create a new <code>Person</code> object as a copy of the specified one
	 * @param p <code>Person</code> object whose fields to copy in this one's creation
	 */
	public Person(Person p) {
		name = new String(p.name);
		id = p.id;
	}
	
	/* ***** SET METHODS ***** */
	
	/**
	 * Set the <code>Person</code>'s name
	 * @return a <code>String</code> containing the <code>Person</code>'s name
	 */
	public void setName(String a_name) {
		name = a_name;
	}
	
	/**
	 * Set the <code>Person</code>'s ID number
	 * @return a <code>int</code> containing the <code>Person</code>'s ID number
	 */
	public void setId(int i) {
		id = i;
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
	 * @return a <code>int</code> containing the <code>Person</code>'s ID number
	 */
	public int getId() {
		return id;
	}
}
