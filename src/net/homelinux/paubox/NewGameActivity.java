package net.homelinux.paubox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class NewGameActivity extends BaseMenuActivity {

	/*******************
	 **** CONSTANTS ****
	 *******************/
	// To be serializable
	public static final String FILENAME = "coinchoid_current_game.ser";

	/************************
	 **** CLASS VARIABLE ****
	 ************************/
	EditText text_players[] = new EditText[4];
	private static final int text_players_ids[] = {
		R.id.name_player1, R.id.name_player2, R.id.name_player3, R.id.name_player4
	};
	private static final int text_players_defaults[] = {
		R.string.name_player1, R.string.name_player2, R.string.name_player3, R.string.name_player4
	};
	CheckBox shuffle_game;
	
	/**************************
	 **** PRIVATE METHODS *****
	 **************************/
	private Game readGame() {
		Game game = null;
		try {
			FileInputStream fis = openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			game = (Game) ois.readObject();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return game;
	}


	/****************************
	 **** PROTECTED METHODDS ****
	 ****************************/
	// Call the waiting activity
	protected void launchAnnouceActivity() {
		Intent annouce_intent = new Intent(this, AnnounceActivity.class);
		annouce_intent.putExtra("net.homelinux.paubox.Game", current_game);
		startActivity(annouce_intent);
	}


	/*************************
	 **** PUBLIC METHODDS ****
	 *************************/
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game_layout);
		
		// Try to read saved game only if we have not clicked "new game"
		if (!getIntent().getAction().equals(Intent.ACTION_DELETE))
			current_game = readGame();

		if (current_game == null)
			current_game = new Game();

		final Button button_start = (Button) findViewById(R.id.button_new_game_go);
		button_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (int i = 0; i<Game.players_cnt; i++) {
					current_game.setPlayer_name(text_players[i].getText().toString(), Game.players[i]);
				}
				current_game.currentDeal().setShuffleDeal(shuffle_game.isChecked());
				launchAnnouceActivity();
			}
		});

		for (int i = 0; i < Game.players_cnt; i++) {
			text_players[i] = (EditText) findViewById(text_players_ids[i]);
			setDefaultFormatting(text_players[i]);
			final String default_text = getResources().getString(text_players_defaults[i]);
			text_players[i].setOnFocusChangeListener( new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					EditText et = (EditText) v;
					if (hasFocus && et.getText().toString().equals(default_text)) {
						et.setText("");
					} else {
						if (et.getText().length() == 0) {
							et.setText(default_text);
							setDefaultFormatting(et);
						} else {
							setDoneFormatting(et);
						}
					}
				}
			});
		}
		shuffle_game = (CheckBox) findViewById(R.id.shuffle_game);
	}

	private static void setDefaultFormatting(EditText et) {
		et.setTextColor(Color.GRAY);
		et.setTypeface(null, Typeface.ITALIC);
	}
	private static void setDoneFormatting(EditText et) {
		et.setTextColor(Color.BLACK);
		et.setTypeface(null, Typeface.NORMAL);
	}

}
