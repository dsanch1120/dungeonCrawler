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
		this.name = this.gold + " Gold";
		this.description = "A valuable currency that is dropped by enemies "
				+ "\n" + " and found in treasure chests."
				+ "\n" + "Can be spent at the merchant's store";
		this.type = ItemType.PURSE;
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

	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}
}
