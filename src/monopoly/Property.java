package monopoly;

import static monopoly.StdDraw.*;
import javax.swing.text.html.HTML.Tag;

import java.awt.Color;

public class Property implements Location {

	private String name;
	private int improvementCost;
	private int[] rent;
	private int improvementLevel;
	private Player owner;
	private boolean isMortgaged;
	private int price;
	private Color color;
	private int mortgagePrice;

	public Property(String name, Color color, int price, int improvementCost, int rentNone, int rentOne, int rentTwo, int rentThree, int rentFour, int rentHotel) {

		this.name = name;
		this.price = price;
		this.improvementCost = improvementCost;
		rent = new int[] { rentNone, rentOne, rentTwo, rentThree, rentFour, rentHotel };
		improvementLevel = 0;
		isMortgaged = false;
		owner = null;
		this.color = color;
		mortgagePrice = price / 2;
	}

	public String getName() {
		return name;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean isMortgaged() {
		return isMortgaged;
	}

	public void setImprovementLevel(int improvementLevel) {
		this.improvementLevel = improvementLevel;
	}

	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}

	public void improve() {
		improvementLevel++;
	}

	public void unImprove() {
		improvementLevel--;
	}

	public int getRent() {
		return rent[improvementLevel];
	}

	public int[] getRentArray() {
		return rent;
	}

	public Color getColor() {
		return color;
	}

	public int getPrice() {
		return price;
	}

	public int getMortgagePrice() {
		return mortgagePrice;
	}

	public int getImprovementCost() {
		return improvementCost;
	}
	
	public int getImprovementLevel(){
		return improvementLevel;
	}

	@Override
	public void landOn(Player player) {
		Monopoly.showLocationDeed(this);
		Monopoly.drawBoard("", 0);
		clearTypedKeys();
		while (!mousePressed()){
		}
		if (owner == null) {
			Monopoly.drawBoard(name + " is unowned. \n Would you like to purchase it for $" + price + "? (y/n)", 0);
			while (true) {
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				char response = nextKeyTyped();
				if (response == 'y' || response == 'n') {
					if (response == 'y') {
						player.setMoney(-price);
						setOwner(player);
						break;
					} else if (response == 'n') {
						break;
					}
				}
			}
		} else if (!isMortgaged) {
			if (owner.checkColorGroup(color) && improvementLevel == 0) {
				Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". You owe them $" + rent[improvementLevel] * 2 + ".", 2000);
				player.setMoney(-getRent() * 2);
				owner.setMoney(getRent() * 2);
			} else {
				Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". You owe them $" + rent[improvementLevel] + ".", 2000);
				player.setMoney(-getRent());
				owner.setMoney(getRent());
			}
		} else {
			Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". It is currently mortgaged.", 2000);
		}
	}

}
