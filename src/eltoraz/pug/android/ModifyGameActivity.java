package eltoraz.pug.android;

import java.util.GregorianCalendar;

import eltoraz.pug.Game;
import eltoraz.pug.Location;
import eltoraz.pug.Person;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

/**
 * Abstract class to serve as the base for any classes that involve modifying
 *  a game.
 * Uses the Template design pattern outlined in the Gang of Four book.
 * @author Bill Jameson
 * @version 1.0
 */
public abstract class ModifyGameActivity extends Activity {
	protected Person user;
	protected Game game;
	protected Game oldGame;
	
	/* ***** UI ELEMENTS ***** */
	protected Button submitButton;
	protected Spinner sportSelectSpinner;
	protected EditText locationEditText;
	protected Button datePickButton;
	protected Button timePickButton;
	protected EditText maxPlayersEditText;
	protected ToggleButton visibilityToggleButton;
	protected EditText descriptionEditText;
	
	protected int mHour, mMinute;
	protected int mYear, mMonth, mDay;
	
	protected CharSequence successMsg = "Game created!";
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	
	// callback received when user sets the date in the dialog
	protected DatePickerDialog.OnDateSetListener dateSetListener =
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
	protected TimePickerDialog.OnTimeSetListener timeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					mHour = hourOfDay;
					mMinute = minute;
					updateTime();
				}
			};
			

	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creategame);
		
		game = null;
		oldGame = null;
		
		getIntentData();
		
		/* ***** CAPTURE VIEW ELEMENTS ***** */
		submitButton = (Button) findViewById(R.id.createButton);
		sportSelectSpinner = (Spinner) findViewById(R.id.sportSelectSpinner);
		locationEditText = (EditText) findViewById(R.id.locationEditText);
		datePickButton = (Button) findViewById(R.id.datePickButton);
		timePickButton = (Button) findViewById(R.id.timePickButton);
		maxPlayersEditText = (EditText) findViewById(R.id.maxPlayersEditText);
		visibilityToggleButton = (ToggleButton) findViewById(R.id.visibilityToggleButton);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		
		/* ***** DEFINE	UI ELEMENT FUNCTIONALITY ***** */
		
		// GAME SELECTION SPINNER
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sports_array,
																			 android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sportSelectSpinner.setAdapter(adapter);
		
		// LOCATION TEXT FIELD
		// TODO: input verification
		locationEditText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
					(keyCode == KeyEvent.KEYCODE_ENTER)) {
					return true;
				}
				return false;
			}
		});
		
		// DATE PICKER BUTTON
		datePickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		// TIME PICKER BUTTON
		timePickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		fillFields(adapter);
		updateDate();
		updateTime();
		
		// CREATE GAME BUTTON
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get all the parameters to create a new game
				Game.SportType sport = Game.SportType.valueOf(sportSelectSpinner.getSelectedItem().toString().toUpperCase());
				int maxPlayers = Integer.parseInt(maxPlayersEditText.getText().toString());
				boolean privacy = visibilityToggleButton.isChecked();
				String addr = locationEditText.getText().toString();
				Location loc = PugNetworkInterface.geocode(getApplicationContext(), addr);
				GregorianCalendar dt = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
				long dtMillis = dt.getTimeInMillis();
				String descr = descriptionEditText.getText().toString();
				int id = -1;
				
				if (game != null)
					id = game.getId();
				Person originalCreator = user;
				if (game != null)
					originalCreator = game.getCreator();
				if (loc == null)
					return;
				
				// TODO: (Optional) Display a progress spinner while sending data, until a reply is received from server
				// Build a game and send it to the server.
				game = Game.buildGame(sport, descr, dtMillis, originalCreator, user, loc, maxPlayers, privacy, id);
				PugNetworkInterface.sendGame(game);
				
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, successMsg, duration);
				toast.show();
				
				setReturnData();
				
				finish();
			}
		});
	}

	/**
	 * Dialog creation callback. The <code>int</code> passed in specifies which dialog to display.
	 * @param id <code>int</code> The unique identifier of the dialog to be drawn.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DATE_DIALOG_ID:
			dialog = new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
			break;
		case TIME_DIALOG_ID:
			dialog = new TimePickerDialog(this, timeSetListener, mHour, mMinute, false);
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
	/**
	 * Update the date shown on the button with the date selected by the user.
	 */
	private void updateDate() {
		CharSequence text = new StringBuilder().append(pad(mMonth+1)).append("/")
											   .append(pad(mDay)).append("/")
											   .append(mYear);
		datePickButton.setText(text);
	}
	
	/**
	 * Update the time shown on the button with the time selected by the user.
	 * Time is displayed in 24-hour format.
	 */
	private void updateTime() {
		// TODO: (Optional) Have an option for AM/PM display later.
		CharSequence text = new StringBuilder().append(pad(mHour)).append(":")
											   .append(pad(mMinute));
		timePickButton.setText(text);
	}
	
	/**
	 * Pad the <code>int</code> with a 0 for consistent display of numerals in
	 *  the time.
	 * @param c <code>int</code> Hour or minute value to pad.
	 * @return <code>String</code> The padded hour or minute.
	 */
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/**
	 * Get the required data from the intent that started this <code>Activity</code>.
	 */
	protected abstract void getIntentData();
	/**
	 * Fill in the Game's fields as needed.
	 * @param adapter <code>ArrayAdapter</code> corresponding to the sport selection Spinner
	 */
	protected abstract void fillFields(ArrayAdapter<CharSequence> adapter);
	/**
	 * If necessary, create an intent and store any return values the calling
	 *  <code>Activity</code> is expecting.
	 */
	protected abstract void setReturnData();
}
