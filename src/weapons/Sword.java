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
		this.description = "The basic sword is best suited for a confident warrior. It has inherent advantages over other weapons, yet not of their drawbacks.";
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

}
