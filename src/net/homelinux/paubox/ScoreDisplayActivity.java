package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreDisplayActivity extends Activity  {

	private Game game;
	
	static View makeDisplayView(Activity activity, Game game) {
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.score_display, null, false);
		final TableLayout table = (TableLayout) root.findViewById(R.id.display_table);
		final TableRow.LayoutParams ll = new TableRow.LayoutParams();
		ll.rightMargin = 1;
		ll.bottomMargin = 1;
		final TableRow.LayoutParams lr = new TableRow.LayoutParams();
		lr.bottomMargin = 1;
		

		int Us_score = 0, Them_score = 0;

		for (Inning i : game.innings) {
			for (Deal d : i.deals) {
				if (d.winner!=Game.UNPLAYED) {
					TableRow tr = new TableRow(activity);
					TextView left = new TextView(activity);
					TextView right = new TextView(activity);
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
		return root;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_display);
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		setContentView(ScoreDisplayActivity.makeDisplayView(this, game));
	}
}

