/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Abstract class for the various items throughout the game.
 */
package dungeonCrawler;

import java.util.Random;

public abstract class Item {
	//Variables
	protected String name;
	protected char icon;
	protected Board board = Board.getBoard();
	protected String description;
	protected ItemType type;
	protected Random rando = new Random();
	protected double spawnChance;
	protected double weight;
	protected int price;
	protected boolean equipped = false;
	
	//Modifies the price of the item
	public void modifyPrice(int option) {
		switch (option) {
		//Player/enemy owns the item
		case 0:
			this.price--;
			this.price = this.price + Board.getPlayer().getCHARISMA();
		//Merchant owns the items
		case 1:
			this.price++;
			this.price = this.price - Board.getPlayer().getCHARISMA();
			if (this.price < 1) {
				this.price = 1;
			}
			break;
		default:
			System.exit(0);
		}
	}
	
	//Abstract Methods
	public abstract int behavior();
	public abstract boolean spawn();
	public abstract void updateStats();
	
	//Getters and Setters
	public boolean isEquipped() {
		return equipped;
	}
	
	public ItemType getType() {
		return this.type;
	}
	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return this.description;
	}
	public int getPrice() {
		return this.price;
	}
}
