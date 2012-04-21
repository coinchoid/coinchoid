package net.homelinux.paubox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WaitingActivity extends BaseMenuActivity {

	Deal current_deal;
	TextView annouce_reminder;
	TextView annouce_info;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.waiting_layout);

		current_deal = (Deal)getIntent().getSerializableExtra("net.homelinux.paubox.Deal");
		current_game = getIntentGame();

		annouce_reminder = (TextView) findViewById(R.id.announce_reminder);
		annouce_reminder.setText(current_deal.getAnnounce(this));
		((TextView) findViewById(R.id.no_oudler_lose)).setText(Integer.toString(current_deal.scoreWithoutTrumps(Deal.TO_MAKE_LOSE)));
		((TextView) findViewById(R.id.all_oudler_lose)).setText(Integer.toString(current_deal.scoreWithTrumps(Deal.TO_MAKE_LOSE)));
		((TextView) findViewById(R.id.no_oudler_win)).setText(Integer.toString(current_deal.scoreWithoutTrumps(Deal.TO_WIN)));
		((TextView) findViewById(R.id.all_oudler_win)).setText(Integer.toString(current_deal.scoreWithTrumps(Deal.TO_WIN)));
		
		final Button winButton = (Button) findViewById(R.id.button_win);
		final EditText et = (EditText) findViewById(R.id.announce_difference);
		
		winButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent()
						.putExtra("net.homelinux.paubox.won",true)
						.putExtra("net.homelinux.paubox.difference", validateAnnounceDifference(et));
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, i);
				finish();
			}
		});
		
		
		final Button lostButton = (Button) findViewById(R.id.button_lost);
		
		lostButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent()
						.putExtra("net.homelinux.paubox.won",false)
						.putExtra("net.homelinux.paubox.difference", validateAnnounceDifference(et));
				setResult(AnnounceActivity.REQUEST_CODE_WAITING, i);
				finish();
			}
		});

	}

	static int validateAnnounceDifference(EditText et) {
		String s = et.getText().toString();
		return validateAnnounceDifference(s);
	}
	static int validateAnnounceDifference(String s) {
		s.replaceAll("[^0-9]", "");
		if (s.equals(""))
			return 0;
		else {
			return validateAnnounceDifference(Integer.parseInt(s));
		}
	}
	static int validateAnnounceDifference(int i) {
		return Math.max(0, i);
	}
	static void setValidatedScore(EditText et, int i) {
		et.setText(Integer.toString(validateAnnounceDifference(i)));
	}
	static void setValidatedScore(EditText et, String s) {
		et.setText(Integer.toString(validateAnnounceDifference(s)));
	}
}