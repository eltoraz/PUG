package eltoraz.pug.android;

import java.util.Calendar;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * An <code>Activity</code> for editing a Game the user created/owns
 * @author Bill Jameson
 * @version 1.0
 */
public class EditGameActivity extends ModifyGameActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		successMsg = "Game modified!";
		submitButton.setText(R.string.edit);
	}

	@Override
	protected void getIntentData() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			user = (Person) extras.get("user");
			game = (Game) extras.get("game");
			oldGame = game;
		}
		else {
			// This else block is here to satisfy the compiler, since the intent will always have the user
			user = new Person();
			game = null;
		}
	}
	
	@Override
	protected void fillFields(ArrayAdapter<CharSequence> adapter) {
		if (game != null) {
			sportSelectSpinner.setSelection(adapter.getPosition(game.getGameType().toString()));
			locationEditText.setText(game.getLocation().getAddress());
			
			final Calendar c = game.getDate();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			
			maxPlayersEditText.setText(Integer.toString(game.getMaxPlayers()));
			visibilityToggleButton.setChecked(game.getPrivate());
			descriptionEditText.setText(game.getDescription());
		}
	}

	@Override
	protected void setReturnData() {
		Intent data = new Intent();
		data.putExtra("old_game", oldGame);
		data.putExtra("game", game);
		setResult(RESULT_OK, data);		
	}
}
