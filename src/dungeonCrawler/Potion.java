/*
 * Created by Daniel Sanchez
 * June, 2020
 */
package dungeonCrawler;

public abstract class Potion extends Item{
	int type;
	
	public Potion(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	public abstract void effect();
	public abstract void endEffect();
	
	@Override
	public int behavior() {
		return type;
	}
}
