package monopoly;

import static monopoly.StdDraw.*;

import java.awt.Color;

public class Utility implements Location {

	private String name;
	private Player owner;
	private final int PRICE = 150;
	private final int MORTGAGE = 75;
	private Color color;
	private boolean isMortgaged;

	public Utility(String name, Color color) {
		this.name = name;
		owner = null;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void landOn(Player player) {
		Monopoly.showLocationDeed(this);
		Monopoly.drawBoard("",0);
		clearTypedKeys();
		while (!hasNextKeyTyped()) {
		}
		if (owner == null) {
			Monopoly.drawBoard(name + " is unowned. \n Would you like to purchase it for $" + PRICE + "? (y/n)", 0);
			while (true) {
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				char response = nextKeyTyped();
				if (response == 'y' || response == 'n') {
					if (response == 'y') {
						player.setMoney(-PRICE);
						setOwner(player);
						break;
					} else if (response == 'n') {
						break;
					}
				}
			}
		} else if (!isMortgaged) {
			Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". You owe them $" + getRent(player), 2000);
			player.setMoney(-getRent(player));
			owner.setMoney(getRent(player));
		} else {
			Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". It is currently mortgaged.", 2000);
		}
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getRent(Player player) {
		if (owner.getUtilitiesOwned() == 2) {
			return player.getLastRoll() * 10;
		} else {
			return player.getLastRoll() * 4;
		}
	}
	
	public Color getColor(){
		return color;
	}

	public int getPrice() {
		return PRICE;
	}

	public boolean isMortgaged() {
		return isMortgaged;
	}
	
	public void setMortgaged(Boolean mortgaged){
		isMortgaged = mortgaged;
	}

	public int getMortgagePrice() {
		return MORTGAGE;
	}

}
