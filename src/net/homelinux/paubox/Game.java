package net.homelinux.paubox;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
	/*******************
	 **** CONSTANTS ****
	 *******************/
	// To be serializable
	public static final long serialVersionUID = 1L;

	// Players
	public static final int Us_1 = 0; // player holding the phone
	public static final int Them_1 = 1;
	public static final int Us_2 = 3;
	public static final int Them_2 = 4;
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

	private void update_score(){
		if (currentDeal().winner == Game.Us)
			score_Us = score_Us + currentDeal().bet*currentDeal().getCoinchedMultiplicator();
		else
			score_Them = score_Them + currentDeal().bet*currentDeal().getCoinchedMultiplicator();			
	}

	private void update_distribution(){
		player = next_player(player);
	}

	private String player_Distribution(){
		switch(player) {
		case 0:
			return "Us_1";
		case 1:
			return "Them_1";
		case 2:
			return "Us_2";
		case 3:
			return "Them_2";
		default:
			return "error player_Distribution ";
		}
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

}
