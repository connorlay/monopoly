package monopoly;

import static edu.princeton.cs.introcs.StdRandom.uniform;

public class ChanceDeck {
	
	private static int sizeChance = 16;
	private static int sizeCommunity = 16;
	private static boolean[] chanceDeck = new boolean[16];
	private static boolean[] communityDeck = new boolean[16];
	
	public static int draw(int number){
		if (number==0){
			if (sizeChance==0){
				chanceDeck =  new boolean[16];
			}
			if (sizeCommunity==0){
				communityDeck = new boolean[16];
			}
			while (true){
				int random = uniform(16);
				if (chanceDeck[random]==false){
					chanceDeck[random] = true;
					sizeChance--;
					return random;
				}
			}	
		} else if(number==1){
			while (true){
				int random = uniform(16);
				if (communityDeck[random]==false){
					communityDeck[random] = true;
					sizeCommunity--;
					return random;
				}
			}
		}
		return -1;
			
	}
	
}
