package monopoly;

public class Taxation implements Location {

	private Boolean isLuxury;
	
	public Taxation(Boolean isLuxury){
		this.isLuxury = isLuxury;
	}
	
	@Override
	public String getName() {
		if (isLuxury){
			return "Luxury Tax";
		} else {
			return "Income Tax";
		}
	}

	@Override
	public void landOn(Player player) {
		// TODO Auto-generated method stub
		if (isLuxury){
			Monopoly.drawBoard("You landed on Luxury Tax. Pay $75.", 2000);
			player.setMoney(-75);
		} else {
			Monopoly.drawBoard("You landed on Income Tax. Pay %10 or $200.", 2000);
			if ((player.getMoney()*0.1) < 200){
				player.setMoney((int) -(player.getMoney()*0.1));
			} else {
				player.setMoney(200);
			}
		}
	}

}
