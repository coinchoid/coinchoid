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
	private static final int Nous_1 = 0; // player holding the phone
	private static final int Eux_1 = 1;
	private static final int Nous_2 = 3;
	private static final int Eux_2 = 4;
	public static final int Nous = 0;
	public static final int Eux = 1;
	//This goes in the winner field, along with "Nous" and "Eux" so the values must be different
	public static final int UNPLAYED = 2;

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
	int winner;

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
	protected void setCurrentTrump(int _current_trump) {
		current_trump = _current_trump;
	}
	protected void setCurrentBet(int _current_bet) {
		current_bet = _current_bet;
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
		current_team_betting = Nous; // of course
		current_dealer = Nous_1;
		teamE_score = 0;
		teamN_score = 0;
		winner = UNPLAYED;
	}
	protected String getAnnounce() {
		return Integer.toString(current_bet) + " " + getCurrentTrump();
	}

	protected void setWon(boolean won) {
		if (current_team_betting == Nous) 
			if (won) winner = Nous;
			else winner = Eux;
		else setWon(!won);
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
