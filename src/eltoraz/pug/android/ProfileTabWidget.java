package eltoraz.pug.android;

import java.util.ArrayList;

import eltoraz.pug.Game;
import eltoraz.pug.Person;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.*;

/**
 * This <code>Activity</code> provides a tabbed interface for the profile
 * screen.
 * 
 * @author Brian Orecchio
 * @version 1.0
 */
public class ProfileTabWidget extends TabActivity {
	protected Person user;

	/**
	 * The <code>onCreate</code> method is called when this <code>Activity</code> is first
	 *  created. It captures the UI elements and sets default functionality.
	 * @param savedInstanceState <code>Bundle</code>
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilemain);

		Resources res = getResources();		// Resource object to get Drawables
		TabHost tabHost = getTabHost();		// The activity TabHost
		TabHost.TabSpec spec;				// Resusable TabSpec for each tab
		Intent intent;						// Reusable Intent for each tab

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			user = (Person) extras.get("user");
		else
			// This else block is here to satisfy the compiler, since the intent
			// will always have the user
			user = new Person();

		// Create an Intent to launch the regular profile activity
		intent = new Intent().setClass(this, ProfileActivity.class);
		intent.putExtra("user", user);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("User Data")
				.setIndicator("User Data", res.getDrawable(R.drawable.profile1))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the joined games tap
		intent = new Intent().setClass(this, ProfileJoinedGamesActivity.class);
		ArrayList<Game> games = PugNetworkInterface.getJoinedGames(user.getId());
		intent.putExtra("user", user);
		intent.putExtra("games", games);
		spec = tabHost.newTabSpec("Games Joined")
				.setIndicator("Games Joined",
						res.getDrawable(R.drawable.basketball))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(2);

		Intent data = new Intent();
		data.putExtra("user", user);
		setResult(RESULT_OK, data);
	}
}
