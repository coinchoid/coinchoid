package net.homelinux.paubox;

import android.os.Parcel;
import android.os.Parcelable;

public class Deal implements Parcelable {

	int teamE_score;
	int teamN_score;
	int team_betting;
	int bet;
	int trump;
	int dealer;
	int winner;

	protected int getTeamE_score() {
		return teamE_score;
	}

	protected void setTeamE_score(int teamEScore) {
		teamE_score = teamEScore;
	}

	protected int getTeamN_score() {
		return teamN_score;
	}

	protected void setTeamN_score(int teamNScore) {
		teamN_score = teamNScore;
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
		teamE_score = 0;
		teamN_score = 0;
		winner = Game.UNPLAYED;
	}

	// For the scores
	public static final int MIN_BET = 80;
	public static final int CAPOT_BET = 190; // 190 means "capot" useful for some functions

	// For the trump (one int per suit, see http://en.wikipedia.org/wiki/Belote)
	// WARNING do not change values since they are used in string.xml
	public static final int TRUMP_CLUB = 0;
	public static final int TRUMP_DIAMOND = 1;
	public static final int TRUMP_HEART = 2;
	public static final int TRUMP_SPADE = 3;
	public static final int TRUMP_NO_TRUMP = 4;
	public static final int TRUMP_ALL_TRUMPS = 5;



	private void writeGameToParcel(Parcel out, int flags) {
		//out.writeStringArray(player_names);
		out.writeInt(teamE_score);
		out.writeInt(teamN_score);
		out.writeInt(bet);
		out.writeInt(trump);
		out.writeInt(team_betting);
		out.writeInt(dealer);
		out.writeInt(winner);
	}
	private void readParcelToGame(Parcel in) {
		//in.readStringArray(player_names);
		teamE_score = in.readInt();
		teamN_score = in.readInt();
		bet = in.readInt();
		trump = in.readInt();
		team_betting = in.readInt();
		dealer = in.readInt();
		winner = in.readInt();
	}

	public int describeContents() {
		return 0;
	}

	protected Deal(Parcel in) {
		readParcelToGame(in);
	}

	public Deal() {
		newDeal();
	}

	public static final Parcelable.Creator<Deal> CREATOR
	= new Parcelable.Creator<Deal>() {
		public Deal createFromParcel(Parcel in) {
			return new Deal(in);
		}

		public Deal[] newArray(int size) {
			return new Deal[size];
		}
	};
	public void writeToParcel(Parcel out, int flags) {
		writeGameToParcel(out, flags);
	}

	protected void setWon(boolean won) {
	    if (team_betting == Game.Us ^ won)
            winner = Game.Them;
        else
            winner = Game.Us;

	}

	protected String getAnnounce() {
		return betToString(bet) + " " + toTrumpString(trump);
	}

}
