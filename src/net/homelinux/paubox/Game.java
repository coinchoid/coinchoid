package net.homelinux.paubox;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Game {
	/*******************
	 **** CONSTANTS ****
	 *******************/
	// To be serializable 
	public static final long serialVersionUID = 1L;

	// Players
	public static final int Nous_1 = 0; // player holding the phone
	public static final int Eux_1 = 1;
	public static final int Nous_2 = 3;
	public static final int Eux_2 = 4;
	public static final int Nous = 0;
	public static final int Eux = 1;
	//This goes in the winner field, along with "Nous" and "Eux" so the values must be different
	public static final int UNPLAYED = 2;

	/************************
	 **** CLASS VARIABLE **** 
	 ************************/
	String[] player_names;
	ArrayList<Inning> innings;

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

	/****************************
	 **** PROTECTED METHODDS ****
	 ****************************/	

	// constructors
	protected Game() {
		newGame();
	}

	protected void newGame() {
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

	protected Inning currentInning() {
		return innings.get(innings.size()-1);
	}
	protected Deal currentDeal() {
		return currentInning().currentDeal();
	}

}
