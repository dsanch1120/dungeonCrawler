package dungeonCrawler;

public class Potion extends Item{
	int type;
	
	public Potion(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public int behavior() {
		return type;
	}
}
