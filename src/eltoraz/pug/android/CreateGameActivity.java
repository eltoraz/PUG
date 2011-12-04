package eltoraz.pug.android;

import java.util.Calendar;

import eltoraz.pug.*;

import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * An <code>Activity</code> for game creation based on user specifications
 * @author Bill Jameson
 * @version 1.0
 */
public class CreateGameActivity extends ModifyGameActivity {
	@Override
	protected void getIntentData() {
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else {
			// This else block is here to satisfy the compiler, since the intent will always have the user
			user = new Person();
		}
	}
	
	@Override
	protected void fillFields(ArrayAdapter<CharSequence> adapter) {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		
		maxPlayersEditText.setText("4");
	}

	@Override
	protected void setReturnData() {
	}
}
