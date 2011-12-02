package eltoraz.pug.android;

import eltoraz.pug.Game;
import eltoraz.pug.Person;
import android.content.Intent;
import android.os.Bundle;

/**
 * An <code>Activity</code> for editing a Game the user created/owns
 * @author Bill Jameson
 * @version 1.0
 */
public class EditGameActivity extends ModifyGameActivity {/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
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
	protected void setReturnData() {
		Intent data = new Intent();
		data.putExtra("old_game", oldGame);
		data.putExtra("game", game);
		setResult(RESULT_OK, data);		
	}
}
