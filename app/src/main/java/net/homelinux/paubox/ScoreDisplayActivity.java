package net.homelinux.paubox;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ScoreDisplayActivity extends BaseMenuActivity {
	
	GameView gw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		current_game = getIntentGame();
        forcePreferencesCounting(current_game);
        current_game.recomputeScores();
		setContentView(R.layout.score_display);
		gw = (GameView) findViewById(R.id.game_view);
		gw.initGame(current_game);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BaseMenuActivity.REQUEST_CODE_EDIT) {
			if (resultCode != RESULT_CANCELED) {
				final Deal d = (Deal) data.getSerializableExtra("net.homelinux.paubox.edit");
				gw.selected_deal.setAs(d);
			}
		}
        forcePreferencesCounting(current_game);
        current_game.recomputeScores();
        gw.notifyDataSetChanged();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        Intent i = new Intent();
	        putIntentGame(i, current_game);
			setResult(BaseMenuActivity.REQUEST_CODE_EDIT, i);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.edit_menu, menu);
	    return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.new_game:
            return true;
        case R.id.quit:
            return true;
        case R.id.preferences:
            return true;
        }
        return false;
    }
}
