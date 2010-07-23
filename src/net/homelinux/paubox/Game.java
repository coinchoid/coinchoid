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
