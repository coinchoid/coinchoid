package net.homelinux.paubox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WaitingActivity extends Activity {
	
	Game current_game;
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
    	
    	Bundle b = getIntent().getExtras();
    	current_game = b.getParcelable("net.homelinux.paubox.Game");
    	
    	annouce_reminder = (TextView) findViewById(R.id.announce_reminder);
    	annouce_reminder.setText("Annonce en cours : " + current_game.getAnnounce());
    	//annouce_reminder.setText("Annonce en cours : test");
    	
    	final Button winButton = (Button) findViewById(R.id.button_win);
    	winButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, new Intent().putExtra("net.homelinux.paubox.won",true));
			    finish();
			}
		});
    	final Button lostButton = (Button) findViewById(R.id.button_lost);
    	lostButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, new Intent().putExtra("net.homelinux.paubox.won",false));
				finish();
			}
		});
    }
}