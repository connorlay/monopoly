package monopoly;

public class Corner implements Location {

	//int that determines which corner location each instance is. 1 is GO, 2 is Jail, 3 is Free Parking, 4 is Go to Jail
	private int id;

	public Corner(int id){
		
		this.id = id;
	}
	
	@Override
	public String getName() {
		if (id == 1){
			return "GO";
		}
		if (id == 2){
			return "Jail";
		}
		if (id == 3){
			return "Free Parking";
		}
		if (id == 4){
			return "Go to Jail";
		}
		return "";
	}

	@Override
	public void landOn(Player player) {
		if (id == 2){
			Monopoly.drawBoard("You landed on " + getName() + ", but you are just visiting.", 2000);
		} else if (id == 3){
			Monopoly.drawBoard("You landed on " + getName() + ".", 2000);
		} else if (id == 4){
			Monopoly.drawBoard("Go to Jail.", 2000);
			player.setLocation(10);
			player.setJail(true);
		}
	}
	
}
