/*
 * Created by Daniel Sanchez
 * June 6, 2020
 */
package dungeonCrawler;

public abstract class Armor extends Item {

	protected int defense;
	
	public Armor(String name, int defense) {
		this.name = name;
		this.defense = defense;
		this.type = ItemType.ARMOR;
	}
	public Armor() {
		
	}
	
	public abstract void equip();
	public abstract void unequip();
	
	@Override
	public int behavior() {
		return this.defense;
	}
}
