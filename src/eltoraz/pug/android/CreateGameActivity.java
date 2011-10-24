package eltoraz.pug.android;

import eltoraz.pug.*;

//import java.lang.reflect.*;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * An <code>Activity</code> for game creation based on user specifications
 * @author Bill Jameson
 * @version 0.1
 */
public class CreateGameActivity extends Activity {
	private Person user;
	private Game game;
	
	// UI ELEMENTS
	private Button createGameButton;
	private Spinner sportSelectSpinner;
	private EditText locationEditText;
	private Button findLocationButton;
	private Button datePickButton;
	private Button timePickButton;
	private EditText maxPlayersEditText;
	private ToggleButton visibilityToggleButton;
	
	private int mHour, mMinute;
	private int mYear, mMonth, mDay;
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	
	// callback received when user sets the date in the dialog
	private DatePickerDialog.OnDateSetListener dateSetListener =
			new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
									  int monthOfYear, int dayOfMonth) {
					mYear = year;
					mMonth = monthOfYear;
					mDay = dayOfMonth;
					updateDate();
				}
			};
			
	// callback received when user sets the time in the dialog 
	private TimePickerDialog.OnTimeSetListener timeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					mHour = hourOfDay;
					mMinute = minute;
					updateTime();
				}
			};
	
	public class CreateGameOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			// avoid runtime exceptions by making sure to reference only classes that exist and have a constructor
			//   taking a Person as an argument
			try {
				// use reflection to create the right type of game
				// Note: all the options in this spinner must have a corresponding class that extends Game
				// Adapted from sample code at http://www.rgagnon.com/javadetails/java-0351.html
				String gameType = "eltoraz.pug." + parent.getItemAtPosition(pos).toString() + "Game";
				Class<?> cl = Class.forName(gameType);
				java.lang.reflect.Constructor<?> constructor = cl.getConstructor(new Class[] {Person.class});
				Object newGameType = constructor.newInstance(new Object[] {user});
				game = (Game) newGameType;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void onNothingSelected(AdapterView<?> parent) {
			// do nothing
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creategame);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else					// This should theoretically never be called
			user = new Person("Test User", -1);
		
		/* ***** CAPTURE VIEW ELEMENTS ***** */
		createGameButton = (Button) findViewById(R.id.createButton);
		sportSelectSpinner = (Spinner) findViewById(R.id.sportSelectSpinner);
		locationEditText = (EditText) findViewById(R.id.locationEditText);
		findLocationButton = (Button) findViewById(R.id.findLocationButton);
		datePickButton = (Button) findViewById(R.id.datePickButton);
		timePickButton = (Button) findViewById(R.id.timePickButton);
		maxPlayersEditText = (EditText) findViewById(R.id.maxPlayersEditText);
		visibilityToggleButton = (ToggleButton) findViewById(R.id.visibilityToggleButton);
		
		/* ***** DEFINE	UI ELEMENT FUNCTIONALITY ***** */
		
		// GAME SELECTION SPINNER
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sports_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sportSelectSpinner.setAdapter(adapter);
		//sportSelectSpinner.setOnItemSelectedListener(new CreateGameOnItemSelectedListener());
		
		// LOCATION TEXT FIELD
		
		// FIND LOCATION BUTTON
		
		// DATE PICKER BUTTON
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDate();
		datePickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		// TIME PICKER BUTTON
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTime();
		timePickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		// MAX PLAYERS TEXT FIELD
		
		// VISIBILITY TOGGLE
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeSetListener, mHour, mMinute, false);
		}
		return null;
	}
	
	private void updateDate() {
		CharSequence text = new StringBuilder().append(mMonth+1).append("/")
											   .append(mDay).append("/")
											   .append(mYear);
		datePickButton.setText(text);
	}
	
	// time displayed in 24-hour format, maybe have an option for AM/PM display later
	private void updateTime() {
		CharSequence text = new StringBuilder().append(pad(mHour)).append(":")
											   .append(mMinute);
		timePickButton.setText(text);
	}
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}
