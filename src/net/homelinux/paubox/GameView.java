package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GameView extends LinearLayout {

	Context c;

	TextView last_score_them; 
	TextView last_score_us; 
	ListView lv;

	Deal selected_deal;
	LayoutInflater li;

	GameAdapterWrapper gaw;

	private void updateFooterText() {
		last_score_them.setText(gaw.getLastScore().get(GameAdapterWrapper.from[GameAdapterWrapper.Them]));
		last_score_us.setText(gaw.getLastScore().get(GameAdapterWrapper.from[GameAdapterWrapper.Us]));
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.c = context;
		li = LayoutInflater.from(c);
		li.inflate(R.layout.game_view, this);
		lv = (ListView) findViewById(R.id.list);
		LinearLayout score_footer = (LinearLayout) li.inflate(R.layout.score_footer, null);
		last_score_them = (TextView) score_footer.findViewById(R.id.last_score_them);
		last_score_us = (TextView)  score_footer.findViewById(R.id.last_score_us);
		lv.addFooterView(score_footer);

	}

	public void initGame(final Game game, boolean editable) {
		gaw = new GameAdapterWrapper(c, game);
		lv.setAdapter(gaw.getAdapter());
		notifyDataSetChanged();

		if (editable) {
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int pos,
						long id) {
					selected_deal = game.innings.get(0).deals.get(pos);
					EditActivity.launchEditActivity((Activity)c, selected_deal);
				}
			});
		} else {
			lv.setSelector(android.R.color.transparent);
			lv.post(new Runnable() {
				public void run() {
					for (int i=0; i<lv.getChildCount();i++) {
						LinearLayout ll = (LinearLayout) lv.getChildAt(i);
						for (int j=0; j<ll.getChildCount(); j++) {
							View v = ll.getChildAt(j);
							if (v instanceof TextView) {
								((TextView) v).setTextColor(getResources().getColor(R.color.white));
							}
						}
					}
				}
			});
		}
	}

	public void notifyDataSetChanged() {
		gaw.notifyDataSetChanged();
		updateFooterText();
	}
}