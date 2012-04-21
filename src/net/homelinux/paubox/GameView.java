package net.homelinux.paubox;

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

	BaseMenuActivity activity;

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

	public GameView(Context activity, AttributeSet attrs) {
		super(activity, attrs);
		this.activity = (BaseMenuActivity) activity;
		li = LayoutInflater.from(activity);
		li.inflate(R.layout.game_view, this);
		lv = (ListView) findViewById(R.id.list);
		LinearLayout score_footer = (LinearLayout) li.inflate(R.layout.score_footer, null);
		last_score_them = (TextView) score_footer.findViewById(R.id.last_score_them);
		last_score_us = (TextView)  score_footer.findViewById(R.id.last_score_us);
		lv.addFooterView(score_footer);

	}

	public void initGame(final Game game) {
		gaw = new GameAdapterWrapper(activity, game);
		lv.setAdapter(gaw.getAdapter());
		notifyDataSetChanged();

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				selected_deal = game.innings.get(0).deals.get(pos);
				activity.launchEditActivity(selected_deal);
			}
		});
	}

	public void notifyDataSetChanged() {
		gaw.notifyDataSetChanged();
		updateFooterText();
	}
}