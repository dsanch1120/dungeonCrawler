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
	
	//Methods
	public abstract int behavior();
	public abstract boolean spawn();
	
	//Getters and Setters
	public ItemType getType() {
		return this.type;
	}
	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return this.description;
	}
}
