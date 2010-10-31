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
import android.widget.Toast;

public class ScoreDisplayActivity extends Activity  {

	private Game game;
	
	static View makeDisplayView(final Activity activity,final Game game) {
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.score_display, null, false);
		final TableLayout table = (TableLayout) root.findViewById(R.id.display_table);
		final TableRow.LayoutParams ll = new TableRow.LayoutParams();
		ll.rightMargin = 1;
		ll.bottomMargin = 1;
		final TableRow.LayoutParams lr = new TableRow.LayoutParams();
		lr.bottomMargin = 1;
		

		int Us_score = 0, Them_score = 0;
		for (final Inning i : game.innings) {
			for (final Deal d : i.deals) {
				if (d.winner!=Game.UNPLAYED) {
					TableRow tr = new TableRow(activity);
					tr.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							//Toast.makeText(activity.getApplicationContext(), "Lauching Edit", Toast.LENGTH_SHORT).show();
							ScoreDisplayActivity.launchEditActivity(activity, d);
						}
					});
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

	static void launchEditActivity(Activity activity,Deal d) {
		Intent editIntent = new Intent(activity, EditActivity.class);
		editIntent.putExtra("net.homelinux.paubox.Editable", d);
		activity.startActivity(editIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_display);
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		setContentView(ScoreDisplayActivity.makeDisplayView(this, game));
	}
}

