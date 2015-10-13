package net.homelinux.paubox;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.homelinux.paubox.Deal.CoinchedMultiplicator;
import net.homelinux.paubox.Deal.Player;
import net.homelinux.paubox.Deal.Team;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceActivity extends BaseMenuActivity {

    /************************
     **** CLASS VARIABLE ****
     ************************/
    LinearLayout scoreDisplayView;
    TextView distribution;

    /****************************
     **** PROTECTED METHODDS ****
     ****************************/
    // Call the waiting activity
    protected void launchWaitingActivity() {
        Intent waiting_intent = new Intent(this, WaitingActivity.class);
        putIntentGame(waiting_intent, current_game);
        waiting_intent.putExtra("net.homelinux.paubox.Deal", current_game.currentDeal());
        startActivityForResult(waiting_intent, REQUEST_CODE_WAITING);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE_PREFERENCES) {
            final Game g = getIntentGame(data);
            current_game.setAs(g);
        }
        forcePreferencesCounting(current_game);
        current_game.recomputeScores();
        updateScoresDisplay();
        switch (requestCode) {
        case REQUEST_CODE_WAITING:
            if (resultCode != RESULT_CANCELED) {
                boolean won = data.getBooleanExtra("net.homelinux.paubox.won", false);
                int difference = data.getIntExtra("net.homelinux.paubox.difference", 0);
                current_game.currentDeal().setWon(won);
                current_game.currentDeal().announce_difference = difference;
                current_game.updateResult();
                current_game.newDeal();
                int resultId = won ? R.string.deal_won : R.string.deal_lost;
                Resources r = getResources();
                String s = r.getString(R.string.deal_summary, r.getString(resultId), difference);
                Toast.makeText(getApplicationContext(), s,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Cancel",
                        Toast.LENGTH_SHORT).show();
            }
            break;
        }   
    }

    private void updateScoresDisplay() {
        ((TextView) findViewById(R.id.score_them)).setText(Integer.toString(current_game.score_Them));
        ((TextView) findViewById(R.id.score_us)).setText(Integer.toString(current_game.score_Us));
    }

    static protected int BetFromItemId(int adapter_view_id) {
        if (adapter_view_id == android.widget.AdapterView.INVALID_ROW_ID)
            return Deal.MIN_BET;
        else if (adapter_view_id > (Deal.MAX_BET - Deal.MIN_BET)/10)
            return Deal.CAPOT_BET;
        else
            return Deal.MIN_BET + adapter_view_id * 10;
    }
    static protected int ItemIdFromBet(int bet) {
        if (bet >= Deal.MIN_BET && bet <= Deal.MAX_BET) {
            return (bet - Deal.MIN_BET)/10;
        }
        else if (bet <Deal.MIN_BET) {
            return 0;
        }
        else return  ((Deal.MAX_BET - Deal.MIN_BET) / 10)+1;
    }
    /*************************
     **** PUBLIC METHODDS ****
     *************************/

    @Override
    public void onResume() {
        super.onResume();
        configureAnnounceView();
    }

    @Override
    public void onPause() {
        super.onPause();
        writeGame(current_game);
    }

    public void configureAnnounceView() {

        distribution = (TextView) findViewById(R.id.distribution);
        distribution.setText(current_game.getCurrentPlayer_name());
        registerForContextMenu(distribution);

        final RadioButton radio_us = (RadioButton) findViewById(R.id.button_Us);
        final RadioButton radio_them = (RadioButton) findViewById(R.id.button_Them);
        final SeekBar bet_seekbar = (SeekBar) findViewById(R.id.bet_seekbar);
        final Deal d = current_game.currentDeal();
        final RadioGroup coinche_radiogroup = (RadioGroup) findViewById(R.id.coinched_group);
        Button button_go = ((Button) findViewById(R.id.button_go));
        button_go.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        saveDeal(AnnounceActivity.this, d, radio_us, radio_them, bet_seekbar,
                                coinche_radiogroup);
                        launchWaitingActivity();
                    }});

        forcePreferencesCounting(current_game);
        current_game.recomputeScores();
        updateScoresDisplay();
        ((LinearLayout) findViewById(R.id.scores)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchDisplayActivity();
            }
        });
    }
    
    public static void saveDeal(final Activity a, final Deal d,
            final RadioButton radio_us, final RadioButton radio_them,
            final SeekBar bet_seekbar, final RadioGroup coinchegroup) {
        //Save the current team betting
        if (radio_us.isChecked())
            d.setTeamBetting(Team.US);
        else if (radio_them.isChecked()) {
            d.setTeamBetting(Team.THEM);
        }
        else {
            Toast.makeText(a.getApplicationContext(), R.string.select_team , Toast.LENGTH_SHORT).show();
            return;
        }

        //Save the current bet and the multiplicator
        d.setBet(BetFromItemId(bet_seekbar.getProgress()));
        d.setCoinchedMultiplicator(multiplicatorFromId(getCoinchedRadioButtonId(a)));
    }
    
    private static int getCoinchedRadioButtonId(Activity a) {
        for (int i=0; i<coinched_radiobuttons_ids.length;i++) {
            RadioButton rb = (RadioButton) a.findViewById(coinched_radiobuttons_ids[i]);
            if (rb.isChecked()) return rb.getId();
        }
        return -1;
    }


    public static void configureDealView(final Activity a,final Deal d) {
        final RadioButton radio_us = (RadioButton) a.findViewById(R.id.button_Us);
        final RadioButton radio_them = (RadioButton) a.findViewById(R.id.button_Them);
        if (d.team_betting == Team.US) {
            radio_us.setChecked(true);
        }
        else if (d.team_betting == Team.THEM){
            radio_them.setChecked(true);
        }

        final RadioButton radio_buttons[] = new RadioButton[coinched_radiobuttons_ids.length];
        for (int i=0; i<radio_buttons.length;i++) {
            radio_buttons[i] = (RadioButton) a.findViewById(coinched_radiobuttons_ids[i]);
        }
        OnClickListener radio_emulator_listener = new OnClickListener() {
            public void onClick(View v) {
                for (RadioButton rb: radio_buttons) {
                    if (v != rb)
                        rb.setChecked(false);
                }
            }
        };
        for (RadioButton rb: radio_buttons) {
            rb.setOnClickListener(radio_emulator_listener);
        }

        final SeekBar bet_seekbar = (SeekBar) a.findViewById(R.id.bet_seekbar);
        final TextView progress_text = (TextView) a.findViewById(R.id.progress_text);

        final BetSeekBarListener bet_listener = new BetSeekBarListener(a, bet_seekbar, progress_text);
        bet_seekbar.setOnSeekBarChangeListener(bet_listener);
        bet_seekbar.setProgress(ItemIdFromBet(d.bet));

        ((RadioButton) a.findViewById(idFromMultiplicator(d.getCoinchedMultiplicator()))).setChecked(true);
    }

    public static int coincheColorFromMultiplicator(Activity a, int multiplicator) {
        switch (multiplicator) {
        case 2:
            return a.getResources().getColor(R.color.orange);
        case 4:
            return a.getResources().getColor(R.color.solid_red);
        default:
            return a.getResources().getColor(R.color.green);
        }
    }

    private static int coinched_radiobuttons_ids[] = { 
        R.id.radio_uncoinched,
        R.id.radio_coinched,
        R.id.radio_overcoinched,
    };
    private static CoinchedMultiplicator multiplicatorFromId(int id) {
        int i;
        for (i=0; i< coinched_radiobuttons_ids.length; i++) {
            if (id == coinched_radiobuttons_ids[i])
                break;
        }
        switch(i) {
        case 1: return CoinchedMultiplicator.COINCHED;
        case 2: return CoinchedMultiplicator.OVERCOINCHED;
        default: return CoinchedMultiplicator.UNCOINCHED;
        }
    }
    private static int idFromMultiplicator(int multiplicator) {
        int idx;
        switch(multiplicator) {
        case 2: idx = 1; break;
        case 4: idx = 2; break;
        default: idx = 0; break;
        }
        return coinched_radiobuttons_ids[idx];
    }

    private void setDefaultNames(Game g) {
        Resources r = this.getResources();
        for (Player p: Player.values())
            g.setPlayer_name(r.getString(p.default_name_id), p);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce_layout);
        if (getIntent().getAction().equals(Intent.ACTION_DELETE)) {
            current_game = getIntentGame();
        } else {
            try {
                current_game = readGame();
            }
            catch (Exception e) {
                //Something went wrong, start a new game
                current_game = new Game();
                setDefaultNames(current_game);
            }
        }
        AnnounceActivity.configureDealView(this, current_game.currentDeal());
        
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
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
        case R.id.display_scores:
            return true;
        }
        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        int id = item.getItemId();
        for (Player p: Player.values())
            if (p.id == id) {
                configureAnnounceView();
                return true;
            }
        return false;
    }

    // To be serializable
    private static final String FILENAME = "coinchoid_current_game.ser";
    private Game readGame() throws Exception {
        Game game = null;
        FileInputStream fis = openFileInput(FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        game = (Game) ois.readObject();
        fis.close();
        return game;
    }
    private void writeGame(Game game) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

