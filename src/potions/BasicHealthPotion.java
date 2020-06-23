package potions;

import dungeonCrawler.ItemType;
import dungeonCrawler.Potion;

public class BasicHealthPotion extends Potion {
	private int healthRestoration;
	
	public BasicHealthPotion(String name) {
		super(name);
		this.description = "The basic health potion restores a modest amount of health. "
				+ "\n" + "Good for bruises, cuts, and scrapes. "
				+ "\n" + "Not for broken bones, dismemberment, or mortal wounds";
		this.spawnChance = 0.99;	//FIXME
		//this.spawnChance = 0.04 * (5 / board.getLevel());
		this.weight = 0.1;
		this.type = ItemType.POTION;
		this.intelligenceCheck = 2;
		this.price = 4;
		this.healthRestoration = 3;
		checkIntelligence();
	}

	public BasicHealthPotion() {
		super();
		this.name = "Basic Health Potion";
		this.description = "The basic health potion restores 3 points of health. "
				+ "\n" + "Good for bruises, cuts, and scrapes. "
				+ "\n" + "Not for broken bones, dismemberment, or mortal wounds";
		this.spawnChance = 0.04 * (5 / board.getLevel());
		this.weight = 0.1;
		this.type = ItemType.POTION;
		this.intelligenceCheck = 2;
		this.price = 4;
		this.healthRestoration = 3;
		checkIntelligence();
	}
	
	//Implemented Methods
	@Override
	public void checkIntelligence() {
		if (board.getPlayer().getINTELLIGENCE() < intelligenceCheck) {
			this.name = "Unknown Potion";
			this.description = "Your limited intelligence prevents you from"
					+ "\n" + "understanding what this potion does";
		}
	}
	
	@Override
	public void effect() {
		board.getPlayer().updateHealth(healthRestoration);
		health.updateHealth();
	}

	//Healing potions have an "instant" effect, thus the endEffect method is left unimplemented
	@Override
	public void endEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean spawn() {
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) <= spawnCheck);
	}

	@Override
	public void updateStats() {
		this.healthRestoration = 4;
	}
}
