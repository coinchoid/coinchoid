package net.homelinux.paubox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

import android.content.Context;

import net.homelinux.paubox.Deal.CoinchedMultiplicator;
import net.homelinux.paubox.Deal.Player;
import net.homelinux.paubox.Deal.Team;

@SuppressWarnings("serial")
public class Game implements Serializable {

    /************************
     **** CLASS VARIABLE ****
     ************************/
    EnumMap<Player, String> player_names;
    ArrayList<Inning> innings;
    
    int score_Us;
    int score_Them;
    Player player;
    public LossScoreCountingMode loss_score_mode;
    public WinScoreCountingMode win_score_mode;
    //Should match the xml: arrays.xml
    public enum WinScoreCountingMode {
        BETONLY(R.string.pref_win_score_mode_bet_value),
        RESULTONLY(R.string.pref_win_score_mode_actual_value),
        SUMBETRESULT(R.string.pref_win_score_mode_sum_value);
        public int id;
        WinScoreCountingMode(int id) {
            this.id = id;
        }
        public static WinScoreCountingMode fromStringValue(String value, Context c) {
            for (WinScoreCountingMode a: WinScoreCountingMode.values())
                if (c.getString(a.id).equals(value))
                    return a;
            return null;
        }
    }
    public enum LossScoreCountingMode {
        BET(R.string.pref_loss_score_mode_bet_value),
        FIXED(R.string.pref_loss_score_mode_fixed_value);
        public int id;
        LossScoreCountingMode(int id) {
            this.id = id;
        }
        public static LossScoreCountingMode fromStringValue(String value, Context c) {
            for (LossScoreCountingMode a: LossScoreCountingMode.values())
                if (c.getString(a.id).equals(value))
                    return a;
            return null;
        }
    }
    int LossFixedValue = 160;

    /**************************
     **** PRIVATE METHODDS ****
     **************************/
    private Player next_player (Player player) {
        Player[] values = Player.values();
            for (int i=0; i<values.length; i++)
                if (values[i] == player)
                    return values[(i+1)%values.length];
        return null;
    }

    private void update_score(Deal d){
         boolean loose;
         int score;
         loose = d.team_betting != d.winner;
         if (loose)
             switch (loss_score_mode) {
             case FIXED: score = LossFixedValue; break;
             case BET:
             default:
                 score = d.bet; break;
             }
         else
             switch (win_score_mode) {
             case SUMBETRESULT:
                 score = d.bet + d.bet + d.announce_difference;
                 break;
             case RESULTONLY:
                 score = d.bet + d.announce_difference;
                 break;
             case BETONLY:
             default:
                 score = d.bet;
                 break;
             }

         if (d.winner == Team.US)
             score_Us += score*d.coinchedMultiplicator.m;
         else
             score_Them += score*d.coinchedMultiplicator.m;
    }

    private void update_score(){
        update_score(currentDeal());
    }

    public void recomputeScores() {
        //We only support 1 inning for now
        score_Us = score_Them = 0;
        for (int index=0;index<innings.size();index++) {
            final Inning i = innings.get(index);
            for (int j=0;j<i.deals.size()-1;j++) {
                final Deal d = i.deals.get(j);
                if (d.winner!=null && !d.isShuffleDeal()) {
                    update_score(d);
                }
            }
        }
    }

    private void update_distribution(){
        player = next_player(player);
    }

    /****************************
     **** PROTECTED METHODDS ****
     ****************************/  

    // constructors
    protected Game() {
        newGame();
    }

    protected void newGame() {
        score_Us = 0;
        score_Them = 0;
        player = Player.A;
        innings = new ArrayList<Inning>();
        innings.add(new Inning());
        player_names = new EnumMap<Deal.Player, String>(Player.class);
        loss_score_mode = LossScoreCountingMode.BET;
    }

    /*************************
     **** PUBLIC METHODDS ****
     *************************/

    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Inning i : innings) {
            result.append(i.toString()).append("\n");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    protected void updateResult() {
        update_score();
        update_distribution();
    }

    protected Inning currentInning() {
        return innings.get(innings.size()-1);
    }

    protected Deal currentDeal() {
        return currentInning().currentDeal();
    }

    protected void newDeal() {
        currentInning().newDeal();
    }

    protected void newInning() {
        innings.add(new Inning());
    }

    protected int getScore_Us() {
        return score_Us;
    }

    protected int getScore_Them() {
        return score_Them;
    }

    public String getPlayer_name(Player player) {
        return player_names.get(player);
    }
    
    public String getCurrentPlayer_name() {
        return player_names.get(player);
    }

    public void setPlayer_name(String name, Player player) {
        player_names.put(player, name);
    }

    public void setPlayer(Player _player) {
        player = _player;
    }

    public void setLossMode(LossScoreCountingMode mode) {
        loss_score_mode = mode;
    }

    public void setAs(Game g) {
        this.loss_score_mode = g.loss_score_mode;
        this.win_score_mode = g.win_score_mode;
        for (int i=0;i<innings.size();i++) {
            (innings.get(i)).setAs(g.innings.get(i));
        }
    }

    public int dealCnt() {
        //TODO change this when multiple innings are supported
        if (innings.isEmpty()) return 0;
        else return innings.get(0).deals.size();
    }
}
