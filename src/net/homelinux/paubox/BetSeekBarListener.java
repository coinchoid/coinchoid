package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

public class BetSeekBarListener implements SeekBar.OnSeekBarChangeListener {

	int bet;
	SeekBar seek_bar;
	TextView progress_text;
	TextView tracking_text;
	Context c;

	public BetSeekBarListener(Activity a, SeekBar _seek_bar, TextView _progress_text, TextView _tracking_text) {
		super();

		seek_bar = _seek_bar;
		progress_text = _progress_text;
		tracking_text = _tracking_text;
		c = a.getApplicationContext();
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		progress_text.setText(c.getString(R.string.seekbar_announce) + " = " + AnnounceActivity.BetFromItemId(progress));
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		tracking_text.setText(c.getString(R.string.seekbar_tracking_on));
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		tracking_text.setText(c.getString(R.string.seekbar_tracking_off));
	}

}
