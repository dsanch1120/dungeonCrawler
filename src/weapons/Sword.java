/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package weapons;
import dungeonCrawler.ItemType;
import dungeonCrawler.Weapon;

public class Sword extends Weapon{
	
	public Sword(String name, int damage) {
		super(name, damage);
		this.name = "Level " + board.getLevel() + " " + name;
		this.description = "The basic sword is best suited for a confident warrior."
				+ "\n" + "It has inherent advantages over other weapons, "
				+ "\n" + "yet not of their drawbacks.";
		this.spawnChance = 0.99;	//FIXME
		//this.spawnChance = 0.02 * (10 / board.getLevel());
		this.weight = 2;
	}

	public Sword() {
		super();
		this.name = "Level " + board.getLevel() + " Basic Sword";
		this.description = "The basic sword is best suited for a confident warrior."
				+ "\n" + "It has inherent advantages over other weapons, "
				+ "\n" + "yet not of their drawbacks.";
		this.spawnChance = 0.02 * (10 / board.getLevel());
		this.damage = 1 * board.getLevel();
		this.weight = 2;
		this.type = ItemType.WEAPON;
		this.price = 6;
	}
	
	//Because the basic sword doesn't impact the user's attributes, equip and unquip do nothing.
	@Override
	public void equip() {
		equipped = true;
	}

	@Override
	public void unequip() {
		equipped = false;
	}
	
	@Override
	public int behavior() {
		return this.damage;
	}

	@Override
	public boolean spawn() {
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) <= spawnCheck);
	}
	
	@Override
	public void updateStats() {
		this.damage = (1 * board.getLevel() + 2);
		this.name = "Level " + (board.getLevel() + 2) + " Basic Sword";
	}
}
