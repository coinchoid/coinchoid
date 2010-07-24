package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreDisplayActivity extends Activity  {

	private Game game;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_display);

		final LinearLayout left_col = (LinearLayout) findViewById(R.id.left_column);
		final LinearLayout right_col = (LinearLayout) findViewById(R.id.right_column);


		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		for (Inning i : game.innings) {
			for (Deal d : i.deals) {
				if (d.winner!=Game.UNPLAYED) {
					TextView left = new TextView(this);
					TextView right = new TextView(this);
					if (d.winner == Game.Them) {
						left.setText(Integer.toString(d.bet));
						right.setText("");
					}
					else {
						right.setText(Integer.toString(d.bet));
						left.setText("");
					}
					left_col.addView(left);
					right_col.addView(right);
				}
			}
		}
	}
}


