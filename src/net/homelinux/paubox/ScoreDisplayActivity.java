package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreDisplayActivity extends Activity  {

	private static final int EDIT = 0;
	private Game game;
	private TableLayout table;
	private TableRow.LayoutParams ll;
	private TableRow.LayoutParams lr;
	private Deal selected_deal;
	
	View makeDisplayView() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.score_display, null, false);
		table = (TableLayout) root.findViewById(R.id.display_table);
		ll = new TableRow.LayoutParams();
		ll.rightMargin = 1;
		ll.bottomMargin = 1;
		lr = new TableRow.LayoutParams();
		lr.bottomMargin = 1;
		
		return root;
	}

	public void onResume() {
		super.onResume();
		clearScores();
		displayScore();
	}

	private void clearScores() {
		table.removeAllViews();
	}

	private void displayScore() {
		int Us_score = 0, Them_score = 0;
		for (final Inning i : game.innings) {
			for (final Deal d : i.deals) {
				if (d.winner!=Game.UNPLAYED && !d.isShuffleDeal()) {
					TableRow tr = new TableRow(this);
					tr.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							selected_deal = d;
							ScoreDisplayActivity.launchEditActivity(ScoreDisplayActivity.this, d);
						}
					});
					TextView left = new TextView(this);
					TextView right = new TextView(this);
					left.setBackgroundColor(Color.BLACK);
					left.setGravity(Gravity.CENTER);
					right.setBackgroundColor(Color.BLACK);
					right.setGravity(Gravity.CENTER);
					if (d.winner == Game.Us) {
						Us_score += d.bet*d.coinchedMultiplicator;
					}
					else {
						Them_score += d.bet*d.coinchedMultiplicator;
					}
					left.setText(Integer.toString(Them_score));
					right.setText(Integer.toString(Us_score));
					tr.addView(left,ll);
					tr.addView(right,lr);
					table.addView(tr);
				}
			}
		}
	}

	static void launchEditActivity(Activity activity,Deal d) {
		Intent editIntent = new Intent(activity, EditActivity.class);
		editIntent.putExtra("net.homelinux.paubox.Editable", d);
		activity.startActivityForResult(editIntent,BaseMenuActivity.REQUEST_CODE_EDIT);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_display);
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		setContentView(makeDisplayView());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BaseMenuActivity.REQUEST_CODE_EDIT) {
			if (resultCode != RESULT_CANCELED) {
				final Deal d = (Deal) data.getSerializableExtra("net.homelinux.paubox.edit");
				this.selected_deal.setAs(d);
			}
		}
	}

	public void onPause() {
		super.onPause();
		setResult(BaseMenuActivity.REQUEST_CODE_EDIT, new Intent().putExtra("net.homelinux.paubox.edit", game));
	}
}

