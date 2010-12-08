package net.homelinux.paubox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

interface DealEditor {
	public void prepareEdit();
	public void setEditListener(Deal d, TableRow tr);
}

public class ScoreDisplayActivity extends Activity implements DealEditor {

	private Game game;
	
	private View root;
	private Deal selected_deal;

	private OnTouchListener highlightListener;
	
	static View makeDisplayView(Activity a) {
		LayoutInflater inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.score_display, null, false);
		return root;
	}

	public void onResume() {
		super.onResume();
		displayScore(this, root, this.game);
	}
	
	public void setEditListener(Deal d, TableRow tr) {
		tr.setOnTouchListener(highlightListener);
		final Deal d_copy = d;
		tr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selected_deal = d_copy;
				ScoreDisplayActivity.launchEditActivity(ScoreDisplayActivity.this, d_copy);
			}
		});
	}

	public static void displayScore(Activity a, View displayView, Game game) {
		TableRow.LayoutParams ll;
		TableRow.LayoutParams lr;
		ll = new TableRow.LayoutParams();
		ll.rightMargin = 1;
		ll.bottomMargin = 1;
		lr = new TableRow.LayoutParams();
		lr.bottomMargin = 1;
		TableLayout table = (TableLayout) displayView.findViewById(R.id.display_table);
		table.removeAllViews();
		if (a instanceof DealEditor)
			((DealEditor) a).prepareEdit();
		
		
		int Us_score = 0, Them_score = 0;
		for (final Inning i : game.innings) {
			for (final Deal d : i.deals) {
				if (d.winner!=Game.UNPLAYED && !d.isShuffleDeal()) {
					TableRow tr = new TableRow(a);
					if (a instanceof DealEditor)
						((DealEditor) a).setEditListener(d, tr);
					TextView left = new TextView(a);
					TextView right = new TextView(a);
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
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		root = makeDisplayView(this);
		setContentView(root);
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

	@Override
	public void prepareEdit() {
		// TODO Auto-generated method stub
		highlightListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					TableRow row = (TableRow) v;
					for (int i = 0;i<row.getChildCount();i++) {
						TextView child = (TextView) row.getChildAt(i);
						child.setPressed(true);
						child.setBackgroundColor(Color.LTGRAY);
					}
					return false;
				}
				case MotionEvent.ACTION_OUTSIDE:
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP: {
					TableRow row = (TableRow) v;
					for (int i = 0;i<row.getChildCount();i++) {
						TextView child = (TextView) row.getChildAt(i);
						child.setPressed(false);
						child.setBackgroundColor(Color.BLACK);
					}
					return false;
				}
				}
				return false;
			}
		};
	}

}

