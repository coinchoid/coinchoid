package net.homelinux.paubox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class EditActivity extends BaseMenuActivity {

	Deal d;
	public void configureEditView() {

	    current_game = getIntentGame();

		AnnounceActivity.configureDealView(this,d);
		final RadioButton radio_us = (RadioButton) findViewById(R.id.button_Us);
		final RadioButton radio_them = (RadioButton) findViewById(R.id.button_Them);
		final SeekBar score_seekbar = (SeekBar) findViewById(R.id.bet_seekbar);
		final RadioGroup coinche_radiogroup = (RadioGroup) findViewById(R.id.coinched_group);
		final RadioButton winner_us = (RadioButton) findViewById(R.id.winner_Us);
		final RadioButton winner_them = (RadioButton) findViewById(R.id.winner_Them);
		if (d.winner==Game.Us) {
			winner_us.setChecked(true);
		} else if (d.winner==Game.Them) {
			winner_them.setChecked(true);
		}

		final EditText et = (EditText) findViewById(R.id.announce_difference);
		WaitingActivity.setValidatedScore(et,d.announce_difference);

		Button edit_ok = ((Button) findViewById(R.id.edit_button_ok));
		edit_ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						AnnounceActivity.saveDeal(EditActivity.this, d, radio_us, radio_them, score_seekbar,
								coinche_radiogroup);
						d.announce_difference = WaitingActivity.validateAnnounceDifference(et);
						if (winner_us.isChecked()) {
							d.setWinner(Game.Us);
						} else if (winner_them.isChecked()) {
							d.setWinner(Game.Them);
						}
						setResult(BaseMenuActivity.REQUEST_CODE_EDIT, new Intent().putExtra("net.homelinux.paubox.edit",d));
						finish();
					}});
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		d = (Deal)getIntent().getSerializableExtra("net.homelinux.paubox.Editable");
		setContentView(R.layout.edit_layout);
		configureEditView();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
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
        }
        return false;
    }
}
