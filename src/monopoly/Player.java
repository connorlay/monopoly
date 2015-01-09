package monopoly;

import java.awt.Color;
import java.util.ArrayList;

import static monopoly.StdDraw.*;

public class Player {

	/**
	 * Written by Connor Lay and Elijah Goldman. Monopoly board image by Brad
	 * Frost: http://bradfrostweb.com/blog/post/monopoly-photoshop-template/
	 */

	private int playerNumber;
	private int money;
	private int location;
	private boolean hasJailCard;
	private Color color;
	private int numRailroadsOwned;
	private boolean inJail;
	private int lastRoll;
	private boolean rolledDoubles;
	private int utilitiesOwned;
	private boolean isBankrupt;
	private ArrayList<Color> colorGroups;

	public Player(int playerNumber) {
		money = 1500;
		utilitiesOwned = 0;
		location = 0;
		numRailroadsOwned = 0;
		this.playerNumber = playerNumber;
		hasJailCard = false;
		colorGroups = new ArrayList<Color>();
		if (playerNumber == 1) {
			color = StdDraw.RED;
		} else if (playerNumber == 2) {
			color = StdDraw.ORANGE;
		} else if (playerNumber == 3) {
			color = StdDraw.GREEN;
		} else if (playerNumber == 4) {
			color = StdDraw.CYAN;
		} else if (playerNumber == 5) {
			color = StdDraw.BLUE;
		} else if (playerNumber == 6) {
			color = StdDraw.MAGENTA;
		}
	}

	public int getNumRailroadsOwned() {
		return numRailroadsOwned;
	}

	public boolean isBankrupt() {
		return isBankrupt;
	}

	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}

	public void setMoney(int moneyChange) {
		money += moneyChange;
	}

	public void setLocation(int newLocation) {
		location = newLocation;
	}

	public int getLocation() {
		return location;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public Color getColor() {
		return color;
	}

	public int getMoney() {
		return money;
	}
	
	public boolean getRolledDoubles() {
		return rolledDoubles;
	}

	public void setRolledDoubles(boolean rolledDoubles) {
		this.rolledDoubles = rolledDoubles;
	}

	public void setJail(boolean inJail){
		this.inJail = inJail;
	}
	
	public Boolean getJail(){
		return inJail;
	}

	public void changeNumRailroadsOwned(int number) {
		numRailroadsOwned += number;
	}
	
	public int getLastRoll(){
		return lastRoll;
	}
	
	public void setLastRoll(int roll){
		lastRoll = roll;
	}
	
	public int getUtilitiesOwned(){
		return utilitiesOwned;
	}
	
	public void changeUtilitiesOwned(int number){
		utilitiesOwned += number;
	}
	
	public void addColorGroup(Color color){
		colorGroups.add(color);
	}
	
	public Boolean checkColorGroup(Color color){
		return colorGroups.contains(color);
	}
	
	public ArrayList<Color> getColorGroups(){
		return colorGroups;
	}
	
	public void removeColorGroup(Color color){
		colorGroups.remove(color);
	}
	
	public void setJailCard(Boolean key){
		hasJailCard = key;
	}
	
	public Boolean getHasJailCard(){
		return hasJailCard;
	}
}
