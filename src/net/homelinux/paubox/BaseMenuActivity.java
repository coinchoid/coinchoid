package net.homelinux.paubox;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseMenuActivity extends Activity {

	/*******************
	 **** CONSTANTS ****
	 *******************/

	public static final int REQUEST_CODE_PREFERENCES = RESULT_FIRST_USER + 1;
	public static final int REQUEST_CODE_WAITING = RESULT_FIRST_USER + 2;

	Game current_game;

	// Call the preferences activity
	protected void launchPreferencesActivity() {
		Intent launchPreferencesIntent = new Intent(this, PreferencesActivity.class);
		// Make it a subactivity so we know when it returns
		startActivityForResult(launchPreferencesIntent, REQUEST_CODE_PREFERENCES);
	}
	protected void launchDisplayActivity() {
		Intent myIntent = new Intent(this,ScoreDisplayActivity.class);
		myIntent.putExtra("net.homelinux.paubox.Game",current_game);
		startActivity(myIntent);
	}

	/**
	 * Invoked during initialization to give the Activity a chance to set up its Menu.
	 *
	 * @param menu the Menu to which entries may be added
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.common_menu, menu);
		return true;
	}

	/**
	 * Invoked when the user selects an item from the Menu.
	 *
	 * @param item the Menu entry which was selected
	 * @return true if the Menu item was legitimate (and we consumed it), false
	 *         otherwise
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_game:
			current_game = new Game();
			Toast.makeText(getApplicationContext(), "Starting new game",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.quit:
			Toast.makeText(getApplicationContext(), "Exit",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.preferences:
			launchPreferencesActivity();
			return true;
		case R.id.display_scores:
			launchDisplayActivity();
			return true;
		}
		return false;
	}
}
