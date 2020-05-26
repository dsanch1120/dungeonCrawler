/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * This class is for the player.
 */
package dungeonCrawler;

import java.util.ArrayList;

public class Player {
	//Variables
	private int ENDURANCE;
	private int STRENGTH;
	private int DEFENSE;
	private int AGILITY;
	private int PERCEPTION;
	private int INTELLIGENCE;
	private int CHARISMA;
	private int XP;
	private int maxHP;
	private int HP;
	private int xCoordinate;
	private int yCoordinate;
	private ArrayList<Item> inventory;
	private int level;
	private final char ICON = 'X';
	
	//Constructor
	public Player(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.HP = 1;
	}
	
	
	//Methods
	
	//Checks the player can level up and increases their level
	public void levelUp() {
		
	}
	public char getIcon() {
		return ICON;
	}


	//Handles the player's attacks
	public Integer attack() {
		return null;
	}
	//Handles the player's defense
	public Integer defend() {
		return null;
	}
	
	//Getters and Setters
	
	
	public int getxCoordinate() {
		return xCoordinate;
	}

	public int getHP() {
		return HP;
	}


	public int getyCoordinate() {
		return yCoordinate;
	}


	public void moveX(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	public void moveY(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	
}
