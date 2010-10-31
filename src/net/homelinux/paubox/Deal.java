package net.homelinux.paubox;

import java.io.Serializable;

public class Deal implements Serializable {

	// To be serializable
	public static final long serialVersionUID = 1L;

	int team_betting;
	int bet;
	int trump;
	int dealer;
	int winner;
	int coinchedMultiplicator;
	Boolean shuffleDeal;

	protected int getCoinchedMultiplicator() {
		return coinchedMultiplicator;
	}

	protected void setCoinchedMultiplicator(int coinchedMultiplicator) {
		this.coinchedMultiplicator = coinchedMultiplicator;
	}

	protected int getTeam_betting() {
		return team_betting;
	}

	protected void setTeam_betting(int teamBetting) {
		team_betting = teamBetting;
	}

	protected int getBet() {
		return bet;
	}

	protected void setBet(int bet) {
		this.bet = bet;
	}

	protected int getTrump() {
		return trump;
	}

	protected void setTrump(int trump) {
		this.trump = trump;
	}

	protected int getDealer() {
		return dealer;
	}

	protected void setDealer(int dealer) {
		this.dealer = dealer;
	}

	protected int getWinner() {
		return winner;
	}

	protected void setWinner(int winner) {
		this.winner = winner;
	}

	public static String betToString(int bet) {
		if (bet == CAPOT_BET)
			return "Capot !";
		else
			return Integer.toString(bet);
	}

	public static String toTrumpString(int trump) {
		switch(trump) {
		case (TRUMP_HEART) :
			//return R.string.trump_heart;
			return "Coeur";
		case (TRUMP_DIAMOND) :
			//return R.string.trump_diamond;
			return "Carreau";
		case (TRUMP_CLUB) :
			//return R.string.trump_club;
			return "Trèfle";
		case (TRUMP_SPADE) :
			//return R.string.trump_spade;
			return "Pique";
		case (TRUMP_ALL_TRUMPS) :
			//return R.string.trump_alltrump;
			return "Tout At";
		case (TRUMP_NO_TRUMP) :
			//return R.string.trump_notrump;
			return "Sans At";
		default:
			return "problem in toTrumpString";
		}
	}

	protected void newDeal() {
		bet = 80;
		trump  = TRUMP_CLUB;
		team_betting = Game.Us; // of course
		dealer = Game.Us_1;
		coinchedMultiplicator = 1;
		winner = Game.UNPLAYED;
		shuffleDeal = false;
	}

	// For the scores
	public static final int MIN_BET = 80;
	public static final int MAX_BET = 180;
	public static final int CAPOT_BET = 250; // 250 means "capot" useful for some functions

	// For the trump (one int per suit, see http://en.wikipedia.org/wiki/Belote)
	// WARNING do not change values since they are used in string.xml
	public static final int TRUMP_CLUB = 0;
	public static final int TRUMP_DIAMOND = 1;
	public static final int TRUMP_HEART = 2;
	public static final int TRUMP_SPADE = 3;
	public static final int TRUMP_NO_TRUMP = 4;
	public static final int TRUMP_ALL_TRUMPS = 5;

	public Deal() {
		newDeal();
	}

	protected void setWon(boolean won) {
	    if (team_betting == Game.Us ^ won)       //XOR
            winner = Game.Them;
        else
            winner = Game.Us;
	}

	protected String getAnnounce() {
		return betToString(bet) + " " + toTrumpString(trump);
	}

	public String toString() {
		return team_betting + ""+ getAnnounce() + " : " + (winner==team_betting ? "Faite !" : "Chute !") ;
	}

	public Boolean isShuffleDeal() {
	    return shuffleDeal;
	}

	public void setShuffleDeal(Boolean shuffleDeal) {
	    this.shuffleDeal = shuffleDeal;
	}
}
