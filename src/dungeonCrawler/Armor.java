package dungeonCrawler;

public class Armor extends Item {

	int defense;
	
	public Armor(String name, int defense) {
		this.name = name;
		this.defense = defense;
	}
	
	@Override
	public int behavior() {
		return this.defense;
	}
}
