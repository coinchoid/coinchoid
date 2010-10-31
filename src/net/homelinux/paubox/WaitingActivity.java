package net.homelinux.paubox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WaitingActivity extends BaseMenuActivity {

	Deal current_game;
	TextView annouce_reminder;

	// Call the announce activity
	protected void launchAnnouceActivity() {
		Intent i = new Intent(this, AnnounceActivity.class);
		startActivity(i);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.waiting_layout);

		current_game = (Deal)getIntent().getSerializableExtra("net.homelinux.paubox.Game");

		annouce_reminder = (TextView) findViewById(R.id.announce_reminder);
		annouce_reminder.setText("Annonce en cours : " + current_game.getAnnounce(this));
		

		final Button winButton = (Button) findViewById(R.id.button_win);
		
		winButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, new Intent().putExtra("net.homelinux.paubox.won",true));
				
				finish();
			}
		});
		
		
		final Button lostButton = (Button) findViewById(R.id.button_lost);
		
		lostButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, new Intent().putExtra("net.homelinux.paubox.won",false));
				finish();
			}
		});
	}
}