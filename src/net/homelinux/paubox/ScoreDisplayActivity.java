package net.homelinux.paubox;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreDisplayActivity extends Activity  {

	private Game game;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_display);

//		  // wrap the table in a scrollpane
//		  scrollPane = new ScrollView(this);
//		  LayoutUtils.Layout.WidthFill_HeightWrap.applyViewGroupParams(scrollPane);
//
//		  scrollPane.addView(PanelBuilder.createWidgetPanel(this));
//		  setContentView(scrollPane);
//		}

		final TableLayout table = (TableLayout) findViewById(R.id.display_table);
		final TableRow.LayoutParams ll = new TableRow.LayoutParams();
		ll.rightMargin = 1;
		ll.bottomMargin = 1;
		final TableRow.LayoutParams lr = new TableRow.LayoutParams();
		lr.bottomMargin = 1;
		final TableLayout.LayoutParams rp = new TableLayout.LayoutParams();
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		if (game != null) {
			for (Inning i : game.innings) {
				for (Deal d : i.deals) {
					if (d.winner!=Game.UNPLAYED) {
						TableRow tr = new TableRow(this);
						TextView left = new TextView(this);
						TextView right = new TextView(this);
						left.setBackgroundColor(Color.BLACK);
						left.setGravity(Gravity.CENTER);
						right.setBackgroundColor(Color.BLACK);
						right.setGravity(Gravity.CENTER);
						if (d.winner == Game.Them) {
							left.setText(Integer.toString(d.bet));
							right.setText("");
						}
						else {
							right.setText(Integer.toString(d.bet));
							left.setText("");
						}
						tr.addView(left,ll);
						tr.addView(right,lr);
						table.addView(tr);
					}
				}
			}
		}
	}
}


