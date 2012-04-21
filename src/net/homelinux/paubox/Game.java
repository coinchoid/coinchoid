package net.homelinux.paubox;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Game implements Serializable {
	/*******************
	 **** CONSTANTS ****
	 *******************/
	// Players
	public static final int Us_1 = 0; // player holding the phone
	public static final int Them_1 = 1;
	public static final int Us_2 = 2;
	public static final int Them_2 = 3;
	public static final int players[] = { Us_1, Them_1, Us_2, Them_2 };
	public static final int players_cnt = 4;

	public static final int Us = 0;
	public static final int Them = 1;
	//This goes in the winner field, along with "Us" and "Them" so the values must be different
	public static final int UNPLAYED = 2;

	/************************
	 **** CLASS VARIABLE ****
	 ************************/
	String[] player_names;
	ArrayList<Inning> innings;
	
	int score_Us;
	int score_Them;
	int player;
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
	private int next_player (int player) {
		switch (player) {
		case Us_1:
			return Them_1;
		case Them_1:
			return Us_2;
		case Us_2:
			return Them_2;
		case Them_2:
			return Us_1;
		default:
			return -1;	
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

         if (d.winner == Game.Us)
             score_Us += score*d.coinchedMultiplicator;
         else
             score_Them += score*d.coinchedMultiplicator;
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
	            if (d.winner!=Game.UNPLAYED && !d.isShuffleDeal()) {
	                update_score(d);
	            }
	        }
	    }
	}

	private void update_distribution(){
		player = next_player(player);
	}

	private String player_Distribution(){
		return player_names[player];
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
		player = 0;
		innings = new ArrayList<Inning>();
		innings.add(new Inning());
		player_names = new String[4];
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

	protected String getPlayer_Distribution() {
		return player_Distribution();
	}

	protected int getScore_Us() {
		return score_Us;
	}

	protected int getScore_Them() {
		return score_Them;
	}

	public String getPlayer_name(int player) {
	    return player_names[player];
	}

	public void setPlayer_name(String name, int player) {
	    player_names[player] = name;
	}

	public void setPlayer(int _player) {
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
