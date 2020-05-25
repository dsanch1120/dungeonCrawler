package dungeonCrawler;

import java.util.Random;

public class Skeleton extends Enemy{

	public Skeleton(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
		this.icon = 'S';
		this.ENDURANCE = 1 * this.currentLevel / 4;
		this.STRENGTH = 1 * this.currentLevel / 3;
		this.DEFENSE = 1 * this.currentLevel / 3;
		this.AGILITY = 2 * this.currentLevel / 4;
		this.INTELLIGENCE = 1 * this.currentLevel / 4;
		this.CHARISMA = 0;
		this.spawnChance = 0.0005 * (5 / this.currentLevel);
		//ADD ITEMS TO SKELETON
		
		//ADD ABILITIES TO SKELETON
	}

	@Override
	public boolean spawn() {
		Random rando = new Random();
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) >= spawnCheck);
	}
	
	@Override
	public Integer attack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer defend() {
		// TODO Auto-generated method stub
		return null;
	}

}
