/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package weapons;
import dungeonCrawler.Weapon;

public class Sword extends Weapon{
	
	public Sword(String name, int damage) {
		super(name, damage);
		this.name = "Level " + board.getLevel() + " " + name;
		this.description = "The basic sword is best suited for a confident warrior."
				+ "\n" + "It has inherent advantages over other weapons, "
				+ "\n" + "yet not of their drawbacks.";
		this.spawnChance = 0.02 * (10 / board.getLevel());
		this.weight = 2;
	}

	public Sword() {
		super();
		this.name = "Basic Sword";
		this.description = "The basic sword is best suited for a confident warrior."
				+ "\n" + "It has inherent advantages over other weapons, "
				+ "\n" + "yet not of their drawbacks.";
		this.spawnChance = 0.02 * (10 / board.getLevel());
		this.damage = 2;
		this.weight = 2;
	}
	
	//Because the basic sword doesn't impact the user's attributes, equip and unquip do nothing.
	@Override
	public void equip() {
	}

	@Override
	public void unequip() {
	}
	
	@Override
	public int behavior() {
		return this.damage;
	}

	@Override
	public boolean spawn() {
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) >= spawnCheck);
	}
}
