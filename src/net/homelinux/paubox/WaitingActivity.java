package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class WaitingActivity extends Activity {
	
	Game current_game;
	TextView annouce_reminder;
	
	// Call the announce activity
    protected void launchContactAdder() {
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
    }
}