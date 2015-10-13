package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

public class BetSeekBarListener implements SeekBar.OnSeekBarChangeListener {

	int bet;
	SeekBar seek_bar;
	TextView progress_text;
	Context c;

	public BetSeekBarListener(Activity a, SeekBar _seek_bar, TextView _progress_text) {
		super();

		seek_bar = _seek_bar;
		progress_text = _progress_text;
		c = a.getApplicationContext();
		progress_text.setText(Deal.betToString(Deal.MIN_BET));
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		progress_text.setText(Deal.betToString(AnnounceActivity.BetFromItemId(progress)));
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
