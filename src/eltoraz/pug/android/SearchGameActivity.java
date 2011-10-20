package eltoraz.pug.android;

import eltoraz.pug.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.*;
public class SearchGameActivity extends Activity {
	private Person user;
	private Button searchSubmitButton;
	private EditText LongitudeText;
	private EditText LatitudeText;
	private TextView LatitudeView;
	private TextView LongitudeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchgame);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Test User", -1);
	
		searchSubmitButton = (Button) findViewById(R.id.searchSubmitButton);
		searchSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SearchGameActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
	}
	
	public CharSequence get_lat() {
		return LatitudeView.getText();
	}
}
