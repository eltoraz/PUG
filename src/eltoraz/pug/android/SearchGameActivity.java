package eltoraz.pug.android;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class SearchGameActivity extends Activity {
	private Person user;
	
	private EditText locationEditText;
	private Button searchSubmitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchgame);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Robert White", 1, 22, Person.Gender.MALE, Game.SportType.BASEBALL);
	
		searchSubmitButton = (Button) findViewById(R.id.searchSubmitButton);
		searchSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SearchGameActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
	}
}
