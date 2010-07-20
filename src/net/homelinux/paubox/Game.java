package net.homelinux.paubox;

import android.content.res.Resources;

public class Game {
	/*******************
	 **** CONSTANTS ****
	 *******************/
	private static final int Nous_1 = 0; // player holding the phone
	private static final int Eux_1 = 1;
	private static final int Nous_2 = 3;
	private static final int Eux_2 = 4;
	private static final int Nous = 0;
	private static final int Eux = 1;

	// For the scores
	private static final int MIN_BET = 80;
	private static final int MAX_BET = 160;

	// For the trump (one int per suit, see http://en.wikipedia.org/wiki/Belote)
	// WARNING do not change values since they are used in string.xml
	public static final int TRUMP_CLUB = 0;
	public static final int TRUMP_DIAMOND = 1;
	public static final int TRUMP_HEART = 2;
	public static final int TRUMP_SPADE = 3;
	public static final int TRUMP_NO_TRUMP = 4;
	public static final int TRUMP_ALL_TRUMPS = 5;

	/************************
	 **** CLASS VARIABLE **** 
	 ************************/
	String[] player_names;
	int teamE_score;
	int teamN_score;
	int current_bet;
	int current_trump;
	int current_team_betting;
	int current_dealer;
	Resources res;

	/**************************
	 **** PRIVATE METHODDS ****
	 **************************/
	private int next_player (int player) {
		switch (player) {
		case Nous_1:
			return Eux_1;
		case Eux_1:
			return Nous_2;
		case Nous_2:
			return Eux_2;
		case Eux_2:
			return Nous_1;
		default:
			return -1;	
		}
	}
	private String betToString(int bet) {
		if (bet == MAX_BET +10) return "Capot !";
		else return Integer.toString(bet);
	}
	private void upBet(boolean up) {
		if (up) {
			current_bet += 10;
		} else {
			current_bet -=10;
		}
		if (current_bet < MIN_BET) {
			current_bet = MIN_BET;
		} else if (current_bet> MAX_BET) {
			current_bet = MAX_BET+10;
		}
	}
    private String toTrumpString(int trump) {
    	String [] p = res.getStringArray(R.array.trumps);
    	return p[trump];
    }

	/****************************
	 **** PROTECTED METHODDS ****
	 ****************************/	
	// getters and setters
	protected String getCurrentTrump() {
		return toTrumpString(current_trump);
	}
	protected int getCurrentBet() {
		return current_bet;
	}
	protected void setCurrentTrump(int _current_trump) {
		current_trump = _current_trump;
	}
	protected void setCurrentBet(int _current_bet) {
		current_bet = _current_bet;
	}
	protected void setRes(Resources _res) {
		res = _res;
	}
	
	// constructors
	protected Game(Resources _res) {
		res = _res;
		newGame();
	}

	// other methods
	protected void changeDealingPlayer() {
		current_dealer = next_player(current_dealer);
	}
	
	protected void newGame() {
		current_bet = 80;
		current_trump  = TRUMP_CLUB;
		current_team_betting = Nous; // of course
		current_dealer = Nous_1;
		teamE_score = 0;
		teamN_score = 0;
	}

}
