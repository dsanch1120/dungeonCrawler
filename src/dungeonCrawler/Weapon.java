package dungeonCrawler;

public abstract class Weapon extends Item {

	int damage;
	
	public Weapon(String name, int damage) {
		this.name = name;
		this.damage = damage;
		this.type = ItemType.WEAPON;
	}
	
	public abstract void equip();
	public abstract void unequip();
	
	@Override
	public int behavior() {
		return damage;
	}

}
