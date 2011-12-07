package eltoraz.pug.android;

import eltoraz.pug.Game.SportType;
import eltoraz.pug.Person;
import eltoraz.pug.Person.Gender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// Logger imports
//import android.util.Log;

/**
 * This <code>Activity</code> allows the user to view and modify his/her
 *  profile information.
 * @author Kevin Frame
 * @version 1.0
 */
public class ProfileActivity extends Activity {
	private Person user;
	
	/* ***** UI ELEMENTS ***** */
	// TODO: Make gender and favorite sport spinners
	private EditText nameEditText;
	private EditText ageEditText;
	private Spinner genderSpinner;
	private Spinner favoriteSportSpinner;
	private Button editButton;
	private Button saveButton;
	
	private String name;
	private String age;
	
	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else {
			// This else block is here to satisfy the compiler, since the intent will always have the user
			user = new Person();
		}
		
		name = user.getName();
		age = String.valueOf(user.getAge());
		
		/* ***** CAPTURE UI ELEMENTS ***** */
		nameEditText = (EditText) findViewById(R.id.editTextName);
		ageEditText = (EditText) findViewById(R.id.editTextAge);
		genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
		favoriteSportSpinner = (Spinner) findViewById(R.id.favoriteSportSpinner);
		editButton = (Button) findViewById(R.id.buttonEdit);
		saveButton = (Button) findViewById(R.id.buttonSaveChanges);
		
		nameEditText.setText(name);
		nameEditText.setFocusable(false);
		nameEditText.setFocusableInTouchMode(false);
		nameEditText.setClickable(false);
		
		ageEditText.setText(age);
		ageEditText.setFocusable(false);
		ageEditText.setFocusableInTouchMode(false);
		ageEditText.setClickable(false);
		
		ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array,
				 android.R.layout.simple_spinner_item);
		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genderSpinner.setAdapter(genderAdapter);
		genderSpinner.setSelection(genderAdapter.getPosition(user.getGender().toString()));
		genderSpinner.setFocusable(false);
		genderSpinner.setFocusableInTouchMode(false);
		genderSpinner.setClickable(false);
		
		ArrayAdapter<CharSequence> favoriteSportAdapter = ArrayAdapter.createFromResource(this, R.array.sports_array,
				 android.R.layout.simple_spinner_item);
		favoriteSportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		favoriteSportSpinner.setAdapter(favoriteSportAdapter);
		favoriteSportSpinner.setSelection(favoriteSportAdapter.getPosition(user.getFavSport().toString()));
		favoriteSportSpinner.setFocusable(false);
		favoriteSportSpinner.setFocusableInTouchMode(false);
		favoriteSportSpinner.setClickable(false);
		
		editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nameEditText.setClickable(true);
				nameEditText.setFocusable(true);
				nameEditText.setFocusableInTouchMode(true);
				
				ageEditText.setFocusable(true);
				ageEditText.setFocusableInTouchMode(true);
				ageEditText.setClickable(true);
				
				genderSpinner.setFocusable(true);
				genderSpinner.setFocusableInTouchMode(true);
				genderSpinner.setClickable(true);
				
				favoriteSportSpinner.setFocusable(true);
				favoriteSportSpinner.setFocusableInTouchMode(true);
				favoriteSportSpinner.setClickable(true);
				
				/* For debugging.
				Context context = getApplicationContext();
				CharSequence text = "Edit Mode!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				*/
			}
		});
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = user.getId();
				
				nameEditText.setFocusable(false);
				nameEditText.setFocusableInTouchMode(false);
				nameEditText.setClickable(false);
				name = nameEditText.getText().toString();
				
				ageEditText.setFocusable(false);
				ageEditText.setFocusableInTouchMode(false);
				ageEditText.setClickable(false);
				age = ageEditText.getText().toString();
				
				genderSpinner.setFocusable(false);
				genderSpinner.setFocusableInTouchMode(false);
				genderSpinner.setClickable(false);
				Gender gender = Gender.valueOf(genderSpinner.getSelectedItem().toString().toUpperCase());
				
				favoriteSportSpinner.setFocusable(false);
				favoriteSportSpinner.setFocusableInTouchMode(false);
				favoriteSportSpinner.setClickable(false);
				SportType fav = SportType.valueOf(favoriteSportSpinner.getSelectedItem().toString().toUpperCase());
				
				user = new Person(name, id, Integer.parseInt(age), gender, fav);
				
				PugNetworkInterface.editUser(user);
				
				Context context = getApplicationContext();
				CharSequence text = "Saved Changes!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
				Intent data = new Intent();
				data.putExtra("user", user);
				setResult(RESULT_OK, data);
				
				finish();
			}
		});
	}	
}
