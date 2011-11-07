package eltoraz.pug.android;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ProfileActivity extends Activity {
	private static final String TAG= ProfileActivity.class.getSimpleName();
	
	private Person user;
	
	private EditText editTextName;
	private Button buttonEdit;
	private Button buttonSaveChanges;
	private EditText editTextAge;
	private EditText editTextGender;
	private EditText editTextFavoriteSport;
	
	private String name;
	private String age;
	private String gender;
	private String favoriteSport;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
		
		name = user.getName();
		age = String.valueOf(user.getAge());
		gender = user.getGender().toString();
		favoriteSport = user.getFavSport().toString();
		
		buttonEdit=(Button) findViewById(R.id.buttonEdit);
		buttonSaveChanges=(Button) findViewById(R.id.buttonSaveChanges);
		editTextName=(EditText) findViewById(R.id.editTextName);
		editTextAge=(EditText) findViewById(R.id.editTextAge);
		editTextGender=(EditText) findViewById(R.id.editTextGender);
		editTextFavoriteSport=(EditText) findViewById(R.id.editTextFavoriteSport);
		
		editTextName.setText("Name: " + name);
		editTextName.setFocusable(false);
		editTextName.setFocusableInTouchMode(false);
		editTextName.setClickable(false);
		
		editTextAge.setText("Age: " + age);
		editTextAge.setFocusable(false);
		editTextAge.setFocusableInTouchMode(false);
		editTextAge.setClickable(false);
		
		editTextGender.setText("Gender: " + gender);
		editTextGender.setFocusable(false);
		editTextGender.setFocusableInTouchMode(false);
		editTextGender.setClickable(false);
		
		editTextFavoriteSport.setText("Favorite Sport: " + favoriteSport);
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
				user.setName(editTextName.getText().toString());
				
				editTextAge.setFocusable(false);
				editTextAge.setFocusableInTouchMode(false);
				editTextAge.setClickable(false);
				user.setAge(Integer.parseInt(editTextAge.getText().toString()));
				
				editTextGender.setFocusable(false);
				editTextGender.setFocusableInTouchMode(false);
				editTextGender.setClickable(false);
				user.setGender(gender);
				
				editTextFavoriteSport.setFocusable(false);
				editTextFavoriteSport.setFocusableInTouchMode(false);
				editTextFavoriteSport.setClickable(false);
				user.setFavSport(favoriteSport);
				
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
