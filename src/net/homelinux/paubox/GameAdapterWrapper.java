package net.homelinux.paubox;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.SimpleAdapter;

public class GameAdapterWrapper {

	static int Them = 0, Us = 1;
	static String[] from = { "score_item_them", "score_item_us" };
	static int[] to = { R.id.score_item_them, R.id.score_item_us };

	private SimpleAdapter sa;
	private Game game;
	private ArrayList<HashMap<String, String>> adapter_data;
	private HashMap<String, String> last_score;

	SimpleAdapter getAdapter() {
		return sa;
	}
	HashMap<String, String> getLastScore() {
		return last_score;
	}

	void makeData() {
		int Us_score = 0, Them_score = 0;
		for (int index=0;index<game.innings.size();index++) {
			final Inning i =game.innings.get(index);
			for (int j=0;j<i.deals.size()-1;j++) {
				final Deal d = i.deals.get(j);
				if (d.winner!=Game.UNPLAYED && !d.isShuffleDeal()) {
					boolean loose;
					int score;
					loose = d.team_betting != d.winner;
					if (loose && game.loose_160)
						score = 160;
					else
						score = d.bet;

					if (d.winner == Game.Us)
						Us_score += score*d.coinchedMultiplicator;
					else
						Them_score += score*d.coinchedMultiplicator;
				}
				HashMap<String, String> m;
				if (j==i.deals.size()-2) {
					m = last_score;
				} else {
					m = adapter_data.get(j);
				}
				m.put(from[Us], Integer.toString(Them_score));
				m.put(from[Them], Integer.toString(Us_score));
			}
		}
	}

	private void initAdapterData() {
		last_score = new HashMap<String, String>();
		adapter_data = new ArrayList<HashMap<String, String>>(game.dealCnt());
		for (int i = 0; i < game.dealCnt()-2; i++) {
			HashMap<String, String> m = new HashMap<String, String>();
			adapter_data.add(m);
		}
	}

	public GameAdapterWrapper(Context _context, Game _game) {
		game = _game;
		initAdapterData();
		makeData();
	    sa = new SimpleAdapter(_context, adapter_data, R.layout.score_display_item, from, to);
	}

	public void notifyDataSetChanged() {
		makeData();
		sa.notifyDataSetChanged();
	}
}
