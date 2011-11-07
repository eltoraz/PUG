package eltoraz.pug.android;

import eltoraz.pug.Game;
import java.util.*;
import eltoraz.pug.Location;
import eltoraz.pug.Person;
import eltoraz.pug.Game.SportType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.GregorianCalendar;
public class ProfileActivity extends Activity{
	private static final String TAG= ProfileActivity.class.getSimpleName();
	private Person user;
	private EditText editTextName;
	private Button buttonEdit;
	private Button buttonSaveChanges;
	private EditText editTextAge;
	private EditText editTextGender;
	private EditText editTextFavoriteSport;
	private String Name="Robert White";
	private String Age="22";
	private String Gender="Male";
	private String FavoriteSport="Basketball";
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
		
		
		buttonEdit=(Button) findViewById(R.id.buttonEdit);
		buttonSaveChanges=(Button) findViewById(R.id.buttonSaveChanges);
		editTextName=(EditText) findViewById(R.id.editTextName);
		editTextAge=(EditText) findViewById(R.id.editTextAge);
		editTextGender=(EditText) findViewById(R.id.editTextGender);
		editTextFavoriteSport=(EditText) findViewById(R.id.editTextFavoriteSport);
		
		editTextName.setText("Name: " + Name);
		editTextName.setFocusable(false);
		editTextName.setFocusableInTouchMode(false);
		editTextName.setClickable(false);
		
		editTextAge.setText("Age: " + Age);
		editTextAge.setFocusable(false);
		editTextAge.setFocusableInTouchMode(false);
		editTextAge.setClickable(false);
		
		editTextGender.setText("Gender: " + Gender);
		editTextGender.setFocusable(false);
		editTextGender.setFocusableInTouchMode(false);
		editTextGender.setClickable(false);
		
		editTextFavoriteSport.setText("Favorite Sport: " + FavoriteSport);
		editTextFavoriteSport.setFocusable(false);
		editTextFavoriteSport.setFocusableInTouchMode(false);
		editTextFavoriteSport.setClickable(false);
		
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextName.setClickable(true);
				editTextName.setFocusable(true);
				editTextName.setFocusableInTouchMode(true);
				
				editTextAge.setFocusable(true);
				editTextAge.setFocusableInTouchMode(true);
				editTextAge.setClickable(true);
				
				editTextGender.setFocusable(true);
				editTextGender.setFocusableInTouchMode(true);
				editTextGender.setClickable(true);
				
				editTextFavoriteSport.setFocusable(true);
				editTextFavoriteSport.setFocusableInTouchMode(true);
				editTextFavoriteSport.setClickable(true);
				
				Context context = getApplicationContext();
				CharSequence text = "Edit Mode!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			
			}
		});
		
		buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextName.setFocusable(false);
				editTextName.setFocusableInTouchMode(false);
				editTextName.setClickable(false);
				
				editTextAge.setFocusable(false);
				editTextAge.setFocusableInTouchMode(false);
				editTextAge.setClickable(false);
				
				editTextGender.setFocusable(false);
				editTextGender.setFocusableInTouchMode(false);
				editTextGender.setClickable(false);
				
				editTextFavoriteSport.setFocusable(false);
				editTextFavoriteSport.setFocusableInTouchMode(false);
				editTextFavoriteSport.setClickable(false);
				
				Context context = getApplicationContext();
				CharSequence text = "Saved Changes!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
			}
		});
		
		/*Log.d(TAG, "1");
		GregorianCalendar A= new GregorianCalendar();
		Log.d(TAG, "2");
		Location l= new Location(74,76);
		Log.d(TAG, "3");
		Game X= Game.buildGame(Game.SportType.BASKETBALL,"Looking for a solid pickup game with a large crowd",A.getTimeInMillis(),user,user,l,20,false);
		Log.d(TAG, "4");
		if (X==null)
			Log.d(TAG,"NULL Games");
		PugNetworkInterface.sendGame(X);
		Log.d(TAG, "5");
		ArrayList<Game> ListOfGames=PugNetworkInterface.getGames();
		Log.d(TAG, "6");
		*/

		

	}
	
}


