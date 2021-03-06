/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Abstract class for enemy objects
 */
package dungeonCrawler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy {
	//Variables
	protected String name;
	protected int gold;
	protected char icon;
	protected int XP;
	protected int xCoordinate;
	protected int yCoordinate;
	protected final int WIDTH = 15;
	protected final int HEIGHT = 15;
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
	BufferedImage oImage;
	BufferedImage image;
	
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

	//Other methods
	public Integer agilityRoll() {
		Random rando = new Random();
		return rando.nextInt(this.AGILITY) + 1;
	}
	
	//Methods to be implemented
	public abstract boolean spawn();
	public abstract Integer attack();
	public abstract Integer defend();
	public abstract void takeDamage(int damage);
	public abstract void draw(Graphics cell);
	public abstract void death();
	
	//Getters and Setters
	public char getIcon() {
		return icon;
	}
}
