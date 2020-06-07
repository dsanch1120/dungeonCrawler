package dungeonCrawler;

public class Weapon extends Item {

	int damage;
	
	public Weapon(String name, int damage) {
		this.name = name;
		this.damage = damage;
		this.type = ItemType.WEAPON;
	}
	
	@Override
	public int behavior() {
		return damage;
	}

}
