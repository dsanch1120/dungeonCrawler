/*
 * Created by Daniel Sanchez
 * June, 2020
 */
package dungeonCrawler;

public abstract class Potion extends Item{
	
	public Potion(String name) {
		this.name = name;
	}
	
	public Potion() {
	}
	
	public abstract void effect();
	public abstract void endEffect();
	
	@Override
	public int behavior() {
		return -1;
	}
}
