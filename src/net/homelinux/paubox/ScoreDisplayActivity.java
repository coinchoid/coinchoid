package net.homelinux.paubox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScoreDisplayActivity extends Activity {
	
	GameView gw;
	Game game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = (Game) getIntent().getSerializableExtra("net.homelinux.paubox.Game");
		setContentView(R.layout.score_display);
		gw = (GameView) findViewById(R.id.game_view);
		gw.initGame(game, true);
		Button b = ((Button) findViewById(R.id.force_counting_system));
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewGameActivity.forcePreferencesCounting(ScoreDisplayActivity.this, game);
				gw.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BaseMenuActivity.REQUEST_CODE_EDIT) {
			if (resultCode != RESULT_CANCELED) {
				final Deal d = (Deal) data.getSerializableExtra("net.homelinux.paubox.edit");
				gw.selected_deal.setAs(d);
				gw.notifyDataSetChanged();
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
			setResult(BaseMenuActivity.REQUEST_CODE_EDIT, new Intent().putExtra("net.homelinux.paubox.edit", game));
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
