package net.homelinux.paubox;

import java.io.Serializable;

public class Deal implements Serializable {

	// To be serializable
	public static final long serialVersionUID = 1L;

	int team_betting;
	int bet;
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

	// TODO : use strings in strings.xml
	private String coinchedMultiplicatorToString() {
		if (coinchedMultiplicator == 1)
			return "";

		if (coinchedMultiplicator == 2)
			return " coinché";

		if (coinchedMultiplicator == 4)
			return " surcoinché";

		return "ERROR";
	}

	// TODO : use strings in strings.xml
	private String betterToString() {
		if (team_betting == Game.Us)
			return "pour Nous";

		if (team_betting == Game.Them)
			return "pour Eux";

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

	protected String getAnnounce() {
		return betToString() + coinchedMultiplicatorToString() + " " + betterToString();
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
