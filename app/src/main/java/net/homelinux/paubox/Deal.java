package net.homelinux.paubox;

import java.io.Serializable;

import net.homelinux.paubox.Deal.Team;

import android.app.Activity;
import android.content.Context;

@SuppressWarnings("serial")
public class Deal implements Serializable {

    public enum CoinchedMultiplicator {
        UNCOINCHED(1, R.string.uncoinched),
        COINCHED(2, R.string.coinched),
        OVERCOINCHED(4, R.string.overcoinched);
        final public int m;
        final public int tId;
        CoinchedMultiplicator(int multiplicator, int textId) {
            m = multiplicator;
            tId = textId;
        }
    }
    public enum Team {
        US(R.string.Us), THEM(R.string.Them);
        static int announce_tId = R.string.announce;
        final public int bet_tId;
        Team(int textId) {
            bet_tId = textId;
        }
    }
    
    public enum Player {
        A(Team.US, BaseMenuActivity.PlayerA_menuid, R.string.name_player1),
        B(Team.THEM, BaseMenuActivity.PlayerB_menuid, R.string.name_player2),
        C(Team.US, BaseMenuActivity.PlayerC_menuid, R.string.name_player3),
        D(Team.THEM, BaseMenuActivity.PlayerD_menuid, R.string.name_player4);
        public final Team t;
        public final int id;
        public final int default_name_id;
        Player(Team team, int _id, int default_name_id) {
            t = team;
            id = _id;
            this.default_name_id = default_name_id;
        }
    }
    
    Team team_betting;
	int bet;
	Player dealer;
	Team winner;
	CoinchedMultiplicator coinchedMultiplicator;
	int announce_difference;
	boolean shuffleDeal;
	
	protected void newDeal() {
		bet = 80;
		team_betting = Team.US; // of course
		dealer = Player.A;
		coinchedMultiplicator = CoinchedMultiplicator.UNCOINCHED;
		shuffleDeal = false;
	}

	// For the scores
	public static final int MIN_BET = 80;
	public static final int MAX_BET = 180;
	public static final int CAPOT_BET = 250; // 250 means "capot" useful for some functions
	public static final int TO_MAKE_LOSE = 0;
	public static final int TO_WIN = 1;
	
	public Deal() {
		newDeal();
	}
	
	public int getCoinchedMultiplicator() {
	    return coinchedMultiplicator.m;
	}
	public void setTeamBetting(Team t) {
	    team_betting = t;
	}

	protected void setWon(boolean won) {
            winner = won ^ (team_betting == Team.US) ? Team.THEM : Team.US;
	}

	protected String getAnnounce(Context c) {
		return (betToString(bet) + " " +
		        c.getString(coinchedMultiplicator.tId, c) + " " +
		        c.getString(Team.announce_tId, c.getString(team_betting.bet_tId))).toLowerCase();
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
		announce_difference = d.announce_difference;
	}
	
	public static String betToString(int bet) {
		if (bet == CAPOT_BET)
			return "Capot !";
		else
			return Integer.toString(bet);
	}
	
	public int scoreWithoutTrumps(int what) {
		int todo = (bet * 130 + 161) / 162;
		return (what == TO_MAKE_LOSE) ? Math.max(1,(131 - todo)) : Math.min(130,todo);
	}

	public int scoreWithTrumps(int what) {
		int todo = (bet * 262 + 161) / 162;
		return (what == TO_MAKE_LOSE) ? Math.max(1,(263 - todo)) : Math.min(262,todo);
	}

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setCoinchedMultiplicator(CoinchedMultiplicator m) {
        this.coinchedMultiplicator = m;
    }

    public void setWinner(Team t) {
        winner = t;
    }
}
