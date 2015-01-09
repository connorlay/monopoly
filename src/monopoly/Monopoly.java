package monopoly;

import static monopoly.StdDraw.*;

import java.awt.Color;

public class Monopoly {

	/**
	 * @param args
	 */
	public static Game game;
	public static Boolean showDeed;
	public static Location deed;

	public static void main(String[] args) {
		setCanvasSize(1200, 650);
		setXscale(0, 1200);
		setYscale(0, 600);
		game = new Game();
		showDeed = false;
		game.run();

	}

	public static void drawBoard(String text, int length) {
		clear();

		// Board Picture
		picture(900, 300, "monopolyBoard.jpg");

		// Draw Text
		if (!showDeed) {
			drawText(text);
		}

		// Draw Title Deed
		if (showDeed) {
			if (deed instanceof Property) {
				drawTitleDeed((Property) deed);
			} else if (deed instanceof Utility) {
				drawTitleDeed((Utility) deed);
			} else if (deed instanceof Railroad) {
				drawTitleDeed((Railroad) deed);
			}
			showDeed = false;
		}

		// Menu lines
		line(0, 600, 530, 600);
		line(0, 600, 0, 350);
		line(0, 350, 530, 350);
		line(530, 350, 530, 600);

		// Menu Dividers
		setPenRadius(.006);
		line(0, 350 + (250 / 7) * 6, 530, 350 + (250 / 7) * 6);
		setPenRadius(.002);
		for (int i = 1; i < 6; i++) {
			line(0, 350 + (250 / 7) * i, 530, 350 + (250 / 7) * i);
		}

		// Menu Text
		textLeft(10, 367 + (250 / 7) * 6, "Players");
		textLeft(100, 367 + (250 / 7) * 6, "Money");
		setPenColor(BLUE);
		textLeft(210, 367 + (250 / 7) * 6, "Properties");

		setPenColor(BLACK);
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (!game.getPlayers()[i].isBankrupt()) {
				setPenColor(game.getPlayers()[i].getColor());
				textLeft(10, 367 + (250 / 7) * (5 - i), "Player " + game.getPlayers()[i].getPlayerNumber());
				setPenColor(BLACK);
				textLeft(100, 367 + (250 / 7) * (5 - i), "$" + game.getPlayers()[i].getMoney());
				drawProperties(game.getPlayers()[i], game.getLocations());
			}
		}

		// Button rectangles
		rectangle(135, 80, 110, 60);
		rectangle(385, 80, 110, 60);
		rectangle(135, 230, 110, 60);
		rectangle(385, 230, 110, 60);

		// Button text
		text(135, 80, "Roll");
		text(385, 80, "Trade");
		text(135, 230, "Improve");
		text(385, 230, "Mortgage");

		// Player Locations
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			drawPlayers(game.getPlayers()[i]);
		}

		// Improvements
		drawImprovements();

		show(length);
	}

	public static void drawPlayers(Player player) {
		if (player.getLocation() == 0) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 1170, (player.getPlayerNumber() * 15));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 1190, ((player.getPlayerNumber() - 3) * 15));
			}
		} else if (player.getLocation() < 10) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 1154 - (player.getLocation() * 53), (player.getPlayerNumber() * 15));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 1174 - (player.getLocation() * 53), ((player.getPlayerNumber() - 3) * 15));
			}
		} else if (player.getLocation() == 10) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 582, (player.getPlayerNumber() * 15));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 590 + ((player.getPlayerNumber() - 3) * 15), 8);
			}
		} else if (player.getLocation() < 20) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 575 + ((player.getPlayerNumber()) * 15), 60 + ((player.getLocation() - 10) * 50));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 575 + ((player.getPlayerNumber() - 3) * 15), 40 + ((player.getLocation() - 10) * 50));
			}
		} else if (player.getLocation() == 20) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 620, 540 + (player.getPlayerNumber() * 15));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 600, 540 + ((player.getPlayerNumber() - 3) * 15));
			}
		} else if (player.getLocation() < 30) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 645 + ((player.getLocation() - 20) * 53), 540 + (player.getPlayerNumber() * 15));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 625 + ((player.getLocation() - 20) * 53), 540 + ((player.getPlayerNumber() - 3) * 15));
			}
		} else if (player.getLocation() == 30) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 1160 + ((player.getPlayerNumber()) * 15), 550);
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 1160 + ((player.getPlayerNumber() - 3) * 15), 570);
			}
		} else if (player.getLocation() < 40) {
			if (player.getPlayerNumber() < 4) {
				drawCircles(player.getColor(), 1160 + ((player.getPlayerNumber()) * 15), 535 - ((player.getLocation() - 30) * 49));
			} else if (player.getPlayerNumber() > 3) {
				drawCircles(player.getColor(), 1160 + ((player.getPlayerNumber() - 3) * 15), 555 - ((player.getLocation() - 30) * 49));
			}
		}

	}

	public static void drawCircles(Color color, int x, int y) {
		setPenColor(color);
		filledCircle(x, y, 8);
		setPenColor(BLACK);
		circle(x, y, 8);
	}

	public static void drawText(String text) {
		setPenColor(BLACK);
		String temp = "";
		int length = text.length();
		if (text.contains(". ")) {
			for (int i = 1; i < length; i++) {
				if (text.charAt(i) == '.') {
					temp = text.substring(i + 1);
					text = text.substring(0, i + 1);
					text(900, 320, text);
					text(900, 270, temp);
					break;
				}
			}
		} else {
			text(900, 300, text);
		}
	}

	public static void drawProperties(Player player, Location[] locations) {

		// draws the properties
		int properties = 0;
		Color temporary = ((Property) locations[1]).getColor();
		for (int i = 0; i < locations.length; i++) {
			if (locations[i] instanceof Property) {
				Property property = ((Property) locations[i]);
				if (property.getColor() != temporary) {
					temporary = property.getColor();
					properties++;
				}
				if (property.getOwner() == player) {
					setPenColor(temporary);
					filledSquare(210 + (properties * 11), 372 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
					setPenColor(BLACK);
				}
				square(210 + (properties * 11), 372 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
				temporary = property.getColor();
				properties++;
			}
		}

		// draws the railroad properties
		if (((Railroad) locations[5]).getOwner() == player) {
			filledSquare(331, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4.5);
		} else {
			square(331, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
		}
		if (((Railroad) locations[15]).getOwner() == player) {
			filledSquare(342, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4.5);
		} else {
			square(342, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
		}
		if (((Railroad) locations[25]).getOwner() == player) {
			filledSquare(353, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4.5);
		} else {
			square(353, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
		}
		if (((Railroad) locations[35]).getOwner() == player) {
			filledSquare(364, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4.5);
		} else {
			square(364, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
		}

		// draws the utility properties
		if (((Utility) locations[12]).getOwner() == player) {
			setPenColor(((Utility) locations[12]).getColor());
			filledSquare(386, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
			setPenColor(BLACK);
		}
		square(386, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
		if (((Utility) locations[28]).getOwner() == player) {
			setPenColor(((Utility) locations[28]).getColor());
			filledSquare(397, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
			setPenColor(BLACK);
		}
		square(397, 360 + (250 / 7) * (6 - player.getPlayerNumber()), 4);
	}

	public static void drawTitleDeed(Property property) {
		int textSpace = 30;
		setPenColor(property.getColor());
		filledRectangle(900, 455, 200, 25);
		setPenColor(WHITE);
		text(900, 455, property.getName());
		setPenColor(BLACK);
		text(900, 410, "Rent $" + property.getRent());
		textLeft(700, 410 - textSpace, "With 1 House:");
		textRight(1100, 410 - textSpace, "$" + property.getRentArray()[1]);
		textLeft(700, 410 - textSpace * 2, "With 2 Houses:");
		textRight(1100, 410 - textSpace * 2, "$" + property.getRentArray()[2]);
		textLeft(700, 410 - textSpace * 3, "With 3 Houses:");
		textRight(1100, 410 - textSpace * 3, "$" + property.getRentArray()[3]);
		textLeft(700, 410 - textSpace * 4, "With 4 Houses:");
		textRight(1100, 410 - textSpace * 4, "$" + property.getRentArray()[4]);
		text(900, 250, "With Hotel: $" + property.getRentArray()[5]);
		text(900, 250 - textSpace, "Mortgage Value: $" + property.getMortgagePrice());
		text(900, 250 - textSpace * 2, "Houses cost $" + property.getImprovementCost());
		text(900, 250 - textSpace * 3, "Hotels cost $" + property.getImprovementCost() + ", plus 4 Houses.");
	}

	public static void drawTitleDeed(Utility utility) {
		int textSpace = 30;
		setPenColor(utility.getColor());
		filledRectangle(900, 455, 200, 25);
		setPenColor(WHITE);
		text(900, 455, utility.getName());
		setPenColor(BLACK);
		textLeft(700, 400, "1 Utility Owned:");
		textRight(1100, 400, "$ 4X Dice roll");
		textLeft(700, 400 - textSpace, "2 Utilities Owned:");
		textRight(1100, 400 - textSpace, "$ 10X Dice roll");
		text(900, 250 - textSpace, "Mortgage Value: $" + utility.getMortgagePrice());
	}

	public static void drawTitleDeed(Railroad railroad) {
		int textSpace = 30;
		setPenColor(BLACK);
		filledRectangle(900, 455, 200, 25);
		setPenColor(WHITE);
		text(900, 455, railroad.getName());
		setPenColor(BLACK);
		textLeft(700, 410 - textSpace, "Rent:");
		textRight(1100, 410 - textSpace, "$25");
		textLeft(700, 410 - textSpace * 2, "2 Railroads owned:");
		textRight(1100, 410 - textSpace * 2, "$50");
		textLeft(700, 410 - textSpace * 3, "3 Railroads owned:");
		textRight(1100, 410 - textSpace * 3, "$100");
		textLeft(700, 410 - textSpace * 4, "4 Railroads owned:");
		textRight(1100, 410 - textSpace * 4, "$200");
		text(900, 250 - textSpace, "Mortgage Value: $" + railroad.getMortgagePrice());
	}

	public static void showLocationDeed(Location location) {
		showDeed = true;
		deed = location;

	}

	public static void drawImprovements() {
		Location[] locations = game.getLocations();
		// bottom row
		for (int j = 1; j < 10; j++) {
			if (locations[j] instanceof Property && ((Property) locations[j]).getImprovementLevel() > 0) {
				if (((Property) locations[j]).getImprovementLevel() < 5) {
					for (int i = 0; i < ((Property) locations[j]).getImprovementLevel(); i++) {
						setPenColor(GREEN);
						filledSquare(1148 + i * (12) - 53 * j, 68, 4);
						setPenColor(BLACK);
						square(1148 + i * (12) - 53 * j, 68, 4);
					}
				} else if (((Property) locations[j]).getImprovementLevel() > 4) {
					setPenColor(RED);
					filledRectangle(1165 - 53 * j, 68, 10, 4);
					setPenColor(BLACK);
					rectangle(1165 - 53 * j, 68, 10, 4);
				}
			}
		}
		// left column
		for (int j = 11; j < 20; j++) {
			if (locations[j] instanceof Property && ((Property) locations[j]).getImprovementLevel() > 0) {
				if (((Property) locations[j]).getImprovementLevel() < 5) {
					for (int i = 0; i < ((Property) locations[j]).getImprovementLevel(); i++) {
						setPenColor(GREEN);
						filledSquare(650, 38 + i * (11) + 49 * (j - 10), 4);
						setPenColor(BLACK);
						square(650, 38 + i * (11) + 49 * (j - 10), 4);
					}
				} else if (((Property) locations[j]).getImprovementLevel() > 4) {
					setPenColor(RED);
					filledRectangle(650, 53 + 49 * (j - 10), 4, 10);
					setPenColor(BLACK);
					rectangle(650, 53 + 49 * (j - 10), 4, 10);
				}
			}
		}
		// right column
		for (int j = 31; j < 40; j++) {
			if (locations[j] instanceof Property && ((Property) locations[j]).getImprovementLevel() > 0) {
				if (((Property) locations[j]).getImprovementLevel() < 5) {
					for (int i = 0; i < ((Property) locations[j]).getImprovementLevel(); i++) {
						setPenColor(GREEN);
						filledSquare(1150, 528 + i * (11) - 49 * (j - 30), 4);
						setPenColor(BLACK);
						square(1150, 528 + i * (11) - 49 * (j - 30), 4);
					}
				} else if (((Property) locations[j]).getImprovementLevel() < 4) {
					setPenColor(RED);
					filledRectangle(1150, 543 - 49 * (j - 30), 4, 10);
					setPenColor(BLACK);
					rectangle(1150, 543 - 49 * (j - 30), 4, 10);
				}
			}
		}
		// top row
		for (int j = 21; j < 30; j++) {
			if (locations[j] instanceof Property && ((Property) locations[j]).getImprovementLevel() > 0) {
				if (((Property) locations[j]).getImprovementLevel() < 4) {
					for (int i = 0; i < ((Property) locations[j]).getImprovementLevel(); i++) {
						setPenColor(GREEN);
						filledSquare(618 + i * (12) + 53 * (j - 20), 530, 4);
						setPenColor(BLACK);
						square(618 + i * (12) + 53 * (j - 20), 530, 4);
					}
				} else if (((Property) locations[j]).getImprovementLevel() > 4) {
					setPenColor(RED);
					filledRectangle(635 + 53 * (j - 20), 530, 10, 4);
					setPenColor(BLACK);
					rectangle(635 + 53 * (j - 20), 530, 10, 4);
				}
			}
		}

	}

}
