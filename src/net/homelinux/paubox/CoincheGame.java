package net.homelinux.paubox;

public class CoincheGame {
	protected static int NOT_PLAYED = 0;
	protected static int WON = 1;
	protected static int LOST = 2;
	private String playerNames[] = { "J1" , "J2" , "J3" , "J4" };
	private String team[] = { "J1" , "J3" } ;
	private int state = NOT_PLAYED;
	private int value = 0;
	private String suit = "NO SUIT";
	
	protected String[] getPlayerNames() {
		return playerNames;
	}
	protected void setPlayerNames(String[] playerNames) {
		this.playerNames = playerNames;
	}
	protected int getState() {
		return state;
	}
	protected void setState(int state) {
		this.state = state;
	}
	protected int getValue() {
		return value;
	}
	protected void setValue(int value) {
		this.value = value;
	}
}
