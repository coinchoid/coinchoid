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
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		progress_text.setText(c.getString(R.string.seekbar_announce) + " = " + AnnounceActivity.BetFromItemId(progress));
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		progress_text.setBackgroundResource(R.color.solid_red);
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		progress_text.setBackgroundResource(R.color.black);
	}

}
