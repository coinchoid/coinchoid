package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class WaitingActivity extends Activity {
	
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
    }
}