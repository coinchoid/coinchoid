package net.homelinux.paubox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import net.homelinux.paubox.Deal.Player;
import net.homelinux.paubox.Deal.Team;

@SuppressWarnings("serial")
public class Game implements Serializable {

	/************************
	 **** CLASS VARIABLE ****
	 ************************/
    HashMap<Player, String> player_names;
    ArrayList<Inning> innings;
	
	int score_Us;
	int score_Them;
	Player player;
	boolean loose_160;
	public int win_score_mode;
	//Should match the xml: arrays.xml
	public static int SCORE_BETONLY = 0;
	public static int SCORE_RESULTONLY = 1;
	public static int SCORE_SUMBETRESULT = 2;

	static int parseScoreString(String s) {
			int i = Integer.parseInt(s);
			if (i<SCORE_BETONLY || i>SCORE_SUMBETRESULT) return SCORE_BETONLY;
			return i;
	}

	/**************************
	 **** PRIVATE METHODDS ****
	 **************************/
	private Player next_player (Player player) {
		switch (player) {
		case A:
			return Player.B;
		case B:
			return Player.C;
		case C:
			return Player.D;
		case D:
			return Player.A;
		default:
		    return null;
		}
	}

	private void update_score(Deal d){
	     boolean loose;
         int score;
         loose = d.team_betting != d.winner;
         if (loose)
             if (loose_160)
                 score = 160;
             else
                 score = d.bet;
         else
             if (win_score_mode == Game.SCORE_SUMBETRESULT)
                 score = d.bet + d.bet + d.announce_difference;
             else if (win_score_mode == Game.SCORE_RESULTONLY)
                 score = d.bet + d.announce_difference;
             else
                 score = d.bet;

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
		player_names = new HashMap<Deal.Player, String>();
		loose_160 = false;
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

	public void setLoose_160(boolean _loose_160) {
	    loose_160 = _loose_160;
	}

	public void setAs(Game g) {
		this.loose_160 = g.loose_160;
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
