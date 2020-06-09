/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package dungeonCrawler;

public abstract class Weapon extends Item {

	protected int damage;
	
	public Weapon(String name, int damage) {
		this.name = name;
		this.damage = damage;
		this.type = ItemType.WEAPON;
	}
	
	//The "equip" and "unequip" methods modify the player's attributes when the weapon
	//	is equipped or unquipped.
	//	EX. Dagger increases player agility by 1 when equipped.
	public abstract void equip();
	public abstract void unequip();
	public abstract int behavior();

}
