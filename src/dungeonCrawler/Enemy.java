/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Abstract class for enemy objects
 */
package dungeonCrawler;

import java.util.ArrayList;

public abstract class Enemy {
	//Variables
	protected String name;
	protected int gold;
	protected char icon;
	protected int XP;
	protected int xCoordinate;
	protected int yCoordinate;
	protected int ENDURANCE;
	protected int STRENGTH;
	protected int DEFENSE;
	protected int AGILITY;
	protected int INTELLIGENCE;
	protected int CHARISMA;
	protected int maxHP;
	protected int HP;
	protected double spawnChance;
	protected ArrayList<Item> inventory;
	protected Board board = Board.getBoard();
	protected int currentLevel;

	//Constructor
	public Enemy(int xCoordinate, int yCoordinate) {
		super();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.currentLevel = board.getLevel();
		if (this.currentLevel == 0) {
			this.currentLevel++;
		}
	} 

	//Methods to be implemented
	public abstract boolean spawn();
	public abstract Integer attack();
	public abstract Integer defend();

	//Getters and Setters
	public char getIcon() {
		return icon;
	}
}
