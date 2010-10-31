package net.homelinux.paubox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceActivity extends BaseMenuActivity {


	/************************
	 **** CLASS VARIABLE ****
	 ************************/
	TextView debug_text;
	Spinner bet_spinner;
	Spinner coinche_spinner;
	TextView current_score;
	TextView distribution;

	PowerManager.WakeLock wl;

	/**************************
	 **** PRIVATE METHODS *****
	 **************************/
	private void updateDebugText() {
		// Since we're in the same package, we can use this context to get
		// the default shared preferences
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		final String winning_score = sharedPref.getString("winning_score", "coucou");
		Deal current_deal = current_game.currentDeal();
		debug_text.setText("Current: " + current_deal.getAnnounce(this) + " (max = " + winning_score + ")");
	}

	/****************************
	 **** PROTECTED METHODDS ****
	 ****************************/
	// Call the waiting activity
	protected void launchWaitingActivity() {
		Intent waiting_intent = new Intent(this, WaitingActivity.class);
		waiting_intent.putExtra("net.homelinux.paubox.Game", current_game.currentDeal());
		startActivityForResult(waiting_intent, REQUEST_CODE_WAITING);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		// The preferences returned if the request code is what we had given
		// earlier in startSubActivity
		if (requestCode == REQUEST_CODE_PREFERENCES) {
			// Read a sample value they have set
			updateDebugText();
			updatePreferences();
		} else if (requestCode == REQUEST_CODE_WAITING) {
			if (resultCode != RESULT_CANCELED) {
				boolean won = data.getBooleanExtra("net.homelinux.paubox.won", false);			
				current_game.currentDeal().setWon(won);
				current_game.updateResult();
				current_game.newDeal();
				//button_coinche.setText(CoincheButtonTextId(current_game.currentDeal().getCoinchedMultiplicator()));
				Toast.makeText(getApplicationContext(), "The game was " + (won ? "won !" : "lost :("),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Cancel",
						Toast.LENGTH_SHORT).show();
			}
		}	

		current_score.setText("Us : " + current_game.getScore_Us() + "\nThem : " + current_game.getScore_Them());		
		distribution.setText("distribution : " + current_game.getPlayer_Distribution());
		bet_spinner.setSelection(0);
		coinche_spinner.setSelection(0);

	}

	private void updatePreferences() {
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("wake_lock_enable", false)) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "AnnounceActivity");
		} else {
			wl = null;
		}
	}

	protected int BetFromItemId(long adapter_view_id) {
		if (adapter_view_id == android.widget.AdapterView.INVALID_ROW_ID)
			return Deal.MIN_BET;
		else if (adapter_view_id > (Deal.MAX_BET - Deal.MIN_BET)/10)
			return Deal.CAPOT_BET;
		else
			return Deal.MIN_BET + (int)adapter_view_id * 10;
	}
	/*************************
	 **** PUBLIC METHODDS ****
	 *************************/

	private int coincheMultiplicatorFromItemId(int position){
		switch(position){
		case 0: return 1;
		case 1: return 2;
		case 2: return 4;
		default: return -1;
		
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (wl!=null) {
			wl.acquire();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		writeGame(current_game);
		if (wl!=null) {
			wl.release();
		}
	}




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announce_layout);
		updatePreferences();
		current_game = (Game)getIntent().getSerializableExtra("net.homelinux.paubox.Game");

		current_score = (TextView) findViewById(R.id.current_score);			
		current_score.setText("Us : " + current_game.getScore_Us() + "\nThem : " + current_game.getScore_Them());

		distribution = (TextView) findViewById(R.id.distribution);
		distribution.setText("distribution : " + current_game.getPlayer_Distribution());

		final Button button_go = (Button) findViewById(R.id.button_go);
		current_game.currentDeal().setCoinchedMultiplicator(1);

		final RadioButton radio_us = (RadioButton) findViewById(R.id.button_Us);
		final RadioButton radio_them = (RadioButton) findViewById(R.id.button_Them);
		debug_text = (TextView) findViewById(R.id.debug_text);

		button_go.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Save the current team betting
				if (radio_us.isChecked())
					current_game.currentDeal().setTeam_betting(Game.Us);
				else if (radio_them.isChecked()) {
					current_game.currentDeal().setTeam_betting(Game.Them);
				}
				else {
					Toast.makeText(getApplicationContext(), R.string.select_team , Toast.LENGTH_SHORT).show();
					return;
				}

				//Save the current bet and the multiplicator
				current_game.currentDeal().setBet(BetFromItemId(bet_spinner.getSelectedItemId()));
				current_game.currentDeal().setCoinchedMultiplicator(coincheMultiplicatorFromItemId(coinche_spinner.getSelectedItemPosition()));

				launchWaitingActivity();
			}
		});


		bet_spinner = (Spinner) findViewById(R.id.bet_spinner);
		ArrayAdapter<CharSequence> bet_adapter = ArrayAdapter.createFromResource(
				this, R.array.points, android.R.layout.simple_spinner_item);
		bet_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bet_spinner.setAdapter(bet_adapter);

		coinche_spinner = (Spinner) findViewById(R.id.coinche_spinner);
		ArrayAdapter<CharSequence> coinche_adapter = ArrayAdapter.createFromResource(
				this, R.array.coinche_array, android.R.layout.simple_spinner_item);
		coinche_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		coinche_spinner.setAdapter(coinche_adapter);

	}

	private void writeGame(Game game) {
		try {
			FileOutputStream fos = openFileOutput(NewGameActivity.FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.new_game:
			return true;
		case R.id.quit:
			return true;
		case R.id.preferences:
			return true;
		case R.id.display_scores:
			return true;
		}
		return false;
	}
}

