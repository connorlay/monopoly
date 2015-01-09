package monopoly;

import static monopoly.StdDraw.*;

public class Railroad implements Location {

	private String name;
	private Player owner;
	private Boolean isMortgaged;
	private final int PRICE = 200;
	private final int MORTGAGE = 100;
	

	public Railroad(String name) {
		this.name = name;
		isMortgaged = false;
	}

	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return PRICE;
	}

	public int getMortgagePrice() {
		return MORTGAGE;
	}

	public int getRent(){
		if (owner.getNumRailroadsOwned() == 1){
			return 25;
		}else if (owner.getNumRailroadsOwned() == 2){
			return 50;
		}else if (owner.getNumRailroadsOwned() == 3){
			return 100;
		}else if (owner.getNumRailroadsOwned() == 4){
			return 200;
		}else{
			return 0;
		}
		
	}

	public Player getOwner(){
		return owner;
	}
	
	public void setOwner(Player player) {
		this.owner = player;   
	}

	public boolean isMortgaged(){
		return isMortgaged;
	}
	
	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}
	
	public void landOn(Player player) {
		Monopoly.showLocationDeed(this);
		Monopoly.drawBoard("", 0);
		clearTypedKeys();
		while (!hasNextKeyTyped()) {
		}
		if (owner == null) {
			Monopoly.drawBoard(name + " is unowned. \n Would you like to purchase it for $" + PRICE + "? (y/n)", 0);
			while (true) {
				while (!hasNextKeyTyped()) {
				}
				char response = nextKeyTyped();
				if (response == 'y' || response == 'n') {
					if (response == 'y') {
						player.setMoney(-PRICE);
						setOwner(player);
						player.changeNumRailroadsOwned(1);
						break;
					} else if (response == 'n') {
						break;
					}
				}
			}
		}  else if (!isMortgaged) {
			Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". You owe them $" + getRent(), 2000);
			player.setMoney(-getRent());
			owner.setMoney(getRent());
		} else {
			Monopoly.drawBoard(name + " is owned by Player " + owner.getPlayerNumber() + ". It is currently mortgaged.", 2000);
		}
	}
}
