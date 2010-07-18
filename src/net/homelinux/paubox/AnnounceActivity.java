package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AnnounceActivity extends Activity {
	
	/*******************
	 **** CONSTANTS ****
	 *******************/
	// For the menu
    private static final int MENU_NEW_GAME = 1;
    private static final int MENU_PREFERENCES = 2;
    private static final int REQUEST_CODE_PREFERENCES = 1;
	// For the scores
	private static final int MIN_BET = 80;
	private static final int MAX_BET = 160;
	// For the trump (one int per suit, see http://en.wikipedia.org/wiki/Belote)
	// WARNING do not change values since they are used in string.xml
	private static final int TRUMP_CLUB = 0;
	private static final int TRUMP_DIAMOND = 1;
	private static final int TRUMP_HEART = 2;
	private static final int TRUMP_SPADE = 3;
	private static final int TRUMP_NO_TRUMP = 4;
	private static final int TRUMP_ALL_TRUMPS = 5;

	/************************
	 **** CLASS VARIABLE **** 
	 ************************/
	int current_bet = 80;
	int current_trump = TRUMP_CLUB;
	TextView debug_text;
    Spinner score_spinner;   


	/**************************
	 **** PRIVATE METHODDS ****
	 **************************/
	private void upBet(boolean up) {
		if (up) {
			current_bet += 10;
		} else {
			current_bet -=10;
		}
		if (current_bet < MIN_BET) {
			current_bet = MIN_BET;
		} else if (current_bet> MAX_BET) {
			current_bet = MAX_BET+10;
		}
		
		debug_text.setText(betToString(current_bet));
	}
	private String betToString(int bet) {
		if (bet == MAX_BET +10) return "Capot !";
		else return Integer.toString(bet);
	}
	private void newGame() {
		upBet(true);
	}
    private void updateDebugText() {
        // Since we're in the same package, we can use this context to get
        // the default shared preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final String winning_score = sharedPref.getString("winning_score", "coucou");
        debug_text.setText("Current: " + current_bet + " " + toTrumpString(current_trump) + " (max = " + winning_score + ")");
    }
    private int toTrumpInt(int id) {
    	switch (id) {
    		case (R.id.radio_heart) :
    			return TRUMP_HEART;
    		case (R.id.radio_diamond) :
    			return TRUMP_DIAMOND;
    		case (R.id.radio_club) :
    			return TRUMP_CLUB;
    		case (R.id.radio_spade) :
    			return TRUMP_SPADE;
    		case (R.id.radio_alltrump) :
    			return TRUMP_ALL_TRUMPS;
    		case (R.id.radio_notrump) :
    			return TRUMP_NO_TRUMP;
        	}
    	return -1;
    }
    private String toTrumpString(int trump) {
    	String [] p = getResources().getStringArray(R.array.trumps);
    	return p[trump];
    }

	/****************************
	 **** PROTECTED METHODDS ****
	 ****************************/
	// Call the waiting activity
    protected void launchWaitingActivity() {
        Intent i = new Intent(this, WaitingActivity.class);
        startActivity(i);
    }
	// Call the preferences activity
    protected void launchPreferencesActivity() {
    	debug_text.setText("Preferences");
        Intent launchPreferencesIntent = new Intent(this, PreferencesActivity.class);
        // Make it a subactivity so we know when it returns
        startActivityForResult(launchPreferencesIntent, REQUEST_CODE_PREFERENCES);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // The preferences returned if the request code is what we had given
        // earlier in startSubActivity
        if (requestCode == REQUEST_CODE_PREFERENCES) {
            // Read a sample value they have set
        	updateDebugText();
        }
    }

	/*************************
	 **** PUBLIC METHODDS ****
	 *************************/
    /**
     * Invoked during initialization to give the Activity a chance to set up its Menu.
     * 
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_NEW_GAME, 0, R.string.menu_new_game);
        menu.add(0, MENU_PREFERENCES, 0, R.string.menu_preferences);

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
            case MENU_NEW_GAME:
                newGame();
                return true;
            case MENU_PREFERENCES:
                launchPreferencesActivity();
                return true;
        }
        return false;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce_layout);
        
        final OnClickListener radio_listener = new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                RadioButton rb = (RadioButton) v;
                current_trump = toTrumpInt(rb.getId());
                updateDebugText();
            }
        };  
        
        final RadioButton radio0 = (RadioButton) findViewById(R.id.radio_heart);
        final RadioButton radio1 = (RadioButton) findViewById(R.id.radio_diamond);
        final RadioButton radio2 = (RadioButton) findViewById(R.id.radio_spade);
        final RadioButton radio3 = (RadioButton) findViewById(R.id.radio_club);
        final RadioButton radio4 = (RadioButton) findViewById(R.id.radio_alltrump);
        final RadioButton radio5 = (RadioButton) findViewById(R.id.radio_notrump);
        final Button button_go = (Button) findViewById(R.id.button_go);
        debug_text = (TextView) findViewById(R.id.debug_text);
        radio0.setOnClickListener(radio_listener);
        radio1.setOnClickListener(radio_listener);
        radio2.setOnClickListener(radio_listener);
        radio3.setOnClickListener(radio_listener);
        radio4.setOnClickListener(radio_listener);
        radio5.setOnClickListener(radio_listener);

        button_go.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                launchWaitingActivity();
            }
        });
        score_spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.points, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        score_spinner.setAdapter(adapter);

        score_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
            	current_bet = Integer.parseInt(parent.getSelectedItem().toString());
            	updateDebugText();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // We don't need to worry about nothing being selected, since Spinners don't allow
                // this.
            }
        });
    }
}