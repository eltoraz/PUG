package eltoraz.pug.android;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ProfileActivity extends Activity{
	private Person user;
	private TextView textViewName;
	private TextView textViewAge;
	private TextView textViewGender;
	private TextView textViewFavoriteSport;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		textViewName = (TextView) findViewById(R.id.textViewName);
		textViewAge = (TextView) findViewById(R.id.textViewAge);
		textViewGender = (TextView) findViewById(R.id.textViewGender);
		textViewFavoriteSport = (TextView) findViewById(R.id.textViewFavoriteSport);
		
		textViewName.setText("Name: Robert White");
		textViewAge.setText("Age: 22");
		textViewGender.setText("Gender: Male");
		textViewFavoriteSport.setText("Favorite Sport: Curling");
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
	}
	
}


