package eltoraz.pug.android;

import eltoraz.pug.Game.SportType;
import eltoraz.pug.Person;
import eltoraz.pug.Person.Gender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ProfileJoinedGamesActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Joined Game tab");
        setContentView(textview);
    }
}



