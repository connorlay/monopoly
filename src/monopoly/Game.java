package monopoly;

import static monopoly.StdDraw.*;

import java.awt.Color;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdRandom;

public class Game {

	private int numberOfPlayers;
	private Player[] players;
	private Location[] location = new Location[40];

	private final Color MAGENTA = new Color(89, 12, 56);
	private final Color CYAN = new Color(135, 166, 213);
	private final Color PINK = new Color(239, 56, 120);
	private final Color ORANGE = new Color(245, 128, 35);
	private final Color RED = new Color(239, 58, 37);
	private final Color YELLOW = new Color(254, 231, 3);
	private final Color GREEN = new Color(19, 165, 90);
	private final Color BLUE = new Color(40, 78, 161);

	public void run() {
		createProperties();
		Monopoly.drawBoard("How many players? (2-6)", 0);
		while (true) {
			while (!hasNextKeyTyped()) {

			}
			numberOfPlayers = Character.getNumericValue(nextKeyTyped());
			if (numberOfPlayers > 1 && numberOfPlayers < 7) {
				break;
			}
		}
		players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			players[i] = new Player(i + 1);

		}
		Monopoly.drawBoard("", 0);
		while (true) {
			for (int i = 0; i < numberOfPlayers; i++) {
				playerTurn(players[i]);
			}

		}
	}

	public void playerTurn(Player player) {
		if (player.getMoney() < 0 && !player.isBankrupt()) {
			bankrupt(player);
		}
		if (!player.isBankrupt()) {
			if (player.getJail()) {
				Monopoly.drawBoard("Player " + player.getPlayerNumber() + " is in jail. Would you like to pay $50 to get out? (y/n)", 0);
				while (true) {
					clearTypedKeys();
					char response;
					while (!hasNextKeyTyped()) {
					}
					response = nextKeyTyped();
					if (response == 'y') {
						player.setJail(false);
						player.setMoney(-50);
						playerTurn(player);
						break;
					} else if (response == 'n') {
						diceRoll(player);
						if (player.getRolledDoubles()) {
							Monopoly.drawBoard("Player " + (player.getPlayerNumber()) + " rolled doubles.", 2000);
							player.setJail(false);
							playerTurn(player);
							break;
						} else {
							Monopoly.drawBoard("Player " + (player.getPlayerNumber()) + " did not roll doubles.", 2000);
							break;
						}
					}
				}
			} else {
				while (true) {
					while (!mousePressed()) {
						mouseOverProperties();
						Monopoly.drawBoard("Player " + player.getPlayerNumber() + "'s turn.", 0);
					}
					if (mouseX() > 25 && mouseX() < 245 && mouseY() > 20 && mouseY() < 140) {
						int roll = diceRoll(player);
						player.setLastRoll(roll);
						movePlayer(player, roll);
						if (location[player.getLocation()] instanceof Chance) {
							int temp = player.getLocation();
							location[player.getLocation()].landOn(player);
							if (temp != player.getLocation()) {
								location[player.getLocation()].landOn(player);
							}
						} else {
							location[player.getLocation()].landOn(player);
						}
						updateMonopolies(player);
						Monopoly.drawBoard("", 0);
						break;
					} else if (mouseX() > 275 && mouseX() < 495 && mouseY() > 170 && mouseY() < 290) {
						mortgageProperty(player);
					} else if (mouseX() > 25 && mouseX() < 245 && mouseY() > 170 && mouseY() < 290) {
						improveProperties(player);
					} else if (mouseX() > 275 && mouseX() < 495 && mouseY() > 20 && mouseY() < 140) {
						Monopoly.drawBoard("Who would you like to trade with? (Enter their number)", 0);
						int response;
						while (true) {
							clearTypedKeys();
							while (!hasNextKeyTyped()) {
							}
							response = Character.getNumericValue(nextKeyTyped());
							if (response != player.getPlayerNumber() && response > 0 && response <= numberOfPlayers && !players[response - 1].isBankrupt()) {
								break;
							}
						}
						int money1 = tradeMoney(player);
						ArrayList<Location> property1 = tradeProperties(player);
						int money2 = tradeMoney(players[response - 1]);
						ArrayList<Location> property2 = tradeProperties(players[response - 1]);
						trade(player, players[response - 1], money1, money2, property1, property2);
						updateMonopolies(player);
						updateMonopolies(players[response - 1]);
					}
					while (mousePressed()) {
					}
				}
			}
		}
	}

	public int diceRoll(Player player) {
		int roll1 = StdRandom.uniform(6);
		int roll2 = StdRandom.uniform(6);
		player.setRolledDoubles(roll1 == roll2);
		return roll1 + roll2 + 2;

	}

	public void movePlayer(Player player, int roll) {
		Monopoly.drawBoard("Player " + (player.getPlayerNumber()) + " rolled a " + roll + ".", 200);
		for (int i = 1; i <= roll; i++) {
			player.setLocation(player.getLocation() + 1);
			if (player.getLocation() > 39) {
				player.setLocation(0);
				player.setMoney(200);
			}
			Monopoly.drawBoard("Player " + (player.getPlayerNumber()) + " rolled a " + roll + ".", 150);
		}
	}

	public void mortgageProperty(Player player) {

		for (int i = 0; i < location.length; i++) {
			clearTypedKeys();
			Location currentLocation = location[i];
			if (currentLocation instanceof Property && ((Property) currentLocation).getOwner() == player) {
				Property current = (Property) currentLocation;
				Monopoly.showLocationDeed(current);
				Monopoly.drawBoard("", 0);
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				if (!current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is unmortgaged. Would you like to mortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							if (current.getImprovementLevel() == 0) {
								current.setMortgaged(true);
								player.setMoney(current.getMortgagePrice());
								Monopoly.drawBoard(current.getName() + " is now mortgaged.", 2000);
							} else {
								Monopoly.drawBoard(current.getName() + " has improvements. Sell all improvements before mortgaging.", 2000);
							}
							break;
						} else if (response == 'n') {
							break;
						}
					}
				} else if (current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is mortgaged. Would you like to unmortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							current.setMortgaged(false);
							player.setMoney(-current.getMortgagePrice());
							Monopoly.drawBoard(current.getName() + " is now unmortgaged.", 2000);
							break;
						} else if (response == 'n') {
							break;
						}
					}
				}
			} else if (currentLocation instanceof Railroad && ((Railroad) currentLocation).getOwner() == player) {
				Railroad current = (Railroad) currentLocation;
				Monopoly.showLocationDeed(current);
				Monopoly.drawBoard("", 0);
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				if (!current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is unmortgaged. Would you like to mortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							current.setMortgaged(true);
							player.setMoney(current.getMortgagePrice());
							Monopoly.drawBoard(current.getName() + " is now mortgaged.", 2000);
							break;
						} else if (response == 'n') {
							break;
						}
					}
				} else if (current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is mortgaged. Would you like to unmortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							current.setMortgaged(false);
							player.setMoney(-current.getMortgagePrice());
							Monopoly.drawBoard(current.getName() + " is now unmortgaged.", 2000);
							break;
						} else if (response == 'n') {
							break;
						}
					}
				}
			} else if (currentLocation instanceof Utility && ((Utility) currentLocation).getOwner() == player) {
				Utility current = (Utility) currentLocation;
				Monopoly.showLocationDeed(current);
				Monopoly.drawBoard("", 0);
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				if (!current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is unmortgaged. Would you like to mortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							current.setMortgaged(true);
							player.setMoney(current.getMortgagePrice());
							Monopoly.drawBoard(current.getName() + " is now mortgaged.", 2000);
							break;
						} else if (response == 'n') {
							break;
						}
					}
				} else if (current.isMortgaged()) {
					Monopoly.drawBoard(current.getName() + " is mortgaged. Would you like to unmortgage it for " + current.getMortgagePrice() + "? (y/n)", 0);
					while (true) {
						while (!hasNextKeyTyped()) {
						}
						char response = nextKeyTyped();
						if (response == 'y') {
							current.setMortgaged(false);
							player.setMoney(-current.getMortgagePrice());
							Monopoly.drawBoard(current.getName() + " is now unmortgaged.", 2000);
							break;
						} else if (response == 'n') {
							break;
						}
					}
				}
			}
		}
	}

	public void improveProperties(Player player) {
		// checks each color group player controls and creates a corresponding
		// list of properties
		for (Color color : player.getColorGroups()) {
			ArrayList<Property> properties = new ArrayList<Property>();
			for (Location current : location) {
				if (current instanceof Property && ((Property) current).getColor() == color) {
					properties.add((Property) current);
				}
			}
			// traverses the list of properties in a color group and asks the
			// owner if they would like to build another house
			for (Property property : properties) {
				if (property.getImprovementLevel() < 4) {
					Monopoly.drawBoard(property.getName() + " has " + property.getImprovementLevel() + " houses. Would you like to build one? (y/n)", 0);
					while (true) {
						clearTypedKeys();
						char response;
						while (!hasNextKeyTyped()) {
						}
						response = nextKeyTyped();
						if (response == 'y') {
							if (canImprove(property, properties)) {
								property.improve();
								player.setMoney(-property.getImprovementCost());
								Monopoly.drawBoard(
										"You built a house on " + property.getName() + ". " + property.getName() + " now has " + property.getImprovementLevel()
												+ " house(s).", 2000);
								break;
							} else {
								Monopoly.drawBoard("You cannot build a house at this time.", 2000);
								break;
							}
						} else if (response == 'n') {
							if (property.getImprovementLevel() > 0) {
								Monopoly.drawBoard("Would you like to sell a house? (y/n)", 0);
								while (true) {
									clearTypedKeys();
									while (!hasNextKeyTyped()) {
									}
									response = nextKeyTyped();
									if (response == 'y') {
										if (canUnimprove(property, properties)) {
											property.unImprove();
											player.setMoney(-property.getImprovementCost());
											Monopoly.drawBoard("You sold a house from " + property.getName() + ". " + property.getName() + " now has "
													+ property.getImprovementLevel() + " house(s).", 2000);
										} else {
											Monopoly.drawBoard("You cannot sell a house at this time.", 2000);
										}
										break;
									} else if (response == 'n') {
										break;
									}
								}
							}
							break;
						}
					}
				} else if (property.getImprovementLevel() == 4) {
					Monopoly.drawBoard(property.getName() + " has " + property.getImprovementLevel() + " houses. Would you like to build a hotel for $"
							+ property.getImprovementCost() + "? (y/n)", 0);
					while (true) {
						clearTypedKeys();
						char response;
						while (!hasNextKeyTyped()) {
						}
						response = nextKeyTyped();
						if (response == 'y') {
							if (canImprove(property, properties)) {
								property.improve();
								player.setMoney(-property.getImprovementCost());
								Monopoly.drawBoard("You built a hotel on " + property.getName() + ".", 2000);
								break;
							} else {
								Monopoly.drawBoard("You cannot build a hotel at this time.", 2000);
								break;
							}
						} else if (response == 'n') {
							break;
						}
					}
				}
			}
		}
	}

	public boolean canImprove(Property property, ArrayList<Property> group) {
		Boolean canImprove = true;
		for (Property current : group) {
			if (!(current.getImprovementLevel() >= property.getImprovementLevel())) {
				canImprove = false;
			}
		}
		return canImprove;
	}

	public boolean canUnimprove(Property property, ArrayList<Property> group) {
		Boolean canUnimprove = true;
		if (property.getImprovementLevel() == 0) {
			return false;
		}
		for (Property current : group) {
			if (!(current.getImprovementLevel() <= property.getImprovementLevel())) {
				canUnimprove = false;
			}
		}
		return canUnimprove;
	}

	public void createProperties() {

		location[1] = new Property("Mediterranian Avenue", MAGENTA, 60, 50, 2, 10, 30, 90, 160, 250);
		location[3] = new Property("Baltic Avenue", MAGENTA, 60, 50, 4, 20, 60, 180, 320, 450);

		location[6] = new Property("Oriental Avenue", CYAN, 100, 50, 6, 30, 90, 270, 400, 550);
		location[8] = new Property("Vermont Avenue", CYAN, 100, 50, 6, 30, 90, 270, 400, 550);
		location[9] = new Property("Connecticut Avenue", CYAN, 120, 50, 8, 40, 100, 300, 450, 600);

		location[11] = new Property("St Charles Place", PINK, 140, 100, 10, 50, 150, 450, 625, 750);
		location[13] = new Property("States Avenue", PINK, 140, 100, 10, 50, 150, 450, 625, 750);
		location[14] = new Property("Virginia Avenue", PINK, 160, 100, 12, 60, 180, 500, 700, 900);

		location[16] = new Property("St James Place", ORANGE, 180, 100, 14, 70, 200, 550, 750, 950);
		location[18] = new Property("Tennessee Avenue", ORANGE, 180, 100, 14, 70, 200, 550, 750, 950);
		location[19] = new Property("New York Avenue", ORANGE, 200, 100, 16, 80, 220, 600, 800, 1000);

		location[21] = new Property("Kentucky Avenue", RED, 220, 150, 18, 90, 250, 700, 875, 1050);
		location[23] = new Property("Indiana Avenue", RED, 220, 150, 18, 90, 250, 700, 875, 1050);
		location[24] = new Property("Illinois Avenue", RED, 240, 150, 20, 100, 300, 750, 925, 1100);

		location[26] = new Property("Atlantic Avenue", YELLOW, 260, 150, 22, 110, 330, 800, 975, 1150);
		location[27] = new Property("Ventnor Avenue", YELLOW, 260, 150, 22, 110, 330, 800, 975, 1150);
		location[29] = new Property("Marvin Gardens", YELLOW, 280, 150, 24, 120, 360, 850, 1025, 1200);

		location[31] = new Property("Pacific Avenue", GREEN, 300, 200, 26, 130, 390, 900, 1100, 1275);
		location[32] = new Property("North Carolina Avenue", GREEN, 300, 200, 26, 130, 390, 900, 1100, 1275);
		location[34] = new Property("Pennsylvania Avenue", GREEN, 320, 200, 28, 150, 450, 1000, 1200, 1400);

		location[37] = new Property("Park Place", BLUE, 350, 200, 35, 175, 500, 1100, 1300, 1500);
		location[39] = new Property("Boardwalk", BLUE, 400, 200, 50, 200, 600, 1400, 1700, 2000);

		location[0] = new Corner(1);
		location[10] = new Corner(2);
		location[20] = new Corner(3);
		location[30] = new Corner(4);

		location[2] = new Chance(false, players);
		location[7] = new Chance(true, players);
		location[17] = new Chance(false, players);
		location[22] = new Chance(true, players);
		location[33] = new Chance(false, players);
		location[36] = new Chance(true, players);

		location[4] = new Taxation(false);
		location[38] = new Taxation(true);

		location[5] = new Railroad("Reading Railroad");
		location[15] = new Railroad("Pennsylvania Railroad");
		location[25] = new Railroad("B & O Railroad");
		location[35] = new Railroad("Short Line");

		location[12] = new Utility("Electric Company", GRAY);
		location[28] = new Utility("Water Works", GRAY);
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Location[] getLocations() {
		return location;
	}

	public void mouseOverProperties() {
		// bottom row
		int widthBottom = 53;
		int heightBottom = 80;
		int startBottom = 1139;
		for (int i = 0; i < 10; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseX() > startBottom - (i * widthBottom) && mouseX() < startBottom - (i - 1) * widthBottom && mouseY() > 0 && mouseY() < heightBottom) {
					Monopoly.showLocationDeed(location[i]);
					Monopoly.drawBoard("", 0);
				}
			}
		}
		// top row
		int widthTop = 53;
		int heightTop = 90;
		int startTop = 662;
		for (int i = 20; i < 30; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseX() > startTop + ((i - 21) * widthTop) && mouseX() < startTop + (i - 20) * widthTop && mouseY() > 520 && mouseY() < 520 + heightTop) {
					Monopoly.showLocationDeed(location[i]);
					Monopoly.drawBoard("", 0);
				}
			}
		}
		// left column
		int widthLeft = 90;
		int heightLeft = 50;
		int startLeft = 570;
		for (int i = 10; i < 20; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseY() > startLeft + ((i - 21) * heightLeft) && mouseY() < startLeft + (i - 20) * heightLeft && mouseX() > 562
						&& mouseX() < 562 + widthLeft) {
					Monopoly.showLocationDeed(location[i]);
					Monopoly.drawBoard("", 0);
				}
			}
		}
		// left column
		int widthRight = 90;
		int heightRight = 50;
		int startRight = 525;
		for (int i = 30; i < 40; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseY() > startRight - ((i - 30) * heightRight) && mouseY() < startRight - (i - 31) * heightRight && mouseX() > 1140
						&& mouseX() < 1140 + widthRight) {
					Monopoly.showLocationDeed(location[i]);
					Monopoly.drawBoard("", 0);
				}
			}
		}
	}

	public Location clickOverProperties() {
		// bottom row
		int widthBottom = 53;
		int heightBottom = 80;
		int startBottom = 1139;
		for (int i = 0; i < 10; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseX() > startBottom - (i * widthBottom) && mouseX() < startBottom - (i - 1) * widthBottom && mouseY() > 0 && mouseY() < heightBottom) {
					if (mousePressed()) {
						return location[i];
					}
				}
			}
		}
		// top row
		int widthTop = 53;
		int heightTop = 90;
		int startTop = 662;
		for (int i = 20; i < 30; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseX() > startTop + ((i - 21) * widthTop) && mouseX() < startTop + (i - 20) * widthTop && mouseY() > 520 && mouseY() < 520 + heightTop) {
					if (mousePressed()) {
						return location[i];
					}
				}
			}
		}
		// left column
		int widthLeft = 90;
		int heightLeft = 50;
		int startLeft = 570;
		for (int i = 10; i < 20; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseY() > startLeft + ((i - 21) * heightLeft) && mouseY() < startLeft + (i - 20) * heightLeft && mouseX() > 562
						&& mouseX() < 562 + widthLeft) {
					if (mousePressed()) {
						return location[i];
					}
				}
			}
		}
		// left column
		int widthRight = 90;
		int heightRight = 50;
		int startRight = 525;
		for (int i = 30; i < 40; i++) {
			if (location[i] instanceof Property || location[i] instanceof Railroad || location[i] instanceof Utility) {
				while (mouseY() > startRight - ((i - 30) * heightRight) && mouseY() < startRight - (i - 31) * heightRight && mouseX() > 1140
						&& mouseX() < 1140 + widthRight) {
					if (mousePressed()) {
						return location[i];
					}
				}
			}
		}
		return null;
	}

	public ArrayList<Location> tradeProperties(Player player) {
		ArrayList<Location> trade = new ArrayList<Location>();
		while (true) {
			Monopoly.drawBoard("Player " + player.getPlayerNumber() + "'s offer.", 2000);
			Monopoly.drawBoard("Click on the properties you would like to offer.", 0);
			Location clicked = null;
			while (clicked == null) {
				clicked = clickOverProperties();
			}
			if ((clicked instanceof Property && ((Property) clicked).getOwner() != player)
					|| (clicked instanceof Railroad && ((Railroad) clicked).getOwner() != player)
					|| (clicked instanceof Utility && ((Utility) clicked).getOwner() != player)) {
				Monopoly.drawBoard("You don't own " + clicked.getName() + ". Would you like to add anything else? (y/n)", 0);
				while (true) {
					clearTypedKeys();
					while (!hasNextKeyTyped()) {
					}
					char response = nextKeyTyped();
					if (response == 'y') {
						break;
					} else if (response == 'n') {
						return trade;
					}
				}
			} else if (!trade.contains(clicked)) {
				trade.add(clicked);
				Monopoly.drawBoard(clicked.getName() + " is in your offer. Would you like to add anything else? (y/n)", 0);
				while (true) {
					clearTypedKeys();
					while (!hasNextKeyTyped()) {
					}
					char response = nextKeyTyped();
					if (response == 'y') {
						break;
					} else if (response == 'n') {
						return trade;
					}
				}
			} else {
				Monopoly.drawBoard(clicked.getName() + " is already in your offer. Would you like to add anything else? (y/n)", 0);
				while (true) {
					clearTypedKeys();
					while (!hasNextKeyTyped()) {
					}
					char response = nextKeyTyped();
					if (response == 'y') {
						break;
					} else if (response == 'n') {
						return trade;
					}
				}
			}
		}
	}

	public int tradeMoney(Player player) {
		Monopoly.drawBoard("Player " + player.getPlayerNumber() + " how much would you like to offer?", 0);
		while (!hasNextKeyTyped()) {
		}
		ArrayList<Integer> money = new ArrayList<Integer>();
		String s = "";
		while (true) {
			clearTypedKeys();
			char response;
			for (Integer i : money) {
				s += i;
			}
			Monopoly.drawBoard(s, 0);
			while (!hasNextKeyTyped()) {
			}
			response = nextKeyTyped();
			if (response == 8 && money.size() > 1) {
				money.remove(money.size() - 1);
			} else if (response >= 48 && response <= 57) {
				money.add(Character.getNumericValue(response));
			} else if (response == 10) {
				System.out.println(Integer.parseInt(s));
				return Integer.parseInt(s);
			}
			s = "";
		}
	}

	public void trade(Player player1, Player player2, int money1, int money2, ArrayList<Location> property1, ArrayList<Location> property2) {
		String s1 = "";
		for (Location current : property1) {
			s1 += ", " + current.getName();
		}
		String s2 = "";
		for (Location current : property2) {
			s2 += ", " + current.getName();
		}
		Monopoly.drawBoard("Player " + player1.getPlayerNumber() + " is trading $" + money1 + s1 + ". (y/n)", 0);
		char response;
		while (true) {
			clearTypedKeys();
			while (!hasNextKeyTyped()) {
			}
			response = nextKeyTyped();
			if (response == 'y' || response == 'n') {
				break;
			}
		}
		if (response == 'y') {
			Monopoly.drawBoard("Player " + player2.getPlayerNumber() + " is trading $" + money2 + s2 + ". (y/n)", 0);
			while (true) {
				clearTypedKeys();
				while (!hasNextKeyTyped()) {
				}
				response = nextKeyTyped();
				if (response == 'y' || response == 'n') {
					break;
				}
			}
			if (response == 'y') {
				for (Location current : property1) {
					if (current instanceof Property) {
						((Property) current).setOwner(player2);
					} else if (current instanceof Utility) {
						((Utility) current).setOwner(player2);
					} else if (current instanceof Railroad) {
						((Railroad) current).setOwner(player2);
					}
				}
				player1.setMoney(-money1);
				player2.setMoney(money1);
				for (Location current : property2) {
					if (current instanceof Property) {
						((Property) current).setOwner(player1);
					} else if (current instanceof Utility) {
						((Utility) current).setOwner(player1);
					} else if (current instanceof Railroad) {
						((Railroad) current).setOwner(player1);
					}
				}
				player1.setMoney(money2);
				player2.setMoney(-money2);
			}
		}

	}

	public void setPlayersInChance() {
		((Chance) location[2]).setPlayers(players);
		((Chance) location[7]).setPlayers(players);
		((Chance) location[17]).setPlayers(players);
		((Chance) location[22]).setPlayers(players);
		((Chance) location[33]).setPlayers(players);
		((Chance) location[36]).setPlayers(players);
	}

	public void updateMonopolies(Player player) {
		if (((Property) location[1]).getOwner() == player && ((Property) location[3]).getOwner() == player) {
			player.addColorGroup(MAGENTA);
		} else {
			player.removeColorGroup(MAGENTA);
		}
		if (((Property) location[6]).getOwner() == player && ((Property) location[8]).getOwner() == player && ((Property) location[9]).getOwner() == player) {
			player.addColorGroup(CYAN);
		} else {
			player.removeColorGroup(CYAN);
		}
		if (((Property) location[11]).getOwner() == player && ((Property) location[13]).getOwner() == player && ((Property) location[14]).getOwner() == player) {
			player.addColorGroup(PINK);
		} else {
			player.removeColorGroup(PINK);
		}
		if (((Property) location[16]).getOwner() == player && ((Property) location[18]).getOwner() == player && ((Property) location[19]).getOwner() == player) {
			player.addColorGroup(ORANGE);
		} else {
			player.removeColorGroup(ORANGE);
		}
		if (((Property) location[21]).getOwner() == player && ((Property) location[23]).getOwner() == player && ((Property) location[24]).getOwner() == player) {
			player.addColorGroup(RED);
		} else {
			player.removeColorGroup(RED);
		}
		if (((Property) location[26]).getOwner() == player && ((Property) location[27]).getOwner() == player && ((Property) location[29]).getOwner() == player) {
			player.addColorGroup(YELLOW);
		} else {
			player.removeColorGroup(YELLOW);
		}
		if (((Property) location[31]).getOwner() == player && ((Property) location[32]).getOwner() == player && ((Property) location[34]).getOwner() == player) {
			player.addColorGroup(GREEN);
		} else {
			player.removeColorGroup(GREEN);
		}
		if (((Property) location[37]).getOwner() == player && ((Property) location[39]).getOwner() == player) {
			player.addColorGroup(BLUE);
		} else {
			player.removeColorGroup(BLUE);
		}
	}

	public void bankrupt(Player player) {
		Monopoly.drawBoard("Player " + player.getPlayerNumber() + " is facing bankruptcy. Do you want to trade or mortgage? (y/n)", 0);
		while (true) {
			clearTypedKeys();
			char response;
			while (!hasNextKeyTyped()) {
			}
			response = nextKeyTyped();
			if (response == 'y') {
				while (true) {
					Monopoly.drawBoard("You can trade, mortgage, or improve.", 0);
					while (!mousePressed()) {
					}
					if (mouseX() > 275 && mouseX() < 495 && mouseY() > 170 && mouseY() < 290) {
						mortgageProperty(player);
					} else if (mouseX() > 25 && mouseX() < 245 && mouseY() > 170 && mouseY() < 290) {
						improveProperties(player);
					} else if (mouseX() > 275 && mouseX() < 495 && mouseY() > 20 && mouseY() < 140) {
						Monopoly.drawBoard("Who would you like to trade with? (Enter their number)", 0);
						int number;
						while (true) {
							clearTypedKeys();
							while (!hasNextKeyTyped()) {
							}
							number = Character.getNumericValue(nextKeyTyped());
							if (number != player.getPlayerNumber() && number > 0 && number <= numberOfPlayers) {
								break;
							}
						}
						int money1 = tradeMoney(player);
						ArrayList<Location> property1 = tradeProperties(player);
						int money2 = tradeMoney(players[number - 1]);
						ArrayList<Location> property2 = tradeProperties(players[number - 1]);
						trade(player, players[number - 1], money1, money2, property1, property2);
						updateMonopolies(player);
						updateMonopolies(players[number - 1]);
					}
					while (mousePressed()) {
					}
					Monopoly.drawBoard("Would you like to do anything else? (y/n)", 0);
					while (true) {
						clearTypedKeys();
						char response2;
						while (!hasNextKeyTyped()) {
						}
						response2 = nextKeyTyped();
						if (response2 == 'y') {
							break;
						} else if (response2 == 'n') {
							player.setBankrupt(true);
							for (Location current : location) {
								if (current instanceof Property && ((Property) current).getOwner() == player) {
									((Property) current).setOwner(null);
									((Property) current).setImprovementLevel(0);
								} else if (current instanceof Utility && ((Utility) current).getOwner() == player) {
									((Utility) current).setOwner(null);
								} else if (current instanceof Railroad && ((Railroad) current).getOwner() == player) {
									((Railroad) current).setOwner(null);
								}
							}
							break;
						}
					}
					if (player.isBankrupt()) {
						break;
					}
				}
			} else if (response == 'n') {
				player.setBankrupt(true);
				for (Location current : location) {
					if (current instanceof Property && ((Property) current).getOwner() == player) {
						((Property) current).setOwner(null);
						((Property) current).setImprovementLevel(0);
					} else if (current instanceof Utility && ((Utility) current).getOwner() == player) {
						((Utility) current).setOwner(null);
					} else if (current instanceof Railroad && ((Railroad) current).getOwner() == player) {
						((Railroad) current).setOwner(null);
					}
				}
				break;
			}
		}
	}
}
