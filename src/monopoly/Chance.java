package monopoly;

public class Chance implements Location {

	//boolean that determines which card to draw, chance or community chest
	private Boolean isChance;
	private Player[] players;
	
	public Chance(Boolean isChance, Player[] players){
		this.isChance = isChance;
		this.players = players;
	}
	
	@Override
	public String getName() {
		if (isChance){
			return "Chance";
		}
		return "Community Chest";
	}
	
	public void setPlayers(Player[] players){
		this.players = players;
	}

	@Override
	public void landOn(Player player) {
		int draw;
		if (isChance){
			draw = ChanceDeck.draw(0);
			Monopoly.drawBoard("You landed on Chance.", 2000);
			if (draw==0){
				Monopoly.drawBoard("Advance to Go and collect $200.", 2000);
				player.setLocation(0);
				player.setMoney(200);
			} else if (draw==1){
				Monopoly.drawBoard("Go back three spaces.", 2000);
				player.setLocation(player.getLocation()-3);
				
			} else if (draw==2){
				Monopoly.drawBoard("Take a ride on the Reading. If you past Go, collect $200.", 2000);
				if (player.getLocation()>5){
					player.setMoney(200);
				} 
				player.setLocation(5);
			} else if (draw==3){
				Monopoly.drawBoard("Advance to St Charles Place. If you pass Go, collect $200.", 2000);
				if (player.getLocation()>11){
					player.setMoney(200);
				} 
				player.setLocation(11);
			} else if (draw==4) {
				//TODO implement property improvements, then add this feature
				Monopoly.drawBoard("Make general repairs on all your property. For each house pay $25 and for each hotel $100.", 2000);
			} else if (draw==5){
				Monopoly.drawBoard("Bank pays you dividend of $50.", 2000);
				player.setMoney(50);
			} else if (draw==6) {
				Monopoly.drawBoard("Pay poor tax of $15.", 2000);
				player.setMoney(-15);
			} else if (draw==7){
				//TODO implement spending time in jail
				Monopoly.drawBoard("Go directly to Jail. Do not pass Go. Do not collect $200.", 2000);
			} else if (draw==8 || draw==12){
				Monopoly.drawBoard("Advance token to the nearest Railroad. Pay the owner twice the rent.", 2000);
				if (player.getLocation() < 5){
					player.setLocation(5);	
				} else if (player.getLocation() < 15){
					player.setLocation(15);
				} else if (player.getLocation() < 25){
					player.setLocation(25);
				} else if (player.getLocation() < 35){
					player.setLocation(35);
				}		
			} else if (draw==9){
				Monopoly.drawBoard("Take a walk on the Board Walk. Advance to Board Walk.", 2000);
				player.setLocation(39);
			} else if (draw==10){
				//TODO implement this one
				Monopoly.drawBoard("You have been elected chairman of the board. Pay each player $50.", 2000);
			} else if (draw==11){
				Monopoly.drawBoard("Advance token to the nearest Utility. Pay ten times the dice thrown to the owner.", 2000);
				if (player.getLocation()<12){
					player.setLocation(12);
				} else if (player.getLocation()<28){
					player.setLocation(28);
				} else if (player.getLocation()>28){
					player.setLocation(12);
					player.setMoney(200);
				}
			} else if (draw==13){
				Monopoly.drawBoard("Advance to Illinois Avenue. If you past Go, collect $200.", 2000);
				if (player.getLocation()>24){
					player.setMoney(200);
				}
				player.setLocation(24);
			} else if (draw==14){
				Monopoly.drawBoard("Your building and loan matures. Collect $150.", 2000);
				player.setMoney(150);
			} else if (draw==15){
				//TODO implement jail
				Monopoly.drawBoard("Get out of Jail free. This card may be kept until needed or sold.", 2000);
			}
			
			
		} else {
			draw = ChanceDeck.draw(1);
			Monopoly.drawBoard("You landed on Community Chest.", 2000);
			if (draw==0){
				Monopoly.drawBoard("Xmas fund matures. Collect $100.", 2000);
				player.setMoney(100);
			} else if (draw==1){
				Monopoly.drawBoard("Doctor's fee. Pay $50.", 2000);
				player.setMoney(-50);
			} else if (draw==2){
				//TODO needs to be implemented
				Monopoly.drawBoard("Grand opera opening. Collect $50 from every player.", 2000);
			} else if (draw==3){
				Monopoly.drawBoard("You inheret $100.", 2000);
				player.setMoney(100);
			} else if (draw==4){
				player.setJailCard(true);
				Monopoly.drawBoard("Get out of Jail free. This card may be kept until needed or sold.", 2000);
			} else if (draw==5){
				Monopoly.drawBoard("Income tax refund. Collect $20.", 2000);
				player.setMoney(20);
			} else if (draw==6){
				Monopoly.drawBoard("From sale of stock you get $45.", 2000);
				player.setMoney(45);
			} else if (draw==7){
				player.setMoney(25);
				Monopoly.drawBoard("Recieve for services $25.", 2000);
			} else if (draw==8){
				//TODO implement this one
				Monopoly.drawBoard("You are assessed for street repairs. $40 for each house. $115 for each hotel.", 2000);
			} else if (draw==9){
				Monopoly.drawBoard("Bank error in your favor. Collect $200.", 2000);
				player.setMoney(200);
			} else if (draw==10){
				Monopoly.drawBoard("Pay school tax of $150.", 2000);
				player.setMoney(-150);
			} else if (draw==11){
				Monopoly.drawBoard("Life insurance matures. Collect $100", 2000);
				player.setMoney(100);
			} else if (draw==12){
				Monopoly.drawBoard("Pay hospital $100.", 2000);
				player.setMoney(-100);
			} else if (draw==13){
				Monopoly.drawBoard("Advance to Go and collect $200.", 2000);
				player.setLocation(0);
				player.setMoney(200);
			} else if (draw==14){
				//TODO implement spending time in jail
				Monopoly.drawBoard("Go directly to Jail. Do not pass Go. Do not collect $200.", 2000);
			} else if (draw==15){
				Monopoly.drawBoard("You have won second prize in a beauty contest. Collect $10.", 2000);
				player.setMoney(10);
			}
		}
		
	}

}
