package potions;

import dungeonCrawler.Potion;

public class BasicHealthPotion extends Potion{

	public BasicHealthPotion(String name) {
		super(name);
		this.description = "The basic health potion restores a modest amount of health. "
				+ "\n" + "Good for bruises, cuts, and scrapes. "
				+ "\n" + "Not for broken bones, dismemberment, or mortal wounds";
		this.spawnChance = 0.04 * (5 / board.getLevel());
		this.weight = 0.1;
	}

	public BasicHealthPotion() {
		super();
		this.name = "Basic Health Potion";
		this.description = "The basic health potion restores a modest amount of health. "
				+ "\n" + "Good for bruises, cuts, and scrapes. "
				+ "\n" + "Not for broken bones, dismemberment, or mortal wounds";
		this.spawnChance = 0.04 * (5 / board.getLevel());
		this.weight = 0.1;
	}
	
	@Override
	public void effect() {
		board.getPlayer().updateHealth(3);
	}

	//Healing potions have an "instant" effect, thus the endEffect method is left unimplemented
	@Override
	public void endEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean spawn() {
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) >= spawnCheck);
	}

}
