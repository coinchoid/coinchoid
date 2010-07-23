package net.homelinux.paubox;

import android.os.Parcelable;
import android.os.Parcel;

public class Game implements Parcelable {
	/*******************
	 **** CONSTANTS ****
	 *******************/
	// To be serializable 
	private static final long serialVersionUID = 1L;

	// Players
	private static final int Us_1 = 0; // player holding the phone
	private static final int Them_1 = 1;
	private static final int Us_2 = 3;
	private static final int Them_2 = 4;
	public static final int Us = 0;
	public static final int Them = 1;
	//This goes in the winner field, along with "Us" and "Them" so the values must be different
	public static final int UNPLAYED = 2;

	// For the scores
	private static final int MIN_BET = 80;
	private static final int CAPOT_BET = 190; // 190 means "capot" useful for some functions

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
	int winner;

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
	private String betToString(int bet) {
		if (bet == CAPOT_BET)
			return "Capot !";
		else
			return Integer.toString(bet);
	}
	private String toTrumpString(int trump) {
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
	private void writeGameToParcel(Parcel out, int flags) {
		//out.writeStringArray(player_names);
		out.writeInt(teamE_score);
		out.writeInt(teamN_score);
		out.writeInt(current_bet);
		out.writeInt(current_trump);
		out.writeInt(current_team_betting);
		out.writeInt(current_dealer);
		out.writeInt(winner);
	}
	private void readParcelToGame(Parcel in) {
		//in.readStringArray(player_names);
		teamE_score = in.readInt();
		teamN_score = in.readInt();
		current_bet = in.readInt();
		current_trump = in.readInt();
		current_team_betting = in.readInt();
		current_dealer = in.readInt();
		winner = in.readInt();
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
	protected String getCurrentBetString() {
		return betToString(current_bet);
	}
	protected void setCurrentTrump(int _current_trump) {
		current_trump = _current_trump;
	}
	protected void setCurrentBet(int _current_bet) {
		current_bet = _current_bet;
	}
	protected void setCurrentBetFromItemId(long adapter_view_id) {
		if (adapter_view_id == android.widget.AdapterView.INVALID_ROW_ID)
			current_bet = MIN_BET;
		else
			current_bet = MIN_BET + (int)adapter_view_id * 10;
	}

	// constructors
	protected Game() {
		newGame();
	}
	protected Game(Parcel in) {
		readParcelToGame(in);
	}
	// other methods
	protected void changeDealingPlayer() {
		current_dealer = next_player(current_dealer);
	}

	protected void newGame() {
		current_bet = 80;
		current_trump  = TRUMP_CLUB;
		current_team_betting = Us; // of course
		current_dealer = Us_1;
		teamE_score = 0;
		teamN_score = 0;
		winner = UNPLAYED;
	}
	protected String getAnnounce() {
		return betToString(current_bet) + " " + getCurrentTrump();
	}

	protected void setWon(boolean won) {
		if (current_team_betting == Us ^ won)
			winner = Them;
		else
			winner = Us;
	}
	/*************************
	 **** PUBLIC METHODDS ****
	 *************************/
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Game> CREATOR
	= new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) {
			return new Game(in);
		}

		public Game[] newArray(int size) {
			return new Game[size];
		}
	};
	public void writeToParcel(Parcel out, int flags) {
		writeGameToParcel(out, flags);
	}

	public String toString() {
		String result = "Attaque : " + current_team_betting + "Annonce " + this.getAnnounce();
		return result;
	}

}
