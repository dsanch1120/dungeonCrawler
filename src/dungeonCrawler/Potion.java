/*
 * Created by Daniel Sanchez
 * June, 2020
 */
package dungeonCrawler;

public abstract class Potion extends Item{
	
	protected int intelligenceCheck;
	protected Health health = Health.getInstance();
	
	public Potion(String name) {
		this.name = name;
		this.type = ItemType.POTION;
	}
	
	public Potion() {
	}
	
	public abstract void effect();
	public abstract void endEffect();
	public abstract void checkIntelligence();
	
	@Override
	public int behavior() {
		return -1;
	}
}
