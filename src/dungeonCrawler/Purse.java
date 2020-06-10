/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package dungeonCrawler;

public class Purse extends Item{
	private int gold;
	
	public Purse(int gold) {
		super();
		this.gold = gold;
	}

	public void purchase(int price) {
		gold = gold - price;
	}
	
	public void addGold(int gold) {
		this.gold += gold;
	}
	
	@Override
	public int behavior() {
		return gold;
	}

	@Override
	public boolean spawn() {
		return false;
	}
}
