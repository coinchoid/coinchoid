package net.homelinux.paubox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	EditText text_player1;
	EditText text_player2;
	EditText text_player3;
	EditText text_player4;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
		current_game = readGame();

		if (current_game == null) {
			current_game = new Game();
		}

		final Button button_start = (Button) findViewById(R.id.button_new_game_go);
		button_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				current_game.setPlayer_name(text_player1.getText().toString(), Game.Us_1);
				current_game.setPlayer_name(text_player2.getText().toString(), Game.Them_1);
				current_game.setPlayer_name(text_player3.getText().toString(), Game.Us_2);
				current_game.setPlayer_name(text_player4.getText().toString(), Game.Them_2);
				current_game.currentDeal().setShuffleDeal(shuffle_game.isChecked());
				launchAnnouceActivity();
			}
		});

		text_player1 = (EditText) findViewById(R.id.name_player1);		
		text_player2 = (EditText) findViewById(R.id.name_player2);		
		text_player3 = (EditText) findViewById(R.id.name_player3);		
		text_player4 = (EditText) findViewById(R.id.name_player4);		
		shuffle_game = (CheckBox) findViewById(R.id.shuffle_game);
	}

}
