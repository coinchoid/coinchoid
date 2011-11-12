package net.homelinux.paubox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ScoreInputView extends LinearLayout {

	public ScoreInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater li = LayoutInflater.from(context);
		li.inflate(R.layout.score_input_view, this);
		final Button button_decr = (Button) findViewById(R.id.score_input_decr);
		final Button button_incr = (Button) findViewById(R.id.score_input_incr);
		final EditText et = (EditText) findViewById(R.id.announce_difference);
		button_incr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int i = WaitingActivity.validateAnnounceDifference(et);
				et.setText(Integer.toString(i+10));
			}
		});
		button_decr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int i = WaitingActivity.validateAnnounceDifference(et);
				et.setText(Integer.toString(i-10));
			}
		});
	}
}
