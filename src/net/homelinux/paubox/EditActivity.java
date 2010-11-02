package net.homelinux.paubox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

public class EditActivity extends Activity {

	Deal d;
	public void configureEditView() {

		AnnounceActivity.configureDealView(this,d);
		final RadioButton radio_us = (RadioButton) findViewById(R.id.button_Us);
		final RadioButton radio_them = (RadioButton) findViewById(R.id.button_Them);
		final SeekBar score_seekbar = (SeekBar) findViewById(R.id.bet_seekbar);
		final Spinner coinche_spinner = (Spinner) findViewById(R.id.coinche_spinner);
		final RadioButton winner_us = (RadioButton) findViewById(R.id.winner_Us);
		final RadioButton winner_them = (RadioButton) findViewById(R.id.winner_Them);
		if (d.winner==Game.Us) {
			winner_us.setChecked(true);
		} else if (d.winner==Game.Them) {
			winner_them.setChecked(true);
		}

		Button edit_ok = ((Button) findViewById(R.id.edit_button_ok));
		edit_ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						AnnounceActivity.saveDeal(EditActivity.this, d, radio_us, radio_them, score_seekbar,
								coinche_spinner);
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
}
