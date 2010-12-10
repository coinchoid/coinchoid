package net.homelinux.paubox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceActivity extends BaseMenuActivity {


	/************************
	 **** CLASS VARIABLE ****
	 ************************/
	LinearLayout scoreDisplayView;
	TextView distribution;

	PowerManager.WakeLock wl;

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
		switch (requestCode) {
		case REQUEST_CODE_PREFERENCES:
			// Read a sample value they have set
			updatePreferences();
			break;
		case REQUEST_CODE_WAITING:
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
			break;
		case REQUEST_CODE_EDIT:
			if (data!=null) {
				final Game g = (Game) data.getSerializableExtra("net.homelinux.paubox.edit");
				current_game.setAs(g);
				current_game.recomputeScores();
			}
		}	

		distribution.setText("distribution : " + current_game.getPlayer_Distribution() + "\n");
	}

	private void updatePreferences() {
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("wake_lock_enable", false)) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "AnnounceActivity");
		} else {
			wl = null;
		}
	}

	static protected int BetFromItemId(int adapter_view_id) {
		if (adapter_view_id == android.widget.AdapterView.INVALID_ROW_ID)
			return Deal.MIN_BET;
		else if (adapter_view_id > (Deal.MAX_BET - Deal.MIN_BET)/10)
			return Deal.CAPOT_BET;
		else
			return Deal.MIN_BET + adapter_view_id * 10;
	}
	static protected int ItemIdFromBet(int bet) {
		if (bet >= Deal.MIN_BET && bet <= Deal.MAX_BET) {
			return (bet - Deal.MIN_BET)/10;
		}
		else if (bet <Deal.MIN_BET) {
			return 0;
		}
		else return  ((Deal.MAX_BET - Deal.MIN_BET) / 10)+1;
	}
	/*************************
	 **** PUBLIC METHODDS ****
	 *************************/

	@Override
	public void onResume() {
		super.onResume();
		if (wl!=null) {
			wl.acquire();
		}
		configureAnnounceView();
		
	}

	@Override
	public void onPause() {
		super.onPause();
		writeGame(current_game);
		if (wl!=null) {
			wl.release();
		}
	}

	public void configureAnnounceView() {

		distribution = (TextView) findViewById(R.id.distribution);
		distribution.setText("distribution : " + current_game.getPlayer_Distribution() + "\n");

		final RadioButton radio_us = (RadioButton) findViewById(R.id.button_Us);
		final RadioButton radio_them = (RadioButton) findViewById(R.id.button_Them);
		final SeekBar bet_seekbar = (SeekBar) findViewById(R.id.bet_seekbar);
		final Button coinche_button = (Button) findViewById(R.id.coinche_button);
		final Deal d = current_game.currentDeal();
		Button button_go = ((Button) findViewById(R.id.button_go));
		button_go.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						saveDeal(AnnounceActivity.this, d, radio_us, radio_them, bet_seekbar,
								coinche_button);
						launchWaitingActivity();
					}});
		final ScrollView scoreScrollView = (ScrollView)findViewById(R.id.ScrollView01);
		final TableLayout scoreTable = (TableLayout) findViewById(R.id.display_table);
		ScoreDisplayActivity.displayScore(this, scoreTable, this.current_game);
		scoreScrollView.post(new Runnable() {
			public void run() {
				scoreScrollView.fullScroll(View.FOCUS_DOWN);
			}
		});
	}
	
	public static void saveDeal(final Activity a, final Deal d,
			final RadioButton radio_us, final RadioButton radio_them,
			final SeekBar bet_seekbar, final Button coincheButton) {
		//Save the current team betting
		if (radio_us.isChecked())
			d.setTeam_betting(Game.Us);
		else if (radio_them.isChecked()) {
			d.setTeam_betting(Game.Them);
		}
		else {
			Toast.makeText(a.getApplicationContext(), R.string.select_team , Toast.LENGTH_SHORT).show();
			return;
		}

		//Save the current bet and the multiplicator
		d.setBet(BetFromItemId(bet_seekbar.getProgress()));
		d.setCoinchedMultiplicator(multiplicatorFromText(a,coincheButton.getText().toString()));
	}
	
	public static void configureDealView(final Activity a,final Deal d) {
		final RadioButton radio_us = (RadioButton) a.findViewById(R.id.button_Us);
		final RadioButton radio_them = (RadioButton) a.findViewById(R.id.button_Them);
		if (d.team_betting == Game.Us) {
			radio_us.setChecked(true);
		}
		else if (d.team_betting == Game.Them){
			radio_them.setChecked(true);
		}

		final SeekBar bet_seekbar = (SeekBar) a.findViewById(R.id.bet_seekbar);
		final TextView progress_text = (TextView) a.findViewById(R.id.progress_text);
		final Button coinche_button = (Button) a.findViewById(R.id.coinche_button);

		final BetSeekBarListener bet_listener = new BetSeekBarListener(a, bet_seekbar, progress_text);
		bet_seekbar.setOnSeekBarChangeListener(bet_listener);
		bet_seekbar.setProgress(ItemIdFromBet(d.bet));

		coinche_button.setText(resIdFromMultiplicator(d.coinchedMultiplicator));
		coinche_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int newMultiplicator = multiplicatorFromText(a, coinche_button.getText().toString());
				newMultiplicator *= 2;
				if (newMultiplicator > 4) newMultiplicator = 1;
				coinche_button.setText(resIdFromMultiplicator(newMultiplicator));
			}
		});
	}

    private static int multiplicatorFromText(Context c, String text) {
		if (text.equals(c.getString(R.string.uncoinched))) return 1;
		else if (text.equals(c.getString(R.string.coinched))) return 2;
		else if (text.equals(c.getString(R.string.overcoinched))) return 4;
		else return -1;
    }
	private static int resIdFromMultiplicator(int coinchedMultiplicator) {
		switch (coinchedMultiplicator) {
			case 1: return R.string.uncoinched;
			case 2: return R.string.coinched;
			case 4: return R.string.overcoinched;
			default: return -1;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announce_layout);
		updatePreferences();
		current_game = (Game)getIntent().getSerializableExtra("net.homelinux.paubox.Game");

		AnnounceActivity.configureDealView(this, current_game.currentDeal());
		
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

