package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

public class AnnounceActivity extends Activity {
	

	private static final int MIN_BET = 80;
	private static final int MAX_BET = 160;
	int current_bet = 80;
	TextView annonce_text;
	
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
		
		annonce_text.setText(betToString(current_bet));
	}
	private String betToString(int bet) {
		if (bet == MAX_BET +10) return "Capot !";
		else return Integer.toString(bet);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final OnClickListener radio_listener = new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                // RadioButton rb = (RadioButton) v;
                // Toast.makeText(CoincheActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
            }
        };
        
        final RadioButton radio_coeur = (RadioButton) findViewById(R.id.radio_coeur);
        final RadioButton radio_carreau = (RadioButton) findViewById(R.id.radio_carreau);
        final RadioButton radio_pique = (RadioButton) findViewById(R.id.radio_pique);
        final RadioButton radio_trefle = (RadioButton) findViewById(R.id.radio_trefle);
        final RadioButton radio_sansat = (RadioButton) findViewById(R.id.radio_sansat);
        final RadioButton radio_toutat = (RadioButton) findViewById(R.id.radio_toutat);
        radio_coeur.setOnClickListener(radio_listener);
        radio_carreau.setOnClickListener(radio_listener);
        radio_pique.setOnClickListener(radio_listener);
        radio_trefle.setOnClickListener(radio_listener);
        
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.points, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        
    }
}