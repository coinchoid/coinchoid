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
	public static final int REQUEST_CODE_EDIT = RESULT_FIRST_USER + 3;

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
		startActivityForResult(myIntent, REQUEST_CODE_EDIT);
	}

	protected void launchNewGameActivity() {
		Intent myIntent = new Intent(this,NewGameActivity.class);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		myIntent.setAction(Intent.ACTION_DELETE);
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
			Toast.makeText(getApplicationContext(), "Lancement d'une nouvelle partie",
			               Toast.LENGTH_SHORT).show();
			launchNewGameActivity();
			return true;
		case R.id.quit:
			this.finish();
			return true;
		case R.id.preferences:
			launchPreferencesActivity();
			return true;
		case R.id.display_scores:
			if (current_game == null)
				Toast.makeText(getApplicationContext(), "Not available now",
					Toast.LENGTH_SHORT).show();
			else
				launchDisplayActivity();
			return true;
		}
		return false;
	}
}
