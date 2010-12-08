package net.homelinux.paubox;

import java.io.Serializable;

import android.app.Activity;

@SuppressWarnings("serial")
public class Deal implements Serializable {

	int team_betting;
	int bet;
	int dealer;
	int winner;
	int coinchedMultiplicator;
	boolean shuffleDeal;

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

	private String betToString() {
		if (bet == CAPOT_BET)
			return "Capot !";
		else
			return Integer.toString(bet);
	}

	private String coinchedMultiplicatorToString(Activity a) {
		if (coinchedMultiplicator == 1)
			return "";

		if (coinchedMultiplicator == 2)
			return " " + a.getApplicationContext().getText(R.string.coinched).toString();

		if (coinchedMultiplicator == 4)
			return " " + a.getApplicationContext().getText(R.string.overcoinched).toString();

		return "ERROR";
	}

	private String betterToString(Activity a) {
		if (team_betting == Game.Us)
			return a.getApplicationContext().getText(R.string.better_to_string_us).toString();

		if (team_betting == Game.Them)
			return a.getApplicationContext().getText(R.string.better_to_string_them).toString();

		return "ERROR";
	}

	protected void newDeal() {
		bet = 80;
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

	public Deal() {
		newDeal();
	}

	protected void setWon(boolean won) {
	    if (team_betting == Game.Us ^ won)       //XOR
            winner = Game.Them;
        else
            winner = Game.Us;
	}

	protected String getAnnounce(Activity a) {
		return betToString() + coinchedMultiplicatorToString(a) + " " + betterToString(a);
	}

	public String toString(Activity a) {
		return team_betting + " " + getAnnounce(a) + " : " + (winner==team_betting ? "Faite !" : "Chute !") ;
	}

	public Boolean isShuffleDeal() {
	    return shuffleDeal;
	}

	public void setShuffleDeal(Boolean shuffleDeal) {
	    this.shuffleDeal = shuffleDeal;
	}

	public void setAs(Deal d) {
		team_betting = d.team_betting;
		bet = d.bet;
		dealer = d.dealer;
		winner = d.winner;
		coinchedMultiplicator = d.coinchedMultiplicator;
		shuffleDeal = d.shuffleDeal;
	}
	
	public static String betToString(int bet) {
		if (bet == CAPOT_BET)
			return "Capot !";
		else
			return Integer.toString(bet);
	}
}
