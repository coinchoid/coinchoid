package net.homelinux.paubox;

import android.content.Context;
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

	// Call the announce activity
	protected void launchAnnouceActivity() {
		Intent i = new Intent(this, AnnounceActivity.class);
		startActivity(i);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.waiting_layout);
		Context c = getApplicationContext();

		current_deal = (Deal)getIntent().getSerializableExtra("net.homelinux.paubox.Game");

		annouce_reminder = (TextView) findViewById(R.id.announce_reminder);
		annouce_reminder.setText(c.getString(R.string.current_annouce) + " " + current_deal.getAnnounce(this) + "\n");
		annouce_info = (TextView) findViewById(R.id.announce_info);
		annouce_info.setText(
				c.getString(R.string.to_fail) + "\n" +
				"   * " + c.getString(R.string.without_trumps) + " " + current_deal.scoreWithoutTrumps(Deal.TO_MAKE_LOSE) + "\n" +
				"   * " + c.getString(R.string.with_trumps)    + " " + current_deal.scoreWithTrumps(Deal.TO_MAKE_LOSE) + "\n" +
				"\n" +
				c.getString(R.string.to_win) + "\n" +
				"   * " + c.getString(R.string.without_trumps) + " " + current_deal.scoreWithoutTrumps(Deal.TO_WIN) + "\n" +
				"   * " + c.getString(R.string.with_trumps)    + " " + current_deal.scoreWithTrumps(Deal.TO_WIN) + "\n"
		);
		
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
		s.replaceAll("[^0-9]", "");
		if (s.equals(""))
			return 0;
		else
			return Integer.parseInt(s);
	}
}