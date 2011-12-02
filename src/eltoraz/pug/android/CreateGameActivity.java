package eltoraz.pug.android;

import eltoraz.pug.*;

import android.os.Bundle;

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
	protected void setReturnData() {
	}
}
